package com.shyy.travel.service;

import com.shyy.travel.common.R;
import com.shyy.travel.dao.Hotel_ordersDao;
import com.shyy.travel.common.Result;
import com.shyy.travel.common.StatusCode;
import com.shyy.travel.pojo.Hotel;
import com.shyy.travel.pojo.Hotel_orders;
import com.shyy.travel.pojo.User;
import com.shyy.travel.pojo.*;
import com.shyy.travel.util.BigDecimalUtil;
import com.shyy.travel.util.DateTimeUtil;
import com.shyy.travel.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.util.IPAddressUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class Hotel_ordersService {

    @Autowired
	private HotelService hotelService;

	@Autowired
	private UserService userService;


	@Autowired
	private IdWorker idWorker;

	@Autowired
	private Hotel_ordersDao hotel_ordersDao;




	/**
	 * 修改
	 * @param orders
	 */
	public void update(Hotel_orders orders) {
		hotel_ordersDao.save(orders);
	}

	public void updateStatus(String status) {
		hotel_ordersDao.updateStatus(status);
	}



	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Hotel_orders> findAll() {
		return hotel_ordersDao.findAll();
	}

	/**
	 * 增加
	 * @param orders
	 * @param scenicid
	 * @param count
	 * @return
	 */
	public Result add(Hotel_orders orders, User user, String scenicid , Integer count, String begin, String end) {
		//通过商品id找商品
		Hotel hotel=hotelService.findById(scenicid);
		System.out.println("hotel================="+hotel);

		//计算总金额
		BigDecimal payment = BigDecimalUtil.mul(hotel.getPrice(),count);

		int stock = hotel.getBed();
		//校验库存
		if (stock<count){
			return new Result(false, StatusCode.ERROR,"库存不足");
		}else {
			//减少库存数量
			int newStock = hotel.getBed() - count;
			System.out.println("新的库存"+newStock);
			hotel.setBed(newStock);
			hotelService.update(hotel);
		}

		//获取当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());

		//存入数据库
		orders.setId( idWorker.nextId()+"" );
		orders.setUserid(user.getId());
		orders.setPayment(payment);
		orders.setQty(count);
		orders.setPhone(user.getMobile());
		orders.setScenicid(scenicid);
		orders.setStatus("0");
		orders.setPaytime(DateTimeUtil.strToDate(date,"yyyy-MM-dd HH:mm:ss"));
		orders.setUsername(user.getName());
		orders.setScenicname(hotel.getName());
		orders.setBegin(begin);
		orders.setEnd(end);
		hotel_ordersDao.save(orders);
		return new Result(true,StatusCode.OK,"添加成功");
	}



	/**
	 * 根据ID查询实体
	 * @param userId
	 * @return
	 */
	public List<Hotel_orders> hotel_orders(String userId){
		return hotel_ordersDao.findByUserid(userId);
	}


	public R findPie() {
		List<String> legendData = new ArrayList<>();
		List<Map<String,Object>> seriesData = new ArrayList<>();
		List<Hotel_orders> hoter = this.hotel_ordersDao.findAll();
		for (Hotel_orders hotel_orders : hoter) {
			String username = hotel_orders.getUsername();
			legendData.add(username);
			Map<String,Object> map = new HashMap<>();
			map.put("name",username);
			map.put("value",hotel_orders.getPayment());
			seriesData.add(map);
		}
		return R.ok().put("legendData",legendData).put("seriesData",seriesData);
	}
}
