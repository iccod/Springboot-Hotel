package com.shyy.travel.dao;

import com.shyy.travel.pojo.Cates;
import com.shyy.travel.pojo.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 *
 */
public interface CatesDao extends JpaRepository<Cates,String>,JpaSpecificationExecutor<Cates>{


    List<Hotel> findByAddrLike(String addr);



}
