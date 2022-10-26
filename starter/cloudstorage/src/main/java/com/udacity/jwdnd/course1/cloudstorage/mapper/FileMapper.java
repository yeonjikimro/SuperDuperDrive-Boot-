package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    List<Files> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files getFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    Files getFileName(String fileName);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(Files files);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    void deleteFileId(Integer fileId);
}
