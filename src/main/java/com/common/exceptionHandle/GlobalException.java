package com.common.exceptionHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    public Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingPathVariableException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            BindException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotSupportedException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class,
            IllegalAccessException.class,
            ArithmeticException.class,
            ClassCastException.class,
            NullPointerException.class
    })
    public Map unifiedException(Throwable RE) {
        logger.info(RE.getMessage());
        return Result.error(1, " SERVER_ERROR");
    }

    @ExceptionHandler({iException.class})
    public Map IResponseException(iException IE) {
        logger.info(IE.getMessage());
        return Result.message(IE.getCode(), IE.getMessage());
    }


    public Map success(RuntimeException e) {
        logger.info(e.getMessage());
        return Result.success();
    }

}
