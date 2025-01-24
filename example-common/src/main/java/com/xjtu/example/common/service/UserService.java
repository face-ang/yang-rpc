package com.xjtu.example.common.service;

import com.xjtu.example.common.model.User;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 获取用户: 打印用户的名称，并且返回参数中的用户对象。
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
