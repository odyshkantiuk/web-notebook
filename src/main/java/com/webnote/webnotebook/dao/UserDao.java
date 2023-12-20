package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User get(int id);
    User get(String name, String password);
    void add(User user);
    void update(User user);
    void delete(int id);
}
