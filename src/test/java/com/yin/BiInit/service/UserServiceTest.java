package com.yin.BiInit.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 *
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "yonghu";
        String userPassword = "";
        String checkPassword = "12345678";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "yonghu";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }
}
