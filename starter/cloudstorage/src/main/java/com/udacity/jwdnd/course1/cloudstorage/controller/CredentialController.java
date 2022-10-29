package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
@Slf4j
public class CredentialController {

    private final AuthenticationService authenticationService;
    private UserMapper userMapper;
    private CredentialService credentialService;

    public CredentialController(UserMapper userMapper, CredentialService credentialService, AuthenticationService authenticationService) {
        this.userMapper = userMapper;
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public String saveAndUpdateCredential(@ModelAttribute("credentials") Credentials credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = this.userMapper.getUser(authentication.getName());
        credential.setUserid(user.getUserId());

            try {
                credentialService.insertOrUpdateCredential(credential);
                redirectAttributes.addFlashAttribute("isSuccessful", "Your credential were successfully saved.");
                return "redirect:/result";
            } catch (Exception error) {
                redirectAttributes.addFlashAttribute("hasError", "Error: Please try again");
                return "redirect:/result";
            }
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {

        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addFlashAttribute("isSuccessful","Your credential were successfully saved.");
            return "redirect:/result";
        } catch (Exception Error) {
            redirectAttributes.addFlashAttribute("hasError", "Error: Please try again");
            return "redirect:/result";
        }


    }

}
