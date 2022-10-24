package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(){
        return this.noteMapper.getAllNotes();
    }

    public Note findByNoteId(Integer noteId) {

        return this.noteMapper.getfindByNoteId(noteId);
    }


    public Integer insertNote(Note note) {
        return this.noteMapper.insertNote(note);
    }

    public Integer editNote(Note note) {
        return this.noteMapper.editNote(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

}
