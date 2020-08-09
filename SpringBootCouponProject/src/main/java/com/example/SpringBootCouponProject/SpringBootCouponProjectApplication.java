package com.example.SpringBootCouponProject;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Company;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.dailyJobs.CouponExpirationDailyJob;
import com.example.SpringBootCouponProject.facades.ClientFacade;
import com.example.SpringBootCouponProject.facades.CompanyFacade;
import com.example.SpringBootCouponProject.facades.CustomerFacade;
import com.example.SpringBootCouponProject.facades.ManagerFacade;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCompanyNameOrIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCouponIdOrCompanyIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExpiredOrNoLongerInStockException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;
import com.example.SpringBootCouponProject.login.ClientType;
import com.example.SpringBootCouponProject.login.LoginManager;
import com.example.SpringBootCouponProject.login.exceptions.AccessDeniedException;

@SpringBootApplication
public class SpringBootCouponProjectApplication {

	public static void main(String[] args) throws SQLException, AccessDeniedException, CompanyNotFoundException,
	CouponExistsException, CompanyExistsException, CannotUpdateCompanyNameOrIdException, CustomerNotFoundException,
	CustomerExistsException, CannotUpdateCouponIdOrCompanyIdException, CouponExpiredOrNoLongerInStockException {	
		ConfigurableApplicationContext ctx = 
				SpringApplication.run(SpringBootCouponProjectApplication.class, args);
		
		
		// ================================== THE APP =================================== \\
		
		LoginManager manager = ctx.getBean(LoginManager.class);
		CouponExpirationDailyJob job = ctx.getBean(CouponExpirationDailyJob.class);
		new Thread(job).start();
		
		
		// ================================== ADMIN SECTION ================================== \\ 
		
		ClientFacade admin = manager.login("admin@admin.com", "admin", ClientType.Admin);
		if(admin instanceof ManagerFacade) {
			
			// add company
//			Company c = new Company("Fox-net", "sexyfoxy1@gmail.com", "dsa3"); 
//			((ManagerFacade) admin).addCompany(c);
			
			// update company + get one company
//			Company c = ((ManagerFacade) admin).getOneCompany(1);
//			c.setEmail("castro@clothes.com");
//			((ManagerFacade) admin).updateCompany(c);
			
			// get all companies
//			for (Company comp : ((ManagerFacade) admin).getAllCompanies()) {
//				System.out.println(comp);
//			}
			
			// delete company
//			((ManagerFacade) admin).deleteCompany(2);
			
			// add customer
//			Customer cu = new Customer("Rami", "Levi", "ramilevi@supermarket.com", "supersaltrash");
//			Customer cu2 = new Customer("yehuda", "poliker", "ramilevi@supermarket.com", "yoplek");
//			((ManagerFacade) admin).addCustomer(cu);
//			((ManagerFacade) admin).addCustomer(cu2);
			
			// update customer
//			Customer cu1 = ((ManagerFacade) admin).getOneCustomer(1);
//			cu1.setFirstName("ramik");
//			((ManagerFacade) admin).updateCustomer(cu1);
			
//			// get one customer
//			System.out.println(((ManagerFacade) admin).getOneCustomer(1));
//			
//			// get all customers
//			for (Customer cust : ((ManagerFacade) admin).getAllCustomers()) {
//				System.out.println(cust);
//			}
			
			// delete customer
//			((ManagerFacade) admin).deleteCustomer(1);
			
			
			
			
			// ================================== COMPANY SECTION ================================== \\ 
//			ClientFacade comp = manager.login("sexyfoxy1@gmail.com", "dsa3", ClientType.Company);
//			
//			// dates 
//			Calendar cal = Calendar.getInstance();
//			Calendar cal2 = Calendar.getInstance();
//			cal.set(2020, Calendar.JULY, 10);
//			cal2.set(2020, 8, 10);
//			
//			if(comp instanceof CompanyFacade) {
				
				// get company details
//				System.out.println( ((CompanyFacade) comp).getCompanyDetails());
				
				// add coupon
//				Coupon coup = new Coupon(CategoryType.Clothes, "Zara", "50% off sale", new Date(cal.getTimeInMillis()), new Date(cal2.getTimeInMillis()), 50, 99.90, "blah blah");
//				((CompanyFacade) comp).addCoupon(coup);
//				
//				Coupon coup2 = new Coupon(CategoryType.Clothes, "Clothes", "30 precent on all clothes", new Date(cal.getTimeInMillis()), new Date(cal2.getTimeInMillis()), 10, 39.90, "blah blah");
//				((CompanyFacade) comp).addCoupon(coup2);
				
				// update coupon 
//				Coupon coup = ((CompanyFacade) comp).getOneCoupon(2);
//				coup.setDescription("Sauna For Freee");
//				((CompanyFacade) comp).updateCoupon(coup);
				
				// get all coupons
//				for (Coupon coup : ((CompanyFacade) comp).getAllCoupons()) {
//					System.out.println(coup);
//				}
				
				// get all coupons by category
//				for (Coupon coup : ((CompanyFacade) comp).getCouponByCategory(CategoryType.Spa)) {
//					System.out.println(coup);
//				}
				
				// get all coupons up to max price
//				for (Coupon coup : ((CompanyFacade) comp).getCouponsUpToPrice(55.50)) {
//					System.out.println(coup);
//				}
				
				// delete coupon
//				((CompanyFacade) comp).deleteCoupon(4);
			
			
			
			// ================================== CUSTOMER SECTION ================================== \\ 
//			ClientFacade cust = manager.login("ramilevi@gmail.com", "12345", ClientType.Customer);
			
//			if(cust instanceof CustomerFacade) {
				
				// get customer details
//				System.out.println(((CustomerFacade) cust).getCustomerDetails());
				
				// coupon purchase
//				((CustomerFacade) cust).purchaseCoupon(((CustomerFacade) cust).getOneCoupon(1));
//				((CustomerFacade) cust).purchaseCoupon(((CompanyFacade) comp).getOneCoupon(4));
//				((CustomerFacade) cust).purchaseCoupon(((CompanyFacade) comp).getOneCoupon(3));
//				((CustomerFacade) cust).purchaseCoupon(((CompanyFacade) comp).getOneCoupon(5));
				
				// get all customer coupons
//				for (Coupon coup : ((CustomerFacade) cust).getAllCoupons()) {
//					System.out.println(coup);
//				}
				
				// get all coupons by category
//				for (Coupon coup : ((CustomerFacade) cust).getCouponsByCategory(CategoryType.Clothes)) {
//					System.out.println(coup);
//				}
				
				// get all coupons up to max price
//				for (Coupon coup : ((CustomerFacade) cust).getCouponsUpToPrice(100.90)) {
//					System.out.println(coup);
//				   }
				
//			    }
						
//		    }
	
		}
		
		job.stopJob();
		
	}

}
