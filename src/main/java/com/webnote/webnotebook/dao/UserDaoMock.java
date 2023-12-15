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
        User oleksandr = new User(0, "Oleksandr");
        User vasyl = new User(1, "Vasyl");
        User vadim = new User(2, "Vadim");
        when(userDao.get("name1", "password")).thenReturn(oleksandr);
        when(userDao.get("name2", "password")).thenReturn(vasyl);
        when(userDao.get("name3", "password")).thenReturn(vadim);
        when(userDao.get(0)).thenReturn(oleksandr);
        when(userDao.get(1)).thenReturn(vasyl);
        when(userDao.get(2)).thenReturn(vadim);
        return userDao;
    }
}
