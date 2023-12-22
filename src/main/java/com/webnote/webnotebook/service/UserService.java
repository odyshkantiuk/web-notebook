package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> get(int id);
    User get(String name);
    User getByPassword(String password);
    User login(String name, String password);
    void add(User user);
    void update(User user);
    void delete(int id);
}
