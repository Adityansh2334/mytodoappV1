package com.note.indiatodo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object attribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        logger.debug((String) attribute+" Status Code");
        return "error";
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
