package com.fehead.Iot.service;

import com.fehead.Iot.entities.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ktoking
 * @since 2020-11-16
 */
public interface IUserService extends IService<User> {
    //查找用户
    User getUserByEntity(User user);

    //修改密码
    int updateUser(User user);

    //注册用户
    User registerUser(User user);
}
