package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.Gallery;
/**
 * 数据访问接口
 *
 */
public interface GalleryDao extends JpaRepository<Gallery,String>,JpaSpecificationExecutor<Gallery>{
	
}
