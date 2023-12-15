package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.UserDao;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserDao userDao;

    @Autowired
    public AuthorizationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public User login(String name, String password) {
        return userDao.get(name, password);
    }
}
