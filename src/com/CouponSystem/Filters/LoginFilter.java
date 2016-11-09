package com.CouponSystem.Filters;

import java.io.IOException;

import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;


@WebFilter("/rest/*")
public class LoginFilter implements Filter 
{
	private static final String FACADE_ATTRIBUTE  = "facadeAtt";
	private static final String LOGIN_PAGE  = "/views/login.html";
	private static final String CUSTOMER_SERVICE_PATH  = "/rest/customerService/login";
	private static final String COMPANY_SERVICE_PATH  = "/rest/companyService/login";
	private static final String ADMIN_SERVICE_PATH  = "/rest/adminService/login";
	
	@Override
	public void destroy() {
		System.out.println("Login filter destroied");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException 
	{

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String[] loginURI = new String[3];
        loginURI[0] = request.getContextPath() + CUSTOMER_SERVICE_PATH;
        loginURI[1] = request.getContextPath() + COMPANY_SERVICE_PATH;
        loginURI[2] = request.getContextPath() + ADMIN_SERVICE_PATH;
        
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods","GET,POST");
//        response.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
//        response.addHeader("Content-Type" , "application/json");
        
        boolean loggedIn = session != null && session.getAttribute(FACADE_ATTRIBUTE) != null;
        
        boolean loginRequest = false;
                
        for (byte i = 0; i < loginURI.length; i++)
        {
        	
        	if (request.getRequestURI().equals(loginURI[i]))
        	{
        		loginRequest = request.getRequestURI().equals(loginURI[i]);
        		break;
        	}
        }


        if (loggedIn || loginRequest)
        {
            chain.doFilter(request, response);
        } 
        else 
        {
        	//TODO: create login page
        	response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Login filter init");
	}

}
