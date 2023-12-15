package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class NoteDaoFake implements NoteDao {
    private final List<Note> allNotes = new ArrayList<>(List.of(
            new Note(0, "title0", "text0", new User(0, "Oleksandr")),
            new Note(2, "title2", "text2", new User(0, "Oleksandr")),
            new Note(1, "title1", "text1", new User(1, "Vasyl"))
    ));

    @Override
    public List<Note> getAll() {
        return allNotes;
    }

    @Override
    public List<Note> getAll(User user) {
        List<Note> userNotes = new ArrayList<>();
        for (Note note : allNotes) {
            if (note.getAuthor().getId() == user.getId()) {
                userNotes.add(note);
            }
        }
        return userNotes;
    }

    @Override
    public Note get(int id) {
        return allNotes.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElseThrow(() -> null);
    }

    @Override
    public List<Note> get(String title) {
        return allNotes.stream()
                .filter(note -> note.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Note> get(String title, Integer id) {
        return allNotes.stream()
                .filter(note -> note.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Note note) {
        allNotes.add(note);
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < allNotes.size(); i++) {
            Note existingNote = allNotes.get(i);
            if (existingNote.getId() == note.getId()) {
                allNotes.set(i, note);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (Note note : allNotes) {
            if (note.getId() == id) {
                allNotes.remove(note);
                return;
            }
        }
    }
}