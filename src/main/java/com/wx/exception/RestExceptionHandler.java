package com.wx.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Shawn on 2014/5/28.
 */
@ControllerAdvice(basePackages = {"com.wx"})
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException(){
        logger.error("IOException handler executed");
        //returning 404 error code
    }

    @ExceptionHandler
    public @ResponseBody String handleSolrServerException(IllegalArgumentException ex){
        ex.printStackTrace();
        logger.error("IllegalArgumentException");
        return "IllegalArgumentException";
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleException(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        logger.error("Exception:{}, URL:{}, Message:{}",ex.getClass().getSimpleName(), request.getRequestURL(), ex.getMessage());
        return "服务器异常";
    }
}
