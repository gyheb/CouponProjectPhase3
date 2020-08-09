package com.example.SpringBootCouponProject.facades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExpiredOrNoLongerInStockException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;

@Service // Component
public class CustomerFacade extends ClientFacade {
	/**
	 * no need to auto wire the repositories
	 * because Client Facade has them all
	 * 
	 * */
	
	public long customerId;
	
	// ======================= login ====================== \\
	
	public boolean login(String email, String password) {
		if(custRepo.existsByEmail(email) && custRepo.existsByPassword(password)) {
			for (Customer cust : custRepo.findAll()) {
				if(cust.getEmail().equals(email) && cust.getPassword().equals(password)) {
					customerId = cust.getCustomerId();
					return true;
				}
			}
		}
		return false;
	}
	
	// ===================== purchase coupon ===================== \\
	/**
	 * 1. Can't purchase the same coupon twice
	 * 2. Can't purchase a coupon with amount 0
	 * 3. Can't purchase and out dated coupon
	 * 4. After the purchase set amount to amount -1
	 * 
	 * */
	
	public void purchaseCoupon(Coupon coupon) throws CouponExistsException, CustomerNotFoundException, CouponExpiredOrNoLongerInStockException {
		Calendar cal = Calendar.getInstance();
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		
		for (Coupon coup :c.getCoupons()) {
			if(coup.getCouponId() == coupon.getCouponId()) {
				throw new CouponExistsException();
			}
			
		}
					
		if(coupon.getAmount() > 0 && coupon.getEndDate().after(new Date(cal.getTimeInMillis()))) {
			coupon.getCustomers().add(c);
			coupon.setAmount(coupon.getAmount()-1);
			coupRepo.save(coupon);	
			
			} else {
				throw new CouponExpiredOrNoLongerInStockException();
			}
			
     	}
	
	// ================= get one coupon ================ \\ 
	
	public Coupon getOneCoupon(long couponId) {
		Coupon c = null;
		for (Coupon coup : coupRepo.findAll()) {
			if(coup.getCouponId() == couponId) {
						c = coup;
						break;
			}
		}
		return c;
		
	}
	
	// =================== get all customer coupons ============== \\
	
	public List<Coupon> getAllCoupons() throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			coupons.add(coup);
		}
		return coupons;
	}
	
	// =================== get all coupons existed ============== \\
	
	public List<Coupon> getAllSystemCoupons() throws CustomerNotFoundException {
		custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : coupRepo.findAll()) {
			coupons.add(coup);
		}
		return coupons;
	}
	
	// ====================== get coupons by ==================== \\
	
	public List<Coupon> getCouponsByCategory(CategoryType type) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getType().equals(type))
			coupons.add(coup);
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsUpToPrice(double price) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getPrice() <= price)
			coupons.add(coup);
		}
		return coupons;
	}
	
	// =================== get customer details =====================\\
	
	public Customer getCustomerDetails() throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		c.setCoupons(c.getCoupons());
		return c;
	}
			
}
	


