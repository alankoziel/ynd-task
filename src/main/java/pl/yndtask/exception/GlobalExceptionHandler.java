package pl.yndtask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TorExitNodeNotFoundException.class)
    public ResponseEntity<Void> handleTorExitNodeNotFound(TorExitNodeNotFoundException ex) {
        log.error("Tor exit node not found: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(TorExitNodeParseException.class)
    public ResponseEntity<String> handleTorExitNodeParseException(TorExitNodeParseException ex) {
        log.error("Error while parsing Tor exit node data: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("There was an error while processing the Tor exit node data.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }
}
