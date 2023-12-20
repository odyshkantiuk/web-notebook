package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.User;
import com.webnote.webnotebook.dao.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public User get(String name, String password) {
        String sql = "SELECT * FROM users WHERE name = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), name);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (id, name, password) VALUES (user_seq.nextval, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name=?, password=? WHERE id=?";
        jdbcTemplate.update(sql, user.getName(), passwordEncoder.encode(user.getPassword()), user.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
