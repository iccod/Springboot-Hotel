package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.Scenic;

import java.util.List;

/**
 * 数据访问接口
 *
 */
public interface ScenicDao extends JpaRepository<Scenic,String>,JpaSpecificationExecutor<Scenic>{


    List<Scenic> findByContryLike(String name);


}
