//package com.CouponSystem.Service;
//
//import java.net.URI;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriBuilder;
//
//import org.glassfish.jersey.client.ClientConfig;
//
//public class client 
//{
//
//	  public static void main(String[] args) {
//		    ClientConfig config = new ClientConfig();
//
//		    Client client = ClientBuilder.newClient(config);
//
//		    WebTarget target = client.target(getBaseURI());
//
////		    String response = target.path("rest").
////		              path("getCompany").
////		              request().
////		              accept(MediaType.APPLICATION_JSON).
////		              get(Response.class)
////		              .toString();
//
//
////		    String plainAnswer = 
////		        target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);
//		    String k = target.path("adminService").path("login?user=admin&password=1234").request().accept(MediaType.TEXT_PLAIN).get(String.class);
//		    //String xmlAnswer = 
//		      //  target.path("adminService").path("getCompany/33").request().accept(MediaType.APPLICATION_XML).get(String.class);
////		    String htmlAnswer= 
////		        target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class);
//
//		  //  System.out.println(response);
//		  //  System.out.println(plainAnswer);
//		    System.out.println(k);
//		    //System.out.println(xmlAnswer);
//		   // System.out.println(htmlAnswer);
//		  }
//
//		  private static URI getBaseURI() {
//		    return UriBuilder.fromUri("http://localhost:8080/CouponSystemWeb/rest").build();
//		  }
//		} 
