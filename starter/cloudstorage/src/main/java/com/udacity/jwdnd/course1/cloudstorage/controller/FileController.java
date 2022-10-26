package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/file")
public class FileController {

    private UserMapper userMapper;
    private FileService fileService;

    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(UserMapper userMapper, FileService fileService, UserService userService) {
        this.userMapper = userMapper;
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String upload(@RequestParam("fileUpload") MultipartFile multipartFile, Files files, Authentication authentication, RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
        User user = userMapper.getUser(username);
        files.setUserId(user.getUserId());

        if (fileService.getFileName(multipartFile.getOriginalFilename()) != null) {

            redirectAttributes.addFlashAttribute("hasError", "File name already exists! Please try again!");

            return "redirect:/result";
        } else if (multipartFile.getOriginalFilename().isEmpty()) {
            // File을 선택하지 않고 Upload 버튼을 눌렀을 때
            redirectAttributes.addFlashAttribute("hasError", "You did not select a file! Please try again!");
            return "redirect:/result";
        } else {
            try {
                fileService.insertFile(files, multipartFile);
                redirectAttributes.addFlashAttribute("isSuccessful" , "Your files were successfully saved.");
                return "redirect:/result";
            } catch (Exception error) {
                logger.error(error.getMessage());
                redirectAttributes.addFlashAttribute("hasError", "Error: Please try again");
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/delete/{fileId}")
    public  String delete(@PathVariable Integer fileId, RedirectAttributes redirectAttributes) {

        try {
            fileService.deleteFileId(fileId);
            redirectAttributes.addFlashAttribute("isDeleted", "You file was deleted!");
            return "redirect:/result";
        } catch (Exception error) {
            redirectAttributes.addFlashAttribute("hasError", "Error: Please try again!");
            return "redirect:/result";
        }
    }

    // ResponseEntity는 View를 제공하지 않는 형태로 요청을 처리하고, 직접 결과 데이터 및 Http 상태코드를 설정하여 응답을 할 수 있다.
    // ResponseEntity를 사용하기 위해 응답 상태 코드, 응답 헤더, 응답 본문을 설정해 준다.

    @GetMapping("/view/{fileId}")
    public ResponseEntity<InputStreamSource> downloadFile(@PathVariable Integer fileId) {
        Files files = this.fileService.getFileId(fileId);

        ByteArrayResource resource = new ByteArrayResource(files.getFileData());
        MediaType type = MediaType.parseMediaType(files.getContentType());

        return ResponseEntity.ok().contentType(type).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + files.getFileName() + "\"").body(resource);
        }
    }