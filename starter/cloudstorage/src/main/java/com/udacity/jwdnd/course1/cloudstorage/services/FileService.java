package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService (FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }



    public List<Files> getAllFiles(Integer userId){

        return this.fileMapper.getAllFiles(userId);
    }


    public Files getFileId(Integer fileId) {
        return this.fileMapper.getFileId(fileId);
    }

    public Files getFileName(String fileName) {
        return this.fileMapper.getFileName(fileName);
    }


    public Integer insertFile(Files files, MultipartFile multipartFile) throws Exception {

        files.setFileName(multipartFile.getOriginalFilename());
        files.setContentType(multipartFile.getContentType());
        files.setFileSize(String.valueOf(multipartFile.getSize()));
        files.setFileData(multipartFile.getBytes());

        return this.fileMapper.insertFile(files);
    }


    public void deleteFileId(Integer fileId) {

       this.fileMapper.deleteFileId(fileId);
    }
}
