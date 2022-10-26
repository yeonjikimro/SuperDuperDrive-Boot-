package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
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
    public String insert(@ModelAttribute("notes") Note note,
                         Authentication authentication, RedirectAttributes redirectAttributes) {

        User user = this.userMapper.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        if (note.getNoteId() != null) {
            try {
                noteService.editNote(note);

                redirectAttributes.addFlashAttribute("isSuccessful", "Your changes were successfully saved.");
                return "redirect:/result";
            } catch (Exception error) {
                redirectAttributes.addFlashAttribute("hasError", "Your changes were not saved");
                return "redirect:/result";
            }
        } else {
            try {
                note.setUserId(user.getUserId());
                noteService.insertNote(note);
                redirectAttributes.addFlashAttribute("isSuccessful", "Success! You have saved your note successfully");
                return "redirect:/result";
            } catch(Exception error) {
                redirectAttributes.addFlashAttribute("hasError", "Error: Please try again");
                return "redirect:/result";
            }
        }
    }



    @GetMapping("/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Note note, RedirectAttributes redirectAttributes, Authentication authentication) {

        User user = this.userMapper.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        try {
            noteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("isDeleted", "Success! your note was deleted!");
            return "redirect:/result";
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("hasError", "Error: Please try again");
            return "redirect:/result";
        }

    }

}
