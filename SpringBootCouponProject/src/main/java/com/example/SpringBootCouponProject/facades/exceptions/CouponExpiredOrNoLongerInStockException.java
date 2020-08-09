package com.example.SpringBootCouponProject.facades.exceptions;

public class CouponExpiredOrNoLongerInStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponExpiredOrNoLongerInStockException() {
		super("Coupon is expired or not longer in stock");
	}

}
