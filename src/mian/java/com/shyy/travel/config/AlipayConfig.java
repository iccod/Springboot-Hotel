package com.shyy.travel.config;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091500519029";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCiULO35CSdp/6h0NcDtRgRVToGHzweHEYRx/I8s4spXEjBTOPXUy5LLRf4a9kuhZKOSY1c14nyJXuvG1+ymhWpEDn9osUMzge0nXTVm5KEpk+VblVnzqBjT0BQ8jIg7Lciq5ik/J+wDTVntSSuYQQ8V5Hq3clCouGU8m0OuLkaYI7Ixm/ziVJ5IONcJQDtPxmjZRZUeyExd8dsbuxpbw2xJ5MxVhM5ivlqwvPpqAlBJ4+XxOwqdaUXbLVfRgSGiOz75vF49yYXgh2K8Qf8PH4jX+WJc68jbpsoHUUiFD32QVUz+wK8/hj1AYHdakBJ/lEaVIibggd84mX8TJ+mGWSbAgMBAAECggEAITeqz+NcqvcKYUs/9X5GIqXybfZOc0sN++2TYWDNBfXg3c5Cgnp/atsboLdRhvfG+l+LEoj5rhiFq1xKxV5LOIIdnA6HjQtNAGWI134zCfqPJuGhkND0ed5qJyapo5tOD0ZWws0AUSMi3spva1BncYHqVsp0ubJjv/us3epn10G9+MFnidAOWoSHTsSzBSlvKtARcZCmZkcHz7GZgArYHy84ZjY2dbTlCrFAU4PbFBsUHCFFA7Cx5IDUSEC+vAxvb7TGN5FV3QYTazP/q9gh4SOkgVbPmRLDHf36EChY8C0esoIa3xnK07RC4GxWQp6E/Y3Yg+PUr2RNaduN72FO4QKBgQD+bqgsq2PTOnWAXjPbyBUhTzF6yrJcz5kB9BlGxEntmzk5GKo+Vpn+2xCO4jJgzbhzyXG5mD1aPPtLA1ONU0/iAuP520WsLzd3+bQWPh6Naauv9oh79ukxYmVjKqeKHyJAYfCUGaTdzLCfGZlUHyVlaWMQhQe2QzgH4+rOLDtQBQKBgQCjUL03ifevJKfW+vbWUSHycf4U5x6pOOtn0EMlzfxGc2U8cgbT0bLap7QER5b5JzItdTLhPZeL4VbBJd8+r7A7/IUHuB5mUR7j2Bn/aYaBnNCUdSfNtSRK/TQaMgPaih8t3sabgjM/oi/uDTP5FyJgWhRlzqBUEKWeVx95dyIkHwKBgQCyHVdABi+Y9KjDV68oIfag3ea+ll2s2Wu80VxMjSdP3kV98gVkJI4jusnprPIlkH6bGB+0FJ6GqIAm8EoyemtGxk1AYb9xnZBQPg9TLuOGjb4yLf04Aw8WklJ1zKuC5u68cZPQqe8fkqbmii4rlK5CQzelnetbLc1wwJ5k/NP83QKBgEQ+A6MGgrnrzjp1ydkKM+40FeC0Jsq9XxiVHMnaRfVGWOImH5Y5RMw7UWyMYZig+UWneGhILWAk+/Rfl+aA0VpvjUMJe6JzeT3p2gsxFCRq23l9bvF1JMI1BaWAir2yA49OTJ1EjZekdPS7DaGjOhka6vUTn0khX6NHcn51+o0vAoGBANz8rKaOQkB2MM7sknLFMe3ZZXTUMzFMO/7cBPUmIMKaKfL1xoRi7CKwzEby16KrmvQVEB1zkmb0qcikvEYHNE2a4nH9nD5GAj+UZx+TohiPTrYbjd1nZozM/lBNINZJx+/Fif/yA0Fs65ZgIbc/ht49npmPHdtkuQvgijjC6ZwE";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0//g93HilvlAW5lzDg2mg4TFTCi2MVeMyk1ffcI4HEWdcP7x6v673AZNQ0qlIK8Y4OvxxjaX7lQ01cMele1HisBj360aSZH8O6ronrCkvZ//2UG+kZVsIZZ04sc3qwb519J53HbIBLRZuL9gOhMcOOyOUOhiivohw6bo7qPEX2oK5A50AFmQArdMz1+sZp69wk0FKNLGemwRhCA/qrEUtZGmK/MXq787pnZR33Sv57cb4molN+z1QpOJCiNiJRWxr5tHGWK3YlKycfOWSADioNxqOf8c4Xj7x9X3EMfeHMABhZugrBs5WYGd3ujlx1Vxr9rJyZ/1Ov9SOq2L/gWVnQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，测试修改springboot端口和外网地址
    public static String notify_url = "http://gbyxfy.natappfree.cc/notify_url";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网（通过netapp获取net123或自己购买生成）可以正常访问
    public static String return_url = "http://localhost:8080/";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}
