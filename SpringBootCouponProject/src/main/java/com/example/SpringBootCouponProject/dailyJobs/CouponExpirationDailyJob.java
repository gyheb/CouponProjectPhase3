package com.example.SpringBootCouponProject.dailyJobs;

import java.sql.Date;
import java.util.Calendar;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.database.CouponRepository;
import com.example.SpringBootCouponProject.database.CustomerRepository;

@Service
public class CouponExpirationDailyJob implements Runnable {
	
	// can do @Autowired without CTOR or CTOR without @Autowired.
	// its better to do a CTOR cause when you try to initialize the object (new)
	// you'll get an alert cause you wont have the variables to insert
	// unlike @Autowired 

	//@Autowired
	private CouponRepository coupRepo;
	//@Autowired
	private CustomerRepository custRepo;
	
	
	
	
	public CouponExpirationDailyJob(CouponRepository coupRepo, CustomerRepository custRepo) {
		super();
		this.coupRepo = coupRepo;
		this.custRepo = custRepo;
	}

	private boolean quit;

	@Override
	public void run() {

		while (!quit) {
			Calendar cal = Calendar.getInstance();
			
			for (Coupon coupon : coupRepo.findAll()) { // run on all coupons.
				if (coupon.getEndDate().before(new Date(cal.getTimeInMillis()))) { // if the coupon date expired
					for (Customer cust : custRepo.findAll()) { // run on all customers
						for (Coupon coup : cust.getCoupons()) { // run on each customer's coupons
							if (coup.getCouponId() == coupon.getCouponId()) // if the the coupon has the same id as the coupon that expired
								coupRepo.delete(coup); // delete the customer's coupon

						}
					}
					System.out.println("The daily job deleted the coupon with the following id: " + coupon.getCouponId());
					coupRepo.deleteById(coupon.getCouponId()); // delete coupon
				}

			}
		}

		try {
			Thread.sleep(24 * 3600); // go to sleep for 24 hours
		} catch (InterruptedException e) {
			e.getMessage();
		}

	}

	public void stopJob() { 
		quit = true;
	}

}
