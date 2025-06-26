package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.Hotel;

import java.util.List;

/**
 * 数据访问接口
 *
 */
public interface HotelDao extends JpaRepository<Hotel,String>,JpaSpecificationExecutor<Hotel>{


    List<Hotel> findByAddrLike(String addr);


}
