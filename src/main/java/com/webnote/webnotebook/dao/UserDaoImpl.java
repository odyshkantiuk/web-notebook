package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User get(String name, String password) {
        throw new UnsupportedOperationException();
    }
}
