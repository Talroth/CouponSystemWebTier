package com.CouponSystem.Service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.http.HTTPException;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.Customer;
import com.CouponSystem.Beans.Income;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Delegator.BuisnessDelegate;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

// All services for admin user, translate request from client (angular) to server facde code and return response

@Path("/adminService")
public class AdminService 
{
	@Context
	private HttpServletRequest request;
	private CouponSystem mainSystem = CouponSystem.getInstance();
	private static final String FACADE_ATTRIBUTE  = "facadeAtt";
	private BuisnessDelegate delgator = BuisnessDelegate.getIncomeService();

	public AdminService()
	{
		
	}


	// View income activities for specific customer
	@POST
	@Path("/viewIncomeByCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCustomer(Customer customer)
	{				
		try 
		{
			return delgator.viewIncomeByCustomer(customer.getId());
		} 
		catch (FacadeException e) 
		{
			return null;
		}		
	}
	
	// View income activities for specific company
	@POST
	@Path("/viewIncomeByCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCompany(Company company)
	{
				
		try 
		{
			return delgator.viewIncomeByCompany(company.getId());
		} 
		catch (FacadeException e) 
		{
			return null;
		}
		
	}
	
	// View income activities 
	@GET
	@Path("/viewAllIncomes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Income> viewAllIncomes()
	{
		
		try 
		{
			return delgator.viewAllIncomes();
		} 
		catch (FacadeException e) 
		{
			return null;
		}				
	}
	
	// Create new customer
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer createCustomer(Customer customer) throws FacadeException {
		try 
		{
			return getFacade().createCustomer(customer);			 
		} 
		catch (FacadeException e) 
		{
			throw e;
		}
	}


	// Delete customer
	@DELETE
	@Path("/removeCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCustomer(Customer customer)  {
		try 
		{
			getFacade().removeCustomer(customer);
			return "ok";
		} 
		catch (FacadeException e) 
		{
			return e.getMessage();
		}
	}


	// Update customer details
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(Customer customer) throws FacadeException {
		try 
		{
			getFacade().updateCustomer(customer);
			return "Customer " + customer.getCustName() + " was updated successfully";
		} 
		catch (FacadeException e)
		{
			throw e;
		}
	}

	// Get customer by id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCustomer/{id}")
	public Customer getCustomer(@PathParam("id") long id)  {

			try 
			{
				return getFacade().getCustomer(id);
			} 
			catch (FacadeException e) 
			{
				return null;
			}
	}


	// Get all customers
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCustomer")
	public Collection<Customer> getAllCustomer()  throws FacadeException {
		try 
		{
			return getFacade().getAllCustomer();
		} 
		catch (FacadeException e) 
		{
			throw e;
		}
	}


	// Create new company
	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Company createCompany(Company company) throws FacadeException {

		try 
		{
			return getFacade().createCompany(company);
		} 
		catch (FacadeException e) 
		{
			throw e;
		}
	}

	// Delete company

	@DELETE
	@Path("/removeCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCompany(Company company)  
	{
		try 
		{
			getFacade().removeCompany(company);
			return "ok";
		} 
		catch (FacadeException e) 
		{
			return e.getMessage();
		}
	}

	// Update company
	@PUT
	@Path("/updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCompany(Company company) throws FacadeException {

		try 
		{
			getFacade().updateCompany(company);
			return "ok";
		} 
		catch (FacadeException e)
		{
			throw e;
		}
		
	}


	// Get company by id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCompany/{id}")
	public Company getCompany(@PathParam("id") long id) {
		try 
		{
			return getFacade().getCompany(id);
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	
	// get all companies
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCompanies")
	public Collection<Company> getAllCompanies() throws FacadeException 
	{
		try 
		{
			return getFacade().getAllCompanies();
		} 
		catch (FacadeException e) 
		{
			throw e;
		}
	}
	

	// Get all coupons
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCoupons/")
	public Collection<Coupon> getCoupons(Customer customer)  {	
		try 
		{
			return getFacade().getCoupons(customer);
		} 
		catch (FacadeException e) 
		{
			return null;
		}
	}

	// admin login
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
		
	public String login(@DefaultValue("none") @QueryParam("user") String userName, @DefaultValue("none") @QueryParam("password") String password) 
	{
		
		try 
		{
			AdminFacade adminFacade = (AdminFacade) mainSystem.login(userName,  password, "admin");
			if (adminFacade != null)
			{
				HttpSession session = request.getSession();
				session.setAttribute(FACADE_ATTRIBUTE, adminFacade);
				return "ok";		
			}
			return "fail";	
		} 
		catch (FacadeException e)
		{
			return e.getMessage();	
		}
							
	}
	
	// admin logout
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
	
	// Get admin facde object
	private AdminFacade getFacade() throws FacadeException
	{
		HttpSession session = request.getSession(false);
	
		if (session !=null)
		{
			return ((AdminFacade) session.getAttribute(FACADE_ATTRIBUTE));
		}
		else
		{
			throw new FacadeException(DAOExceptionErrorType.ADMIN_FAIL_LOGIN,"User is not connected");
		}
	}
	

}
