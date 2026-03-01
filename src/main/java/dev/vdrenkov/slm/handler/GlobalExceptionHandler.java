package dev.vdrenkov.slm.handler;

import dev.vdrenkov.slm.exception.AuthorNotFoundException;
import dev.vdrenkov.slm.exception.BookNotFoundException;
import dev.vdrenkov.slm.exception.ClientNotFoundException;
import dev.vdrenkov.slm.exception.OrderNotFoundException;
import dev.vdrenkov.slm.exception.UserNotFoundException;
import dev.vdrenkov.slm.exception.UserRoleNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
/**
 * GlobalExceptionHandler component.
 */
public class GlobalExceptionHandler {

    private static final String CAUGHT_EXCEPTION = "Caught exception: ";
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    /**
     * Handles handleMethodArgumentNotValidException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final StringBuilder errors = new StringBuilder();
        final BindingResult bindingResult = exception.getBindingResult();

        for (final FieldError error : bindingResult.getFieldErrors()) {
            errors.append(error.getDefaultMessage());
            errors.append("\n");
        }

        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    /**
     * Handles handleUnexpectedTypeException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleUnexpectedTypeException(final UnexpectedTypeException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Wrong data entered";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    /**
     * Handles handleHttpMessageNotReadableException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Incorrect data entered";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    /**
     * Handles handleHttpRequestMethodNotSupportedException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
        final HttpRequestMethodNotSupportedException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "HTTP method not supported";
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    /**
     * Handles handleIllegalArgumentException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = exception.getMessage() != null ? exception.getMessage() : "Illegal argument provided";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    /**
     * Handles handleMissingServletRequestParameterException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleMissingServletRequestParameterException(
        final MissingServletRequestParameterException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Invalid parameter provided";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    /**
     * Handles handleDateTimeParseException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleDateTimeParseException(final DateTimeParseException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Invalid date provided";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    /**
     * Handles handleMethodArgumentTypeMismatchException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(
        final MethodArgumentTypeMismatchException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Invalid URL provided";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    /**
     * Handles handleConstraintViolationException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleConstraintViolationException(final ConstraintViolationException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>("Request validation failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    /**
     * Handles handleHandlerMethodValidationException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleHandlerMethodValidationException(final HandlerMethodValidationException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>("Request validation failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    /**
     * Handles handleInternalAuthenticationServiceException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleInternalAuthenticationServiceException(
        final InternalAuthenticationServiceException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    /**
     * Handles handleBadCredentialsException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleBadCredentialsException(final BadCredentialsException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    /**
     * Handles handleDataIntegrityViolationException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleDataIntegrityViolationException(final DataIntegrityViolationException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        final String message = "Violation of database rules!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    /**
     * Handles handleAuthorNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleAuthorNotFoundException(final AuthorNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    /**
     * Handles handleBookNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleBookNotFoundException(final BookNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    /**
     * Handles handleClientNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleClientNotFoundException(final ClientNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    /**
     * Handles handleOrderNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleOrderNotFoundException(final OrderNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    /**
     * Handles handleUserNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    /**
     * Handles handleUserRoleNotFoundException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleUserRoleNotFoundException(final UserRoleNotFoundException exception) {
        log.warn(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    /**
     * Handles handleException operation.
     * @param exception Raised exception instance.
     * @return Response entity containing the operation result.
     */
    public ResponseEntity<String> handleException(final Exception exception) {
        log.error(CAUGHT_EXCEPTION, exception);
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
