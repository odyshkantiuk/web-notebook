package com.webnote.webnotebook.dao;

import static org.mockito.Mockito.*;

import com.webnote.webnotebook.dao.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserDaoMock {
    @Bean
    @Scope("singleton")
    public UserDao userDao() {
        UserDao userDao = mock(UserDao.class);
        when(userDao.get("name1", "password")).thenReturn(new User("Oleksandr"));
        when(userDao.get("name2", "password")).thenReturn(new User("Vasyl"));
        when(userDao.get("name3", "password")).thenReturn(new User("Vadim"));
        return userDao;
    }
}
