package ru.practicum.ewm.errorHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.dto.Common.ApiError;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMATTER;
import static ru.practicum.dto.Common.Messages.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                INCORRECT_DATA,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                LocalDateTime.now().format(DEFAULT_DATE_FORMATTER));
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleNotFound(final Throwable e) {
        log.info(e.getMessage());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                INCORRECT_DATA,
                e.getMessage(),
                LocalDateTime.now().format(DEFAULT_DATE_FORMATTER));
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final NotFoundException e) {
        log.info(e.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.getReasonPhrase(),
                NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now().format(DEFAULT_DATE_FORMATTER));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(final DataIntegrityViolationException e) {
        log.info(e.getMessage());
        return new ApiError(HttpStatus.CONFLICT.getReasonPhrase(),
                INTEGRITY_CONSTRAINT,
                e.getMessage(),
                LocalDateTime.now().format(DEFAULT_DATE_FORMATTER));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(final IllegalArgumentException e) {
        log.info(e.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                INCORRECT_DATA,
                e.getMessage(),
                LocalDateTime.now().format(DEFAULT_DATE_FORMATTER));
    }
}