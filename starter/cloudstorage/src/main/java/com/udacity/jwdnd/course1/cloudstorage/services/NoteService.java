package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userId){
        return this.noteMapper.getAllNotes(userId);
    }



    public int insertNote(Note note) {
        return noteMapper.insertNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public Integer editNote(Note note) {
        return this.noteMapper.editNote(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

}
