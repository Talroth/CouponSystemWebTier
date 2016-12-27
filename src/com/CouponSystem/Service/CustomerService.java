package com.CouponSystem.Service;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.http.HTTPException;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.Beans.Customer;
import com.CouponSystem.Beans.Income;
import com.CouponSystem.Beans.IncomeType;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Delegator.BuisnessDelegate;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

//All services for customer user, translate request from client (angular) to server facde code and return response

@Path("/customerService")
public class CustomerService  {

	@Context
	private HttpServletRequest request;
	private CouponSystem mainSystem = CouponSystem.getInstance();
	private static final String FACADE_ATTRIBUTE  = "facadeAtt";
	private BuisnessDelegate delgator = BuisnessDelegate.getIncomeService();

	// get only specific coupon, valid coupon is one which was purchased by current customer (according to fsesion details)
	@POST
	@Path("/purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String purchaseCoupon(Coupon coupon)  {		
		try 
		{
			CustomerFacade curCust = getFacade();
			curCust.purchaseCoupon(coupon);
			
			delgator.storeIncome(new Income(curCust.getMe().getCustName(), LocalDateTime.now(), IncomeType.CUSTOMER_PURCHASE, coupon.getPrice(), curCust.getMe().getId(), curCust.getCompanyIdByCouponId(coupon.getId())));
			
			return "ok";
		} 
		catch (FacadeException e) 
		{
			return e.getMessage();
		}
	}

	// Return all purchased coupons by current customer
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllPurchasedCoupons")
	public Collection<Coupon> getAllPurchasedCoupons()  {
		try 
		{
			return getFacade().getAllPurchasedCoupons();
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	// Return all purchased coupons (by type) by current customer
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllPurchasedCouponsByType/{couponType}")
	public Collection<Coupon> getAllPurchasedCouponsByType(@PathParam("couponType") CouponType type)  {		
		try 
		{
			return getFacade().getAllPurchasedCouponsByType(type);
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	// Return all purchased coupons (by max price) by current customer
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllPurchasedCouponsByPrice/{price}")
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@PathParam("price") double price)  {		
		try 
		{
			return getFacade().getAllPurchasedCouponsByPrice(price);
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}


	// customer login
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public String login(@DefaultValue("none") @QueryParam("user") String name, @DefaultValue("none") @QueryParam("password") String password)  {
		try 
		{
			CustomerFacade customerFacade = (CustomerFacade) mainSystem.login(name,  password, "customer");
			if (customerFacade != null)
			{
				HttpSession session = request.getSession();
				session.setAttribute(FACADE_ATTRIBUTE, customerFacade);
				return "ok";		
			}
			return "fail";	
		} 
		catch (FacadeException e)
		{
			return e.getMessage();	
		}
	}
	
	// Get all coupons not only purchased
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCoupons")
	public Collection<Coupon> getAllCoupons() {
		try 
		{
			return getFacade().getAllCoupons();
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	// get specific coupon by id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCoupon/{id}")
	public Coupon getCoupon(@PathParam("id") long id)   {

		try 
		{
			return getFacade().getCoupon(id);
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	// customer logout
	@POST
	@Path("/logout")
	public String logout()
	{
		try
		{
			HttpSession session = request.getSession();
			if (session != null)
			{
				session.invalidate();
				return "ok";
			}
			else
			{
				return "User connection was not found";
			}
		}
		catch (HTTPException e)
		{
			return "User connection was not found";
		}
	}
	
	// get customer facade
	private CustomerFacade getFacade() throws FacadeException
	{
		HttpSession session = request.getSession(false);
		if (session !=null)
		{
			return ((CustomerFacade) session.getAttribute(FACADE_ATTRIBUTE));
		}
		else
		{
			throw new FacadeException(DAOExceptionErrorType.ADMIN_FAIL_LOGIN,"User is not connected");
		}
	}
	
}
