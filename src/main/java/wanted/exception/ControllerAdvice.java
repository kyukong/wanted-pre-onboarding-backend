package wanted.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import wanted.exception.dto.response.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(WantedException.class)
	public ResponseEntity<ExceptionResponse> handledException(WantedException e) {
		ExceptionResponse response = ExceptionResponse.from(e.getMessage());
		return ResponseEntity.status(400).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> unhandledException(Exception e) {
		ExceptionResponse response = ExceptionResponse.from(e.getMessage());
		return ResponseEntity.status(500).body(response);
	}
}
