package com.webnote.webnotebook.controller;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import com.webnote.webnotebook.service.UserService;
import com.webnote.webnotebook.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/notes")
public class NoteRestController {
    private final UserService userService;
    private final NoteService noteService;

    @Autowired
    public NoteRestController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        List<Note> notes = noteService.getAll();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Note>> getAll(@RequestParam Integer page) {
        List<Note> notes = noteService.getAll(page);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Note>> getAllByUser(@PathVariable Integer userId) {
        Optional<User> userOptional = userService.get(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Note> notes = noteService.getAll(user);
            if (notes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(notes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<Optional<Note>> get(@PathVariable Integer noteId) {
        Optional<Note> note = noteService.get(noteId);
        if (note.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(note);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Note>> get(@RequestParam String title) {
        List<Note> notes = noteService.get(title);
        if (notes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> add(@RequestBody Note note) {
        noteService.add(note);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/addupdate/{noteId}")
    public ResponseEntity<Note> addAndUpdate(@PathVariable Integer noteId, @RequestBody Note note) {
        noteService.addAndUpdate(noteId, note);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> update(@PathVariable Integer noteId, @RequestBody Note note) {
        if (noteService.get(noteId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        note.setId(noteId);
        noteService.update(note);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer noteId) {
        if (noteService.get(noteId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        noteService.delete(noteId);
        return ResponseEntity.ok(noteId);
    }
}
