package com.note.indiatodo.controller;


import com.note.indiatodo.entity.Notes;
import com.note.indiatodo.entity.Users;
import com.note.indiatodo.service.NoteServices;
import com.note.indiatodo.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class AddNoteController {
    @Autowired
    private NoteServices noteServices;
    @Autowired
    private UserServices userServices;
    @GetMapping(value="/savenote",produces = MediaType.APPLICATION_XML_VALUE)
    public ModelAndView addNote(Notes notes, HttpServletRequest req){
        Users userdata = (Users) req.getSession().getAttribute("users");
        if(userdata!=null && notes!=null) {
            notes.setUsers(userServices.getUserDataById(userdata.getId()));
            noteServices.saveNotes(notes);
            ModelAndView mv= new ModelAndView();
            List<Notes> notesByUser = noteServices.getNotesByUser(userdata.getId());
            if (notesByUser != null) mv.addObject("noteList",notesByUser);
            mv.addObject("user",userdata);
            mv.setViewName("todo");
            mv.addObject("navbar","logoutnav");
            mv.addObject("content","mynotes");
            mv.addObject("username", userdata.getUser_name());
            req.getSession().setAttribute("users",userdata);
            return mv;
        }
        return new ModelAndView("redirect:/error");
    }
    @RequestMapping(value="/addnewnote",produces = MediaType.APPLICATION_XML_VALUE)
    public ModelAndView addNoteView(HttpServletRequest req){
        Users users = (Users)req.getSession().getAttribute("users");
        if(users!=null) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("todo");
            mv.addObject("user", users);
            mv.addObject("username", users.getUser_name());
            String pattern = "yyyy-MMMMM-dd hh:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            String date = simpleDateFormat.format(new Date());
            mv.addObject("currentDate",date);
            mv.addObject("user_note","Write Your Notes Here");
            mv.addObject("notes",new Notes());
            mv.addObject("navbar","logoutnav");
            mv.addObject("note_id","");
            mv.addObject("content","modifynote");
            mv.addObject("action","/savenote");
            mv.addObject("buttonText","Save");
            return mv;
        }
        return new ModelAndView("redirect:/error");
    }

    @GetMapping(value = "/showingDescriptionData")
    public ModelAndView viewNoteDescription(HttpServletRequest req){
        Users users = (Users)req.getSession().getAttribute("users");
        if(users!=null) {
            String note_id = req.getParameter("note_id");
            Long id=Long.parseLong(note_id);
            String userNote = noteServices.getUserNoteById(id);
            ModelAndView mv= new ModelAndView();
            mv.addObject("userNote",userNote);
            mv.addObject("noteId",id);
            mv.setViewName("todo");
            mv.addObject("navbar","logoutnav");
            mv.addObject("content","viewDescription");
            mv.addObject("user", users);
            mv.addObject("username",users.getUser_name());
            return mv;
        }
        return new ModelAndView("redirect:/error");
    }


    @GetMapping("/update")
    public ModelAndView updateNoteView(HttpServletRequest req) {
        Users user = (Users)req.getSession().getAttribute("users");
        if(user!=null){
            ModelAndView mv=new ModelAndView();
            String note_id = req.getParameter("note_id");
            Notes notes = noteServices.getNotesById(Long.parseLong(note_id));
            mv.setViewName("todo");
            mv.addObject("user", user);
            mv.addObject("note_id",note_id);
            mv.addObject("username", user.getUser_name());
            mv.addObject("user_note",notes.getUser_note());
            mv.addObject("currentDate",notes.getCreate_date());
            mv.addObject("notes",new Notes());
            mv.addObject("navbar","logoutnav");
            mv.addObject("content","modifynote");
            mv.addObject("action","/noteupdate");
            mv.addObject("buttonText","Update");
            return mv;
        }
        return new ModelAndView("error");
    }

    @GetMapping("/noteupdate")
    public ModelAndView updateNote(Notes notes, HttpServletRequest req){
        Users userData=(Users)req.getSession().getAttribute("users");
        if(userData !=null){
            ModelAndView mv= new ModelAndView();
            final String note_id = req.getParameter("note_id");
            noteServices.updateNoteById(Long.parseLong(note_id),notes.getCreate_date(),notes.getUser_note()
            ,notes.getTarget_date(),notes.getUser_preference());
            List<Notes> notesByUser = noteServices.getNotesByUser(userData.getId());
            if(notesByUser!=null)
                mv.addObject("noteList",noteServices.getNotesByUser(userData.getId()));
            mv.addObject("user",userData);
            mv.setViewName("todo");
            mv.addObject("navbar","logoutnav");
            mv.addObject("content","mynotes");
            mv.addObject("username", userData.getUser_name());
            req.getSession().setAttribute("users",userData);
            return mv;
        }
        return new ModelAndView("error");
    }
    @GetMapping("/delete")
    public ModelAndView deleteNoteByUsers(HttpServletRequest req) {
        Users userData = (Users) req.getSession().getAttribute("users");
        String note_id = req.getParameter("note_id");
        Long id= Long.parseLong(note_id);
        if (userData != null) {
            noteServices.deleteNoteById(id);
            ModelAndView mv= new ModelAndView();
            List<Notes> notesByUser = noteServices.getNotesByUser(userData.getId());
            if(notesByUser!=null)
                mv.addObject("noteList",noteServices.getNotesByUser(userData.getId()));
            mv.addObject("user",userData);
            mv.setViewName("todo");
            mv.addObject("navbar","logoutnav");
            mv.addObject("content","mynotes");
            mv.addObject("username", userData.getUser_name());
            req.getSession().setAttribute("users",userData);
            return mv;
        }
        return new ModelAndView("error");
    }
    @RequestMapping("/backtoView")
    public ModelAndView backView(HttpServletRequest req){
        Users userData = (Users) req.getSession().getAttribute("users");
        ModelAndView mv= new ModelAndView();
        try {
            if (userData != null) {
                List<Notes> notesByUser = noteServices.getNotesByUser(userData.getId());
                if (notesByUser != null) mv.addObject("noteList",notesByUser);
                mv.addObject("user",userData);
                mv.setViewName("todo");
                mv.addObject("navbar","logoutnav");
                mv.addObject("content","mynotes");
                mv.addObject("username",userData.getUser_name().toUpperCase());
                req.getSession().setAttribute("users",userData);
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ModelAndView("error");
    }
    @RequestMapping("/showPriorityTasks")
    public ModelAndView getAllHighProrityTasks(HttpServletRequest req){
        Users userData = (Users) req.getSession().getAttribute("users");
        ModelAndView mv= new ModelAndView();
        try {
            if (userData != null) {
                List<Notes> notesByUser = noteServices.getAllHighNotes(userData.getId());
                if (notesByUser != null) mv.addObject("noteList",notesByUser);
                mv.addObject("user",userData);
                mv.setViewName("todo");
                mv.addObject("navbar","logoutnav");
                mv.addObject("content","mynotes");
                mv.addObject("username",userData.getUser_name().toUpperCase());
                req.getSession().setAttribute("users",userData);
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ModelAndView("error");
    }
    @RequestMapping("/deleteAllNotes")
    public ModelAndView deleteAllNotes(HttpServletRequest req){
        Users userData = (Users) req.getSession().getAttribute("users");
        ModelAndView mv= new ModelAndView();
        try {
            if (userData != null) {
                noteServices.deleteAllNoteByUserId(userData.getId());
                List<Notes> notesByUser = noteServices.getNotesByUser(userData.getId());
                if (notesByUser != null) mv.addObject("noteList",notesByUser);
                mv.addObject("user",userData);
                mv.setViewName("todo");
                mv.addObject("navbar","logoutnav");
                mv.addObject("content","mynotes");
                mv.addObject("username",userData.getUser_name().toUpperCase());
                req.getSession().setAttribute("users",userData);
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ModelAndView("error");
    }

}
