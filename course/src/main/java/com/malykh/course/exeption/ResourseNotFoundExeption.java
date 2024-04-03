package com.malykh.course.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFoundExeption extends RuntimeException {

    public ResourseNotFoundExeption(String message) {
        super(message);
    }
}
