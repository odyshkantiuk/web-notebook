package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.User;

public interface AuthorizationService {
    User get(int id);
    User login(String name, String password);
}
