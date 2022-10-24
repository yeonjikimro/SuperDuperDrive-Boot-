package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/note")
@Slf4j
public class NoteController {

    private final AuthenticationService authenticationService;
    private final NoteService noteService;
    private final UserMapper userMapper;

    public NoteController(NoteService noteService, UserMapper userMapper, AuthenticationService authenticationService) {
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public String insert(@ModelAttribute("note") Note note,
                                Model model, Authentication authentication) {

        User user = this.userMapper.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        this.noteService.insertNote(note);

        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);

        return "home";
    }

    @PutMapping
    public String update(Authentication authentication, @ModelAttribute("note") Note note, Model model) {

        User user = this.userMapper.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        this.noteService.editNote(note);

        return "redirect:/home";
    }


    @GetMapping("/note/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }

}
