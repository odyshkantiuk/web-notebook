package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.UserDao;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    public Optional<User> get(int id) {
        return userDao.findById(id);
    }

    @Override
    public User get(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User getByPassword(String password) {
        return userDao.findByPassword(password);
    }

    @Override
    public User login(String name, String password) {
        User user = userDao.findByName(name);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        if (userDao.existsById(user.getId())) {
            userDao.save(user);
        } else {
            throw new RuntimeException("User with id " + user.getId() + " not found");
        }
    }

    @Override
    public void delete(int id) {
        userDao.deleteById(id);
    }
}
