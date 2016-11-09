package com.tal.Hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.DBDAO.CouponDBDAO;

import DAOException.DAOException;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

  // This method is called if TEXT_PLAIN is request
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sayPlainTextHello() {
    return "Hello Jersey";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/j")
  public Coupon sayJSONHello() {

	  CouponDBDAO cop = CouponDBDAO.getInstance();
	  
	  
	  try {
		return cop.getCoupon(30);
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
//    return "{" + 
//        "'id': 1," + 
//        "'name': 'A green door'," + 
//        "'price': 12.50," +
//        "'tags': ['home', 'green']" + 
//    "}";
  }
  
  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  @Path("x")
  public String sayXMLHello() {
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

}
