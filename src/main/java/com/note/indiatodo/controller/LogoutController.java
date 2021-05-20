package com.note.indiatodo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class LogoutController {

    @RequestMapping(value={"/logout"})
    public String logoutWork(HttpServletRequest req, HttpServletResponse rsp){
        Cookie[] cookies = req.getCookies();
       if(cookies!=null) {
           for (Cookie ck : cookies) {
               String value = ck.getName();
                   if (value.equals("JSESSIONID")) {
                       ck.setMaxAge(0);
                       rsp.addCookie(ck);
                       System.out.println("Cookie Cleared");
                   }
           }
       }
        HttpSession session = req.getSession(false);
        if(session !=null){
            System.out.println("Destroyed The Current Session 2222");
            session.removeAttribute("users");
            session.invalidate();
        }
        return "redirect:/welcome";
    }
}
