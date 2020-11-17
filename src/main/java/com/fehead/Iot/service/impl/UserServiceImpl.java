package com.fehead.Iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fehead.Iot.entities.User;
import com.fehead.Iot.mapper.UserMapper;
import com.fehead.Iot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ktoking
 * @since 2020-11-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByEntity(User user) {
        User user1 = userMapper.selectOne(new QueryWrapper<>(user));
        return user1;
    }

    @Override
    public int updateUser(User user) {
       return userMapper.updateById(user);
    }

    @Override
    public User registerUser(User user) {
        userMapper.insert(user);
        return user;
    }
}
