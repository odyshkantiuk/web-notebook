package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.User;

public interface AuthorizationService {
    User login(String name, String password);
}
