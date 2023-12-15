package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {
    @Override
    public List<Note> getAll() {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<Note> getAll(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Note get(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Note> get(String title) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Note> get(String title, Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Note note) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }
}
