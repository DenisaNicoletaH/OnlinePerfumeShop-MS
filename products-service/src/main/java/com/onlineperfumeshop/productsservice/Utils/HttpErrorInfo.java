package com.onlineperfumeshop.productsservice.Utils;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class HttpErrorInfo {

   private final ZonedDateTime timeStamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;


    public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        timeStamp=ZonedDateTime.now();
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
