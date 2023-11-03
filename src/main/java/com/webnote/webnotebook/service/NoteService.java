package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;

import java.util.List;

public interface NoteService {
    List<Note> getAll();
    List<Note> getAll(User user);
    Note get(String id);
    void add(String title, String content, User user);
    void update(Note note);
    void delete(String id);
}
