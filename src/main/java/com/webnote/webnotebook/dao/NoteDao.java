package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;

import java.util.List;

public interface NoteDao {
    List<Note> getAll();
    List<Note> getAll(User user);
    Note get(int id);
    List<Note> get(String title);
    void add(Note note);
    void update(Note note);
    void delete(int id);
}
