package com.example.SpringBootCouponProject.facades.exceptions;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerNotFoundException() {
		super("Customer not found");
	}
	

}
