package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByName(String name);
    User findByPassword(String password);
}
