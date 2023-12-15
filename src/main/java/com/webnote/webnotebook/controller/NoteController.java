package com.webnote.webnotebook.controller;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import com.webnote.webnotebook.service.AuthorizationService;
import com.webnote.webnotebook.service.NoteService;
import com.webnote.webnotebook.service.TokenGeneratorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
public class NoteController {
    private final AuthorizationService authorizationService;
    private final HttpSession session;
    private final NoteService noteService;
    private final TokenGeneratorService tokenGeneratorService;

    @Autowired
    public NoteController(AuthorizationService authorizationService, HttpSession session, NoteService noteService, TokenGeneratorService tokenGeneratorService) {
        this.authorizationService = authorizationService;
        this.session = session;
        this.noteService = noteService;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    @GetMapping("/")
    public String root() {
        return "login";
    }

    @RequestMapping("login")
    public String login(@RequestParam String login, @RequestParam String password) {
        User user = authorizationService.login(login, password);
        if (user == null) return "no-such-user";
        session.setAttribute("user", user);
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model) {
        List<Note> notes = noteService.getAll((User) session.getAttribute("user"));
        model.addAttribute("notes", notes);
        return "home";
    }

    @PostMapping("edit")
    public String edit(Model model, @RequestParam String noteId) {
        if (!noteId.equals("null")) {
            Note note = noteService.get(Integer.valueOf(noteId));
            if (!note.getAuthor().equals(session.getAttribute("user"))) {
                return "error";
            }
            model.addAttribute("title", HtmlUtils.htmlEscape(note.getTitle()));
            model.addAttribute("text", HtmlUtils.htmlEscape(note.getContent()));
        }
        model.addAttribute("noteId", HtmlUtils.htmlEscape(noteId));
        return "note";
    }

    @RequestMapping("save")
    public String save(@RequestParam String noteId, @RequestParam String titleName, @RequestParam String content) {
        User user = (User) session.getAttribute("user");
        Note note;
        if (!noteId.equals("null")) {
            note = noteService.get(Integer.valueOf(noteId));
            if (!note.getAuthor().equals(session.getAttribute("user"))) {
                return "error";
            }
            for (Note notebook : noteService.getAll((User) session.getAttribute("user"))) {
                if (notebook.getTitle().equals(titleName) && Integer.parseInt(noteId) != notebook.getId()) {
                    return "error";
                }
            }
            note.setTitle(titleName);
            note.setContent(content);
            noteService.update(note);
            return "redirect:/home";
        }
        for (Note notebook : noteService.getAll(user)) {
            if (notebook.getTitle().equals(titleName)) {
                return "error";
            }
        }
        noteService.add(new Note(titleName, content, user));
        return "redirect:/home";
    }

    @RequestMapping("delete")
    public String delete(@RequestParam String noteId) {
        Note note = noteService.get(Integer.valueOf(noteId));
        if (!note.getAuthor().equals(session.getAttribute("user"))) {
            return "error";
        }
        noteService.delete(Integer.valueOf(noteId));
        return "redirect:/home";
    }

    @PostMapping("share")
    public String share(@RequestParam String noteId, Model model) {
        Note note = noteService.get(Integer.valueOf(noteId));
        if (!note.getAuthor().equals(session.getAttribute("user"))) {
            return "error";
        }
        String token = tokenGeneratorService.generateToken(noteId);
        model.addAttribute("token", HtmlUtils.htmlEscape(token));
        return "redirect:/share/" + token;
    }

    @GetMapping("share/{token}")
    public String sharedNote(Model model, @PathVariable String token) {
        String noteId = tokenGeneratorService.getNoteId(token);
        Note note = noteService.get(Integer.valueOf(noteId));
        model.addAttribute("title", HtmlUtils.htmlEscape(note.getTitle()));
        model.addAttribute("text", HtmlUtils.htmlEscape(note.getContent()));
        return "share";
    }
}