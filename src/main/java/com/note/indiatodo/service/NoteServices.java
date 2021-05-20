package com.note.indiatodo.service;


import com.note.indiatodo.dao.NoteDao;
import com.note.indiatodo.entity.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServices {
    @Autowired
    NoteDao noteDao;

    public void saveNotes(Notes notes){
        noteDao.saveNote(notes);
    }
    public List<Notes> getNotesByUser(Long id){
        return noteDao.getNotesByUserId(id);
    }
    public void deleteNoteById(Long id){
        noteDao.deleteNoteByNoteId(id);
    }
    public void updateNoteById(Long id,String crDt,String newNote,String tDt,Integer pref){
        noteDao.updateNotesByNoteId(id,crDt,newNote,tDt,pref);
    }
    public String getUserNoteById(Long id){
        return noteDao.getUserNoteById(id);
    }
    public Notes getNotesById(Long id){
       return noteDao.getNotesById(id);
    }
    public List<Notes> getAllHighNotes(Long id){
        return noteDao.getHighProrityTaskFirst(id);
    }
    public void deleteAllNoteByUserId(Long id){
        noteDao.deleteAllNotesByUserId(id);
    }
}
