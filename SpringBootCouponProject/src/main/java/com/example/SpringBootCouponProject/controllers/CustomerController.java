package com.example.SpringBootCouponProject.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.facades.CustomerFacade;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExpiredOrNoLongerInStockException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins="http://localhost:4200")
public class CustomerController {
	
	@Autowired
	private Map<String, Session> sessions;

	// another way of doing it
//	public CustomerController(Map<String, Session> sessions) {
//		super();
//		this.sessions = sessions;
//	}
	
// ========================================= PURCHASE COUPON ================================== \\
		@PostMapping("/{token}")
		public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CouponExistsException, CustomerNotFoundException, CouponExpiredOrNoLongerInStockException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					((CustomerFacade)session.getService()).purchaseCoupon(coupon);
					return ResponseEntity.ok("you purchased the coupon " + coupon.getTitle() + " successfully!");
						
				}
					
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
	    }
	
// ========================================= GET ALL CUSTOMER COUPONS ================================== \\
		@GetMapping("/{token}/AllCoupons")
		public ResponseEntity<?> getAllCoupons(@PathVariable String token) throws CustomerNotFoundException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getAllCoupons());
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET COMPANY FROM COUPON ================================== \\
		
		@GetMapping("/{token}/{id}")
		public ResponseEntity<?> getCompanyFromCoupon(@PathVariable String token, @PathVariable int id) throws CouponExpiredOrNoLongerInStockException{
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getCompanyFromCoupon(id));
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET ALL SYSTEM COUPONS ================================== \\
		@GetMapping("/{token}/AllSystemCoupons")
		public ResponseEntity<?> getAllSystemCoupons(@PathVariable String token) throws CustomerNotFoundException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getAllSystemCoupons());
						
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET COUPONS BY CATEGORY ================================== \\
		@GetMapping("/{token}/CouponsByCategory/{type}")
		public ResponseEntity<?> getAllCouponsByCategory(@PathVariable String token, @PathVariable CategoryType type) throws CustomerNotFoundException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getCouponsByCategory(type));
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET COUPONS UP TO PRICE ================================== \\
		@GetMapping("/{token}/CouponsUpToPrice/{price}")
		public ResponseEntity<?> getAllCouponsUpToPrice(@PathVariable String token, @PathVariable double price) throws CustomerNotFoundException {
		    Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getCouponsUpToPrice(price));
							
				}
						
			}
			   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET CUSTOMER DETAILS ================================== \\
		@GetMapping("/{token}/CustomerDetails")
		public ResponseEntity<?> getCustomerDetails(@PathVariable String token) throws CustomerNotFoundException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getCustomerDetails());
											
				}
										
			}
				  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}

// ========================================= LOGOUT ================================== \\
		@PostMapping("logout")
		public void logout(@RequestBody String token){
			sessions.remove(token);
		}
}

