package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoteMapper {


    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes(Integer userId);
    @Select("SELECT noteId FROM NOTES INNER JOIN USER on NOTE.userId = USERS.user" + "WHERE USERS.username=#{username};")
    Integer getNoteId(String username);

    @Insert("INSERT INTO NOTES(noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}, userId=#{userId} WHERE noteId=#{noteId}")
    Integer editNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    void deleteNote(Integer noteId);
}
