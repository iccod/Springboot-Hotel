package com.shyy.travel.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shyy.travel.config.AlipayConfig;
import com.shyy.travel.util.AliPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
public class AliController {

    @ResponseBody
    @RequestMapping("/paySuccess")
    public String paySuccess() {
        return "支付成功!";
    }

    @ResponseBody
    @RequestMapping("/payFail")
    public String payFail() {
        return "支付失败!";
    }

    /*** 调用支付*/
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goAlipay() throws IOException, AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = AliPayUtil.alipayClient;
        // 订单模型
        String productCode = "FAST_INSTANT_TRADE_PAY";
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        model.setSubject("支付测试");
        model.setTotalAmount("0.01");
        model.setBody("支付测试，共0.01元");
        model.setProductCode(productCode);

        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(AlipayConfig.return_url);
        pagePayRequest.setNotifyUrl(AlipayConfig.notify_url);
        pagePayRequest.setBizModel(model);
        //请求
        String result = alipayClient.pageExecute(pagePayRequest).getBody();
        return result;
    }

    /*** 同步回调*/
    @RequestMapping("/return_url")
    public ModelAndView return_url(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
       // log.info(">>>>>>>>支付成功, 进入同步通知接口...");
        boolean verifyResult = AliPayUtil.rsaCheckV1(request);
        ModelAndView mv = null;
        if (verifyResult) {
            //商户订单号
            String out_trade_no = AliPayUtil.getByte(request.getParameter("out_trade_no"));
            //支付宝交易号
            String trade_no = AliPayUtil.getByte(request.getParameter("trade_no"));
        //    log.info("商户订单号：{}，支付宝交易号，{}", out_trade_no, trade_no);
            mv = new ModelAndView("paySuccess");


        } else {
            mv = new ModelAndView("payFail");
        }
        return mv;
    }

    /*** 异步回调*/
    @ResponseBody
    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    public String notify_url(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
       // log.info(">>>>>>>>支付成功, 进入异步通知接口...");
        // 一定要验签，防止黑客篡改参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder(">>>>>>>>>> alipay notify >>>>>>>>>>>>>>\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n"));
        notifyBuild.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
     //   log.info(notifyBuild.toString());
        boolean flag = AliPayUtil.rsaCheckV1(request);
        if (flag) {
            /**
             * TODO 需要严格按照如下描述校验通知数据的正确性
             *
             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
             *
             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
             * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
             */

            //交易状态
            String tradeStatus = AliPayUtil.getByte(request.getParameter("trade_status"));
            // 商户订单号
            String out_trade_no = AliPayUtil.getByte(request.getParameter("out_trade_no"));
            //支付宝交易号
            String trade_no = AliPayUtil.getByte(request.getParameter("trade_no"));
            //付款金额
            String total_amount = AliPayUtil.getByte(request.getParameter("total_amount"));
           // log.info("交易状态:{},商户订单号:{},支付宝交易号:{},付款金额:{}", tradeStatus, out_trade_no, trade_no, total_amount);
            // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
            // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
            if (tradeStatus.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。

            }
            return "success";
        }
        return "fail";
    }

    /***查看支付流水*/
    @RequestMapping(value = "/queryPay")
    @ResponseBody
    public String queryPay(String orderId) throws IOException, AlipayApiException {
        AlipayClient alipayClient = AliPayUtil.alipayClient;
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderId);
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        alipayRequest.setBizModel(model);
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        return result;
    }

    /**
     * 退款
     *
     * @param orderNo 商户订单号
     * @return
     */
    @PostMapping("/refund")
    @ResponseBody
    public String refund(String orderNo) throws AlipayApiException {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        // 商户订单号
        model.setOutTradeNo(orderNo);
        // 退款金额
        model.setRefundAmount("0.1");
        // 退款原因
        model.setRefundReason("无理由退货");
        // 退款订单号(同一个订单可以分多次部分退款，当分多次时必传)
        String outOrderId = UUID.randomUUID().toString();
        model.setOutRequestNo(outOrderId);
    //    log.info("退款请求号：{}", outOrderId);
        alipayRequest.setBizModel(model);
        AlipayTradeRefundResponse alipayResponse = AliPayUtil.alipayClient.execute(alipayRequest);
        return alipayResponse.getBody();
    }

    /**
     * 退款查询
     *
     * @param orderNo       商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/refundQuery")
    @ResponseBody
    public String refundQuery(String orderNo, String refundOrderNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(orderNo);
        model.setOutRequestNo(refundOrderNo);
        alipayRequest.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse alipayResponse = AliPayUtil.alipayClient.execute(alipayRequest);
        System.out.println(alipayResponse.getBody());

        return alipayResponse.getBody();
    }

    /**
     * 关闭交易
     *
     * @param orderNo
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/close")
    @ResponseBody
    public String close(String orderNo) throws AlipayApiException {
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(orderNo);
        alipayRequest.setBizModel(model);
        AlipayTradeCloseResponse alipayResponse = AliPayUtil.alipayClient.execute(alipayRequest);
        System.out.println(alipayResponse.getBody());
        return alipayResponse.getBody();
    }

}
