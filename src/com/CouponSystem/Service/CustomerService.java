package com.CouponSystem.Service;

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

import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

@Path("/customerService")
public class CustomerService  {

	@Context
	private HttpServletRequest request;
	//private HttpServletResponse response;
	private CouponSystem mainSystem = CouponSystem.getInstance();
	private static final String FACADE_ATTRIBUTE  = "facadeAtt";
	private static final String USER_NAME  = "userName";

	@POST
	@Path("/purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String purchaseCoupon(Coupon coupon)  {		
		try 
		{
			getFacade().purchaseCoupon(coupon);
			return "ok";
		} 
		catch (FacadeException e) 
		{
			return e.getMessage();
		}
	}

	
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
				//TODO: check if new ?
				session.setAttribute(FACADE_ATTRIBUTE, customerFacade);
				session.setAttribute(USER_NAME, name);
				return "ok";		
			}
			return "fail";	
		} 
		catch (FacadeException e)
		{
			return e.getMessage();	
		}
	}
	
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

	@POST
	@Path("/logout")
	public String logout()
	{
		//TODO: in case of failure add catch
		HttpSession session = request.getSession();
		session.invalidate();
		return "ok";
	}
	
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
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getUserName")
	public String getUserName() throws FacadeException
	{
		HttpSession session = request.getSession(false);
		if (session !=null)
		{
			return  (String) session.getAttribute(USER_NAME);
		}
		else
		{
			throw new FacadeException(DAOExceptionErrorType.ADMIN_FAIL_LOGIN,"User is not connected");
		}
	}
}
