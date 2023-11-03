package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.User;

public interface UserDao {
    User get(String name, String password);
}
