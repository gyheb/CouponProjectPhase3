package com.example.SpringBootCouponProject.facades;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Company;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCouponIdOrCompanyIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExpiredOrNoLongerInStockException;

@Service // Component
public class CompanyFacade extends ClientFacade {
	/**
	 * no need to auto wire the repositories
	 * because Client Facade has them all
	 * 
	 * */
	
	public long companyId;
	
	// ==================== login ===================== \\
	
	public boolean login(String email, String password) {
		if(compRepo.existsByEmail(email) && compRepo.existsByPassword(password)) {
			for (Company comp : compRepo.findAll()) {
				if(comp.getEmail().equals(email) && comp.getPassword().equals(password)) {
					companyId = comp.getCompanyId();
					return true;
				}
			}
			
		}
		return false;
		
	}
	
	// =================== adding methods ================== \\
	/**
	 * 1. Can't add a coupon with the same title
	 * 
	 * */
	
	public void addCoupon(Coupon coupon) throws CompanyNotFoundException, CouponExistsException {
		Company comp = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		for (Coupon coup : comp.getCoupons()) {
			if(coup.getTitle().equals(coupon.getTitle())) {
				throw new CouponExistsException();
			}
		}
		coupon.setCompany(comp);
		coupRepo.save(coupon);
	}
	
	// ================== update methods =================== \\
	/**
	 * 1. Can't update coupon id
	 * 
	 * 2. Can't update coupon company id 
	 * 
	 * */
	
	public void updateCoupon(Coupon coupon) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
		Company comp = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		for (Coupon coup : comp.getCoupons()) {
			if(coup.getCompany().getCompanyId() == coupon.getCompany().getCompanyId()
					&& coup.getCouponId() == coupon.getCouponId())
				coupRepo.save(coupon);
			else
				throw new CannotUpdateCouponIdOrCompanyIdException();
		}
	}
	
	// =================== delete methods =================== \\
	/**
	 * 1. delete all customer purchases as well
	 * 
	 * */
	
	public void deleteCoupon(long couponId) {
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Customer cust : custRepo.findAll()) {
					for (Coupon coup : cust.getCoupons()) {
						if(coup.getCouponId() == couponId)
							coupRepo.delete(coup);
				
					}
				}
			}			
		}
		coupRepo.deleteById(couponId);
	}
	
	// =================== get company from coupon ================ \\
	
	public Company getCompanyFromCoupon(long couponId) throws CouponExpiredOrNoLongerInStockException {
		return coupRepo.findById(couponId).orElseThrow(CouponExpiredOrNoLongerInStockException::new).getCompany();
	}
	
	// ================= get one coupon ================ \\ 
	
	public Coupon getOneCoupon(long couponId) {
		Coupon c = null;
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Coupon coup : comp.getCoupons()) {
					if(coup.getCouponId() == couponId) {
						c = coup;
						break;
					}
				}
			}
		}
		return c;
		
	}
	
	// ================= get all methods =============== \\

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Coupon coup : comp.getCoupons()) {
					coupons.add(coup);
				}
			}
		}
		return coupons;
	}
	
	// ================ get by methods ================== \\
	
	public List<Coupon> getCouponByCategory(CategoryType type) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Coupon coup : comp.getCoupons()) {
					if(coup.getType().equals(type)) {
						coupons.add(coup);
					}
				}
			}
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsUpToPrice(double price) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Coupon coup : comp.getCoupons()) {
					if(coup.getPrice() <= price) {
						coupons.add(coup);
					}
				}
			}
		}
		return coupons;
	}
	
	
	// =================== get company details ================ \\
	
	public Company getCompanyDetails() throws CompanyNotFoundException {
		Company c = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		c.setCoupons(c.getCoupons());
		return c;
	}


}
