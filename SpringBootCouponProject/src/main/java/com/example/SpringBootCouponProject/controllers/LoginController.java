package com.example.SpringBootCouponProject.controllers;

import java.util.Map;
import java.util.UUID;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootCouponProject.facades.ClientFacade;
import com.example.SpringBootCouponProject.facades.CompanyFacade;
import com.example.SpringBootCouponProject.facades.CustomerFacade;
import com.example.SpringBootCouponProject.facades.ManagerFacade;

@RestController
@RequestMapping("login")
@CrossOrigin(origins="http://localhost:4200")
public class LoginController {
	
	// no need to return response entity, return customer and spring will do the rest.

	private Map<String, Session> sessions;
	private ConfigurableApplicationContext ctx;

	
	public LoginController(Map<String, Session> sessions, ConfigurableApplicationContext ctx) {
		super();
		this.sessions = sessions;
		this.ctx = ctx;
	}


	// without path variable it will look like that //login?email=ZZZ&password=AAA&type=SSS
	@PostMapping("{email}/{password}/{type}") 
	public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password, @PathVariable String type){
		if(type.equals("admin")) {
			ManagerFacade service = ctx.getBean(ManagerFacade.class);
			if(service.login(email, password)) {
				// login successful
				return ResponseEntity.ok(addSession(service));
			}
		} else if (type.equals("customer")){
			CustomerFacade service = ctx.getBean(CustomerFacade.class);
			if(service.login(email, password)) {
				// login successful
				return ResponseEntity.ok(addSession(service));
			}
			
		} else {
			CompanyFacade service = ctx.getBean(CompanyFacade.class);
			if(service.login(email, password)) {
				// login successful
				return ResponseEntity.ok(addSession(service));
			
	    	 }
    	}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invaild Login!");
   }
	
	@PostMapping("logout")
	public void logout(@RequestBody String token){
		sessions.remove(token);
	}
	
	// session method
	private String addSession(ClientFacade service) {
		String token = UUID.randomUUID().toString();
		 // add new session with that token that contains the service with the current time.
		sessions.put(token, new Session(service, System.currentTimeMillis())); 
		return token; // return the token to the customer with his identifier.
	}
	
}
