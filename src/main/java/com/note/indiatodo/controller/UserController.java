package com.note.indiatodo.controller;


import com.note.indiatodo.awsConfig.BucketHandler;
import com.note.indiatodo.dto.LoginUserValid;
import com.note.indiatodo.dto.ModalConfig;
import com.note.indiatodo.entity.Notes;
import com.note.indiatodo.entity.Users;
import com.note.indiatodo.service.NoteServices;
import com.note.indiatodo.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private NoteServices noteServices;
    @Autowired
    private BucketHandler bucketHandler;
    @Autowired
    private ModalConfig modalConfig;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ModelAndView signUpUser(@ModelAttribute("userForm") @Valid Users users,
                                   BindingResult bindingResult) {
        ModelAndView mv2 = new ModelAndView();
        if(bindingResult.hasErrors()){
            mv2.setViewName("redirect:/error");
            return mv2;
        }
        //CheckPoint
        if(userServices.getUserData(users.getUser_emai_ph(),users.getUser_password()) != null){
            mv2.setViewName("index");
            mv2.addObject("content","homeview");
            mv2.addObject("navbar","homenav");
            modalConfig.setCheckFlag("SignUp");
            mv2.addObject("modalCheck",modalConfig);
            mv2.addObject("loginValue",new LoginUserValid());
            mv2.addObject("userForm",new Users());
            mv2.addObject("Msg","Registration Failed!.. An User is already Registered. Try with different IDs.");
            return mv2;
        }
        //Saving Image To S3 Bucket.
        if (users.getImage().isEmpty()) {
            users.setUser_image(bucketHandler.getCommonImageUrl());
        }else{
        final URL url = bucketHandler.uplaodImage(users.getUser_name(), users.getImage());
        users.setUser_image(url);
        }
        //END
        userServices.saveUsersData(users);
        mv2.setViewName("index");
        mv2.addObject("content","homeview");
        mv2.addObject("navbar","homenav");
        modalConfig.setCheckFlag("SignUp");
        mv2.addObject("modalCheck",modalConfig);
        mv2.addObject("loginValue",new LoginUserValid());
        mv2.addObject("userForm",new Users());
        mv2.addObject("Msg","Registration Successful! Please Login");
        return mv2;
    }

    @PostMapping("/signin")
    public ModelAndView getUserData(@ModelAttribute("loginVal") LoginUserValid loginUserValid,
                                    HttpServletRequest req,
                                    HttpServletResponse res) {
        Users userData = null;
        ModelAndView mv= new ModelAndView();
        try {
            userData = userServices.getUserData(loginUserValid.getUsername(), loginUserValid.getPassword());
            if (userData != null) {
                List<Notes> notesByUser = noteServices.getNotesByUser(userData.getId());
                if (notesByUser != null) mv.addObject("noteList",notesByUser);
                mv.addObject("user",userData);
                mv.setViewName("todo");
                mv.addObject("navbar","logoutnav");
                mv.addObject("content","mynotes");
                if(userData.getUser_name().contains(" ")){
                    String[] names=userData.getUser_name().split(" ");
                    mv.addObject("username",names[0].toUpperCase());
                }else{
                    mv.addObject("username",userData.getUser_name().toUpperCase());
                }
                HttpSession session = req.getSession();
                session.setAttribute("users",userData);
                session.setMaxInactiveInterval(30*60);
                Cookie cookie= new Cookie("user", URLEncoder.encode(userData.getUser_name(),"UTF-8"));
                cookie.setMaxAge(30*60);
                res.addCookie(cookie);
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().invalidate();
        }
        modalConfig.setCheckFlag("FailLogin");
       ModelAndView mv2= new ModelAndView();
       mv2.setViewName("index");
        mv2.addObject("content","homeview");
        mv2.addObject("navbar","homenav");
       mv2.addObject("modalCheck",modalConfig);
        mv2.addObject("loginValue",new LoginUserValid());
        mv2.addObject("userForm",new Users());
       mv2.addObject("Msg","Authorization Failed! Check Your Credentials!!");
       return mv2;
    }

}

