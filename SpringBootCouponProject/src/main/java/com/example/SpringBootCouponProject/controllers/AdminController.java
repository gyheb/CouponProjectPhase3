package com.example.SpringBootCouponProject.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.SpringBootCouponProject.beans.Company;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.ManagerFacade;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCompanyNameOrIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins="http://localhost:4200")
public class AdminController {
	
	@Autowired
	private Map<String, Session> sessions;
	
//	public AdminController(Map<String, Session> sessions) {
//		super();
//		this.sessions = sessions;
//	}

// ========================================= ADD COMPANY ================================== \\
	@PostMapping("/Company/{token}")
	public ResponseEntity<?> addCompany (@PathVariable String token, @RequestBody Company company) throws CompanyExistsException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			// checking the current time - the last time the user accessed is lower than 10min is ok
			// otherwise i don't know you
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); // update if user access
				((ManagerFacade)session.getService()).addCompany(company);
				return ResponseEntity.ok(company);
				
			}
			
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		
	}

// ========================================= ADD CUSTOMER ================================== \\
	@PostMapping("/{token}/Customer")
	public ResponseEntity<?> addCustomer (@PathVariable String token, @RequestBody Customer customer) throws CustomerExistsException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				((ManagerFacade)session.getService()).addCustomer(customer);
				return ResponseEntity.ok(customer);
				
			}
			
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
		
	}

// ========================================= DELETE CUSTOMER ================================== \\
	@DeleteMapping("/{token}/Customer/{id}")
	public ResponseEntity<?> deleteCustomer (@PathVariable String token, @RequestBody long id) throws CustomerNotFoundException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				((ManagerFacade)session.getService()).deleteCustomer(id);
				return ResponseEntity.ok("The customer with the following id " + id + " was deleted!");
					
			}
				
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
			
	}
	
// ========================================= DELETE COMPANY ================================== \\
	@DeleteMapping("/{token}/Company/{id}")
	public ResponseEntity<?> deleteCompany (@PathVariable String token, @RequestBody long id) throws CompanyNotFoundException  {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				((ManagerFacade)session.getService()).deleteCompany(id);
				return ResponseEntity.ok("The company with the following id " + id + " was deleted!");
						
			}
					
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
				
	}
	
// ========================================= UPDATE COMPANY ================================== \\
	@PutMapping("/{token}/Company")
	public ResponseEntity<?> updateCompany (@PathVariable String token, @RequestBody Company company) throws CannotUpdateCompanyNameOrIdException, CompanyNotFoundException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				((ManagerFacade)session.getService()).updateCompany(company);
				return ResponseEntity.ok("Company info updated!");
							
			}
						
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
					
	}
	
// ========================================= UPDATE CUSTOMER ================================== \\
	@PutMapping("/{token}/Customer")
	public ResponseEntity<?> updateCustomer (@PathVariable String token, @RequestBody Customer customer) throws CustomerNotFoundException, CustomerExistsException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				((ManagerFacade)session.getService()).updateCustomer(customer);
				return ResponseEntity.ok("Customer info updated!");
								
			}
							
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
						
	}

// ========================================= GET ALL COMPANIES ================================== \\
	@GetMapping("/AllCompanies/{token}")
	public ResponseEntity<?> getAllCompanies(@PathVariable String token){
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis()); 
				System.out.println(((ManagerFacade)session.getService()).getAllCompanies());
				return ResponseEntity.ok(((ManagerFacade)session.getService()).getAllCompanies());
				
			}
			
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
	}
	
// ========================================= GET ALL CUSTOMERS ================================== \\
	@GetMapping("/{token}/AllCustomers")
	public ResponseEntity<?> getAllCustomers(@PathVariable String token){
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis());
				return ResponseEntity.ok(((ManagerFacade)session.getService()).getAllCustomers());
				
			}
			
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
	}
	
// ========================================= GET ONE CUSTOMER ================================== \\
	@GetMapping("/{token}/Customer/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable String token, @RequestBody long id) throws CustomerNotFoundException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis());
				return ResponseEntity.ok(((ManagerFacade)session.getService()).getOneCustomer(id));
					
			}
				
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
	}
	
// ========================================= GET ONE CUSTOMER ================================== \\
	@GetMapping("/{token}/Company/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @RequestBody long id) throws CompanyNotFoundException {
		Session session = sessions.get(token);
		if(session != null && session.getService() instanceof ManagerFacade) {
			if(System.currentTimeMillis() - session.getLastAccessed() < 1000*60*10) {
				session.setLastAccessed(System.currentTimeMillis());
				return ResponseEntity.ok(((ManagerFacade)session.getService()).getOneCompany(id));
						
			}
					
		}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized access...");
	}
}
