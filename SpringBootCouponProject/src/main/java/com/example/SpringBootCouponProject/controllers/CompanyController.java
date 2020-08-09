package com.example.SpringBootCouponProject.controllers;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.facades.CompanyFacade;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCouponIdOrCompanyIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;

@RestController
@RequestMapping("company")
@CrossOrigin(origins="http://localhost:4200")
public class CompanyController {
	
	private Map<String, Session> sessions;

	public CompanyController(Map<String, Session> sessions) {
		super();
		this.sessions = sessions;
	}
	
	// ========================================= ADD COUPON ================================== \\
		@PostMapping("/{token}/Coupon")
		public ResponseEntity<?> addCoupon (@PathVariable String token, @RequestBody Coupon coupon) throws CompanyNotFoundException, CouponExistsException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				// checking the current time - the last time the user accessed is lower than 10min is ok
				// otherwise i don't know you
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis()); // update if user access
					((CompanyFacade)session.getService()).addCoupon(coupon);
					return ResponseEntity.ok(coupon);
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= UPDATE COUPON ================================== \\
		@PutMapping("/{token}/Coupon")
		public ResponseEntity<?> updateCoupon (@PathVariable String token, @RequestBody Coupon coupon) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					((CompanyFacade)session.getService()).updateCoupon(coupon);
					return ResponseEntity.ok(coupon);
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= DELETE COUPON ================================== \\
		@DeleteMapping("/{token}/Coupon/{id}")
		public ResponseEntity<?> deleteCoupon (@PathVariable String token, @RequestBody long id) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					((CompanyFacade)session.getService()).deleteCoupon(id);
					return ResponseEntity.ok("The coupon with the following id " + id + " was deleted!");
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= GET ONE COUPON ================================== \\
		@GetMapping("/{token}/Coupon/{id}")
		public ResponseEntity<?> getOneCoupon (@PathVariable String token, @RequestBody long id) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade)session.getService()).getOneCoupon(id));
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= GET ALL COUPONS ================================== \\
		@GetMapping("/{token}/AllCoupons")
		public ResponseEntity<?> getAllCoupon (@PathVariable String token) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade)session.getService()).getAllCoupons());
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= GET COUPONS BY CATEGORY ================================== \\
		@GetMapping("/{token}/CouponsByCategory/{type}")
		public ResponseEntity<?> getCouponsByCategory (@PathVariable String token, @RequestBody CategoryType type) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade)session.getService()).getCouponByCategory(type));
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= GET COUPONS UP TO PRICE ================================== \\
		@GetMapping("/{token}/CouponsUpToPrice/{price}")
		public ResponseEntity<?> getCouponsUpToPrice (@PathVariable String token, @RequestBody double price) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade)session.getService()).getCouponsUpToPrice(price));
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
		
	// ========================================= GET COMPANY DETAILS ================================== \\
		@GetMapping("/{token}/CompanyDetails")
		public ResponseEntity<?> getCompanyDetails (@PathVariable String token) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
			Session session = sessions.get(token);
			if(session != null && session.getService() instanceof CompanyFacade) {
				if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
					session.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade)session.getService()).getCompanyDetails());
					
				}
				
			}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
		}
}
