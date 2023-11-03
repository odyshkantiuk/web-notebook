package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.NoteDao;
import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao;

    @Autowired
    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public List<Note> getAll() {
        return noteDao.getAll();
    }

    @Override
    public List<Note> getAll(User user) {
        return noteDao.getAll(user);
    }

    @Override
    public Note get(String id) {
        return noteDao.get(Integer.parseInt(id));
    }

    @Override
    public void add(String title, String content, User user) {
        List<Integer> noteIds = getAll().stream()
                .map(Note::getId)
                .filter(id -> id >= 0)
                .toList();
        int id = -1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!noteIds.contains(i)) {
                id = i;
                break;
            }
        }
        noteDao.add(new Note(id, title, content, user));
    }

    @Override
    public void update(Note note) {
        noteDao.update(note);
    }

    @Override
    public void delete(String id) {
        noteDao.delete(Integer.parseInt(id));
    }
}
