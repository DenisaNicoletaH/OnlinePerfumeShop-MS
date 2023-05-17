package com.onlineperfumeshop.deliveryservice.Utils;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryInvalidInputException;
import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RestControllerAdvice
public class CheckoutGlobalControllerExceptionHandler {

    //Duplication Exception
    /*
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ConflictDeliveryException.class)
    public HttpErrorInfo handleConflictDeliveryException(WebRequest request, Exception ex) {
        return createHttpErrorInfo(CONFLICT, request, ex);
    }

     */

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DeliveryInvalidInputException.class)
    public HttpErrorInfo handleDeliveryInvalidInputException(WebRequest request, Exception ex) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }


    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, WebRequest request, Exception ex) {
        final String path = request.getDescription(false);
        final String message = ex.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);

        return new HttpErrorInfo(httpStatus, path, message);
    }
}


