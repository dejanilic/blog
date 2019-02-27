package com.app.blog.controllers;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception notFound) {
        log.error(notFound.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", notFound);
        modelAndView.setViewName("error/404error");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception numberFormat) {
        log.error(numberFormat.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", numberFormat);
        modelAndView.setViewName("error/406error");
        return modelAndView;
    }
}
