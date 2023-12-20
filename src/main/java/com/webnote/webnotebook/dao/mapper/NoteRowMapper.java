package com.webnote.webnotebook.dao.mapper;

import com.webnote.webnotebook.dao.UserDao;
import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper implements RowMapper<Note> {
    private final UserDao userDao;

    public NoteRowMapper(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        int authorId = rs.getInt("author_id");
        User author = userDao.get(authorId);
        return new Note(id, title, content, author);
    }
}
