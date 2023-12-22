package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> getAll();
    List<Note> getAll(Integer page);
    List<Note> getAll(User user);
    Optional<Note> get(Integer id);
    List<Note> get(String title);
    void addAndUpdate(Integer id, Note note);
    void add(Note note);
    void update(Note note);
    void delete(Integer id);
}
