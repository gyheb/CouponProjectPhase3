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

//	public CustomerController(Map<String, Session> sessions) {
//		super();
//		this.sessions = sessions;
//	}
	
// ========================================= PURCHASE COUPON ================================== \\
		@PostMapping("/{token}")
		public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CouponExistsException, CustomerNotFoundException, CouponExpiredOrNoLongerInStockException {
			Session session = sessions.get(token);
			System.out.println("session p: " + session);
			if(session != null && session.getService() instanceof CustomerFacade) {
				System.out.println("part 1");
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					System.out.println("part 2");
					session.setLastAccessed(System.currentTimeMillis()); 
					System.out.println("part 3");
					((CustomerFacade)session.getService()).purchaseCoupon(coupon);
					System.out.println("part 4");
					return ResponseEntity.ok(coupon);
						
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
		public ResponseEntity<?> getAllCouponsByCategory(@PathVariable String token, @RequestBody CategoryType type) throws CustomerNotFoundException {
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
		public ResponseEntity<?> getAllCouponsUpToPrice(@PathVariable String token, @RequestBody double price) throws CustomerNotFoundException {
		    Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getCouponsUpToPrice(price));
							
				}
						
			}
			   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		}
		
// ========================================= GET ONE COUPON ================================== \\
		@GetMapping("/{token}/Coupon/{id}")
		public ResponseEntity<?> getOneCoupon(@PathVariable String token, @RequestBody long id) throws CustomerNotFoundException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CustomerFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); 
					return ResponseEntity.ok(((CustomerFacade)session.getService()).getOneCoupon(id));
									
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
}
