package com.note.indiatodo.controller;

import com.note.indiatodo.dto.LoginUserValid;
import com.note.indiatodo.dto.ModalConfig;
import com.note.indiatodo.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class WelcomeController {

    @RequestMapping(value={"/welcome","/"})
    public ModelAndView welcomeUser(){
        ModelAndView mv= new ModelAndView();
        mv.setViewName("index");
        mv.addObject("content","homeview");
        mv.addObject("navbar","homenav");
        mv.addObject("modalCheck",new ModalConfig());
        mv.addObject("userForm",new Users());
        mv.addObject("loginValue",new LoginUserValid());
        return mv;
    }
}
