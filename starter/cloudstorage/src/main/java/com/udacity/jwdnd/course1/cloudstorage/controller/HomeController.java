package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {


    private NoteService noteService;

    private UserService userService;

    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication, Note note) {

        List<Note> noteList = noteService.getAllNotes();

        model.addAttribute("noteList", noteList);


        return "home";

    }

}
