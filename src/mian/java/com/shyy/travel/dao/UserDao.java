package com.shyy.travel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shyy.travel.pojo.User;

/**
 * 数据访问接口
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);

    /**
     * 根据姓名查询用户
     * @param name
     * @return
     */
    public User findByName(String name);

    /**
     *
     * @param email
     * @return
     */
    public User findByEmail(String email);


}
