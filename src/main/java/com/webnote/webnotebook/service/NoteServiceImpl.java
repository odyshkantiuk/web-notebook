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
    public List<Note> getAll(Integer page) {
        List<Note> notes = noteDao.getAll();
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(page * 10, notes.size());
        if (startIndex >= notes.size()) {
            return notes;
        }
        return notes.subList(startIndex, endIndex);
    }

    @Override
    public List<Note> getAll(User user) {
        return noteDao.getAll(user);
    }

    @Override
    public Note get(Integer id) {
        return noteDao.get(id);
    }

    @Override
    public List<Note> get(String title) {
        return noteDao.get(title);
    }

    @Override
    public List<Note> get(String title, Integer id) {
        return noteDao.get(title, id);
    }

    @Override
    public void add(Note note) {
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
        noteDao.add(new Note(id, note.getTitle(), note.getContent(), note.getAuthor()));
    }

    @Override
    public void update(Note note) {
        noteDao.update(note);
    }

    @Override
    public void delete(Integer id) {
        noteDao.delete(id);
    }
}
