package com.example.SpringBootCouponProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCompanyNameOrIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCouponIdOrCompanyIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExpiredOrNoLongerInStockException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;
import com.example.SpringBootCouponProject.login.exceptions.AccessDeniedException;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler
{
	
	@ExceptionHandler(CannotUpdateCompanyNameOrIdException.class)
	public ResponseEntity<String> handleCannotUpdateCompanyNameOrIdException(Exception e){
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
	}
	
	@ExceptionHandler(CannotUpdateCouponIdOrCompanyIdException.class)
	public ResponseEntity<String> handleCannotUpdateCouponIdOrCompanyIdException(Exception e){
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
	}
	
	@ExceptionHandler(CouponExpiredOrNoLongerInStockException.class)
	public ResponseEntity<String> handleCouponExpiredOrNoLongerInStockException(Exception e){
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
	}
	
	@ExceptionHandler(CompanyExistsException.class)
	public ResponseEntity<String> handleCompanyExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CouponExistsException.class)
	public ResponseEntity<String> handleCouponExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CustomerExistsException.class)
	public ResponseEntity<String> handleCustomerExistsException(Exception e){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<String> handleCompanyNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> handleCustomerNotFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
}
