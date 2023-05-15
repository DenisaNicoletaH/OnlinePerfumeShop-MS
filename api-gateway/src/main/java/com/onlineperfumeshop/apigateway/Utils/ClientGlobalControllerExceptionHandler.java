package com.onlineperfumeshop.apigateway.Utils;

import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.ClientInvalidInputException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.ConflictClientException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ClientGlobalControllerExceptionHandler {

    //Duplication Exception
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConflictClientException.class)
    public HttpErrorInfo handleConflictClientException(WebRequest request, Exception ex) {
        return createHttpErrorInfo(CONFLICT, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ClientInvalidInputException.class)
    public HttpErrorInfo handleClientInvalidInputException(WebRequest request, Exception ex) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpErrorInfo handleClientNotFoundException(WebRequest request, Exception ex) {
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }




    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, WebRequest request, Exception ex) {
        final String path = request.getDescription(false);
        final String message = ex.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);

        return new HttpErrorInfo(httpStatus, path, message);
    }
}


