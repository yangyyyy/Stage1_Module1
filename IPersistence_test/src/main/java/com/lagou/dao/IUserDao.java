package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;

    //插入单个用户
    public boolean insertUser(User user) throws Exception;

    //根据id更新username
    public boolean updateUser(User user) throws Exception;

    //根据id删除user
    public boolean deleteUser(User user) throws Exception;

    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;


}
