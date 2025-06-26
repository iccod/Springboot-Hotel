package com.shyy.travel.controller.admin;

import com.shyy.travel.common.R;
import com.shyy.travel.common.Result;
import com.shyy.travel.common.StatusCode;
import com.shyy.travel.dao.Hotel_ordersDao;
import com.shyy.travel.pojo.Hotel_orders;
import com.shyy.travel.pojo.User;
import com.shyy.travel.service.Hotel_ordersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/hotelorders")
public class Hotel_OrdersController {


    @Autowired
    private Hotel_ordersService hotel_ordersService;


    /**
     * 查询全部数据
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",hotel_ordersService.findAll());
    }

    /**
     * 增加
     * @param orders
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Hotel_orders orders, HttpSession session){

        //获取数量
        System.out.println("id为--->"+orders.getId());
        System.out.println("数量为--->"+orders.getQty());
        System.out.println("数量为--->"+orders.getBegin());
        System.out.println("数量为--->"+orders.getEnd());
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new Result(false,StatusCode.ACCESSERROR,"请登录");
        }

        return hotel_ordersService.add(orders,user,orders.getId(),orders.getQty(),orders.getBegin(),orders.getEnd());
    }

    /**
     * 修改
     * @param
     */
    @ResponseBody
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@PathVariable String id){
        System.out.println(id);
        hotel_ordersService.updateStatus(id);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/ordersList")
    public String ordersList(){
        return "admin/ordersmanage/hotal_orders";
    }

}
