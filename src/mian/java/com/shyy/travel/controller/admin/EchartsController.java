package com.shyy.travel.controller.admin;

import com.shyy.travel.common.R;
import com.shyy.travel.service.Hotel_ordersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchartsController {

    @Autowired
    private Hotel_ordersService ordersService;

    @RequestMapping("/sys/echarts/pie")
    public R findPie(){
        return this.ordersService.findPie();
    }
}
