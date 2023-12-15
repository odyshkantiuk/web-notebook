package com.webnote.webnotebook.controller;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.service.AuthorizationService;
import com.webnote.webnotebook.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notes")
public class FrontController {
    private final AuthorizationService authorizationService;
    private final NoteService noteService;

    @Autowired
    public FrontController(AuthorizationService authorizationService, NoteService noteService) {
        this.authorizationService = authorizationService;
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
        List<Note> notes = noteService.getAll(authorizationService.get(userId));
        if (notes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<Note> get(@PathVariable Integer noteId) {
        Note note = noteService.get(noteId);
        if (note == null) {
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
    public ResponseEntity<String> add(@RequestBody Note note) {
        noteService.add(note);
        return ResponseEntity.ok("Note added successfully");
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<String> update(@PathVariable Integer noteId, @RequestBody Note note) {
        if (noteService.get(noteId) == null) {
            return ResponseEntity.notFound().build();
        }
        note.setId(noteId);
        noteService.update(note);
        return ResponseEntity.ok("Note updated successfully");
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> delete(@PathVariable Integer noteId) {
        if (noteService.get(noteId) == null) {
            return ResponseEntity.notFound().build();
        }
        noteService.delete(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }
}
