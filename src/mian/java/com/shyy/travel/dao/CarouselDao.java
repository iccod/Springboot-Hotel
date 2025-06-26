package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.Carousel;
/**
 * 数据访问接口
 *
 */
public interface CarouselDao extends JpaRepository<Carousel,String>,JpaSpecificationExecutor<Carousel>{
	
}
