package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import com.webnote.webnotebook.dao.mapper.NoteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    @Autowired
    public NoteDaoImpl(DataSource dataSource, UserDao userDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userDao = userDao;
    }

    @Override
    public List<Note> getAll() {
        String sql = "SELECT * FROM notes";
        return jdbcTemplate.query(sql, new NoteRowMapper(userDao));
    }

    @Override
    public List<Note> getAll(User user) {
        String sql = "SELECT * FROM notes WHERE author_id = ?";
        return jdbcTemplate.query(sql, new NoteRowMapper(userDao), user.getId());
    }

    @Override
    public Note get(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new NoteRowMapper(userDao), id);
    }

    @Override
    public List<Note> get(String title) {
        String sql = "SELECT * FROM notes WHERE title LIKE ?";
        String searchTerm = "%" + title + "%";
        return jdbcTemplate.query(sql, new NoteRowMapper(userDao), searchTerm);
    }

    @Override
    public void add(Note note) {
        String sql = "INSERT INTO notes (id, title, content, author_id) VALUES (note_seq.nextval, ?, ?, ?)";
        jdbcTemplate.update(sql, note.getTitle(), note.getContent(), note.getAuthor().getId());
    }

    @Override
    public void update(Note note) {
        if (this.get(note.getId()) == null) {
            throw new RuntimeException();
        }
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, note.getTitle(), note.getContent(), note.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
