package com.fehead.Iot.controller;


import com.fehead.Iot.entities.User;
import com.fehead.Iot.error.BusinessException;
import com.fehead.Iot.error.EmBusinessError;
import com.fehead.Iot.response.CommonReturnType;
import com.fehead.Iot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ktoking
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @PostMapping("/insert")
    public CommonReturnType userInsert(String userName,String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringIsEmpty(userName)||StringIsEmpty(password))  throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名或密码为空");
        User selectUser = new User().setUserName(userName);
        User userByEntity = userService.getUserByEntity(selectUser);
        if(userByEntity!=null){
            throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名已存在");
        }else {
            //注册新用户
            selectUser.setUserPassword(EncoderByMd5(password));
            try {
                User user = userService.registerUser(selectUser);
            }catch (Exception e){
                throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR);
            }
            return CommonReturnType.creat(selectUser);
        }
    }

    @GetMapping("/login")
    public CommonReturnType userLogin(String userName,String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringIsEmpty(userName)||StringIsEmpty(password))  throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名或密码为空");
        User user=new User();
        user.setUserName(userName);
        user.setUserPassword(EncoderByMd5(password));
        User login = userService.getUserByEntity(user);
        if(login==null) throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名或密码错误");
        else return CommonReturnType.creat(login);
    }

    @PutMapping("/update")
    public CommonReturnType updateUser(String userName,String oldPassword,String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringIsEmpty(userName)||StringIsEmpty(oldPassword)||StringIsEmpty(newPassword)) throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名或密码为空");
        User user = new User().setUserName(userName).setUserPassword(EncoderByMd5(oldPassword));
        User userByEntity = userService.getUserByEntity(user);
        if(userByEntity==null){
            throw new BusinessException(EmBusinessError.DATA_INSERT_ERROR,"用户名或密码错误");
        }else {
            //修改密码逻辑
            User updateUser = new User().setUserPassword(EncoderByMd5(newPassword)).setUserName(userName).setId(userByEntity.getId());
            try {
                userService.updateUser(updateUser);
            }catch (Exception e){
                throw new BusinessException(EmBusinessError.DATA_UPDATE_ERROR);
            }
            return CommonReturnType.creat(updateUser);
        }
    }


    public static boolean StringIsEmpty(String str){
        if(str.length()==0||str==null) return true;
        return false;
    }
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    /**判断用户密码是否正确
     *newpasswd 用户输入的密码
     *oldpasswd 正确密码*/
    public static boolean checkpassword(String newpasswd,String oldpasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(EncoderByMd5(newpasswd).equals(oldpasswd))
            return true;
        else
            return false;
    }
}

