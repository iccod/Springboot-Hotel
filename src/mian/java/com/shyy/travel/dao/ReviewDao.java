package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.Review;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReviewDao extends JpaRepository<Review,String>,JpaSpecificationExecutor<Review>{
	
}
