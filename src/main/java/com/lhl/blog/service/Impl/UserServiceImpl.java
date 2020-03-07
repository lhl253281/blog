package com.lhl.blog.service.Impl;

import com.lhl.blog.dao.UserRepository;
import com.lhl.blog.pojo.User;
import com.lhl.blog.service.UserService;
import com.lhl.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User checkUser(String username, String password) {

        User user = repository.findByUsernameAndPassword(username, MD5Util.encodeMD5(password));
        return user;
    }
}
