package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User get(int id);
    User login(String name, String password);
    void add(User user);
    void update(User user);
    void delete(int id);
}
