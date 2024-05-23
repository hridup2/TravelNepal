package com.travelNepal.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> LoginExceptionHandler(LoginException lx, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(lx.getMessage(), req.getDescription(false), LocalDateTime.now());
		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		return rs;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> ParentExceptionHandler(Exception ex, WebRequest req) {
		
		MyErrorDetails err = new MyErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		return rs;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> NohandlerFoundExceptionHandler(NoHandlerFoundException nf, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(nf.getMessage(), req.getDescription(false), LocalDateTime.now());

		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);

		return rs;

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> MANVExceptionHandler(MethodArgumentNotValidException mv) {

		MyErrorDetails err = new MyErrorDetails("Validation Error",
				mv.getBindingResult().getFieldError().getDefaultMessage(), LocalDateTime.now());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(BookingException.class)
	public ResponseEntity<MyErrorDetails> BookingsExceptionHandler(BookingException bx, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(bx.getMessage(), req.getDescription(false), LocalDateTime.now());
		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		return rs;
	}

	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> AdminExceptionHandler(AdminException ax, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(ax.getMessage(), req.getDescription(false), LocalDateTime.now());
		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		return rs;
	}

	@ExceptionHandler(PackageException.class)
	public ResponseEntity<MyErrorDetails> PackageExceptionHandler(PackageException ax, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(ax.getMessage(), req.getDescription(false), LocalDateTime.now());
		ResponseEntity<MyErrorDetails> rs = new ResponseEntity<>(err, HttpStatus.OK);
		return rs;
	}
}