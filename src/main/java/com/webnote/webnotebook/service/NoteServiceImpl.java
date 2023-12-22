package com.webnote.webnotebook.service;

import com.webnote.webnotebook.dao.NoteDao;
import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao;

    @Autowired
    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public List<Note> getAll() {
        return (List<Note>) noteDao.findAll();
    }

    @Override
    public List<Note> getAll(Integer page) {
        List<Note> notes = (List<Note>) noteDao.findAll();
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(page * 10, notes.size());
        if (startIndex >= notes.size()) {
            return notes;
        }
        return notes.subList(startIndex, endIndex);
    }

    @Override
    public List<Note> getAll(User user) {
        return noteDao.findAllByUserId(user.getId());
    }

    @Override
    public Optional<Note> get(Integer id) {
        return noteDao.findById(id);
    }

    @Override
    public List<Note> get(String title) {
        return noteDao.findByTitleContaining(title);
    }

    @Transactional
    public void addAndUpdate(Integer id, Note note) {
        noteDao.save(note);
        note.setId(id);
        if (noteDao.existsById(note.getId())) {
            noteDao.save(note);
        } else {
            throw new RuntimeException("Note with id " + note.getId() + " not found");
        }
    }

    @Override
    public void add(Note note) {
        noteDao.save(note);
    }

    @Override
    public void update(Note note) {
        if (noteDao.existsById(note.getId())) {
            noteDao.save(note);
        } else {
            throw new RuntimeException("Note with id " + note.getId() + " not found");
        }
    }

    @Override
    public void delete(Integer id) {
        noteDao.deleteById(id);
    }
}
