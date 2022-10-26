package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private UserMapper userMapper;

    private NoteService noteService;

    private UserService userService;

    private EncryptionService encryptionService;
    
    private FileService fileService;

    private CredentialMapper credentialMapper;

    private CredentialService credentialService;

    public HomeController(UserMapper userMapper, NoteService noteService, UserService userService, EncryptionService encryptionService, FileService fileService, CredentialMapper credentialMapper, CredentialService credentialService) {
        this.userMapper = userMapper;
        this.noteService = noteService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.credentialMapper = credentialMapper;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication, Note note, Credentials credential) {

        User user = this.userMapper.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(user.getUserId());


        List<Note> noteList = this.noteService.getAllNotes(note.getUserId());
        List<Credentials> credentialsList = this.credentialService.getAllCredentials(credential.getCredentialId());

        model.addAttribute("noteList", noteList);
        model.addAttribute("filesList", fileService.getAllFiles(userId));
        model.addAttribute("credentialList",credentialsList);


        return "home";

    }

}
