//package com.CouponSystem.Service;
//
//import javax.ws.rs.ext.Provider;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//
//
////TODO: need to unregister the jackson exception mapper and replace it with this one
//@Provider
//public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
//
//	@Override
//	public Response toResponse(IllegalArgumentException ex) {
//		System.out.println("Inside exception mapper for illegal argument");
//		return Response.status(Response.Status.NOT_ACCEPTABLE)
//				.entity(ex)
//				.type(MediaType.APPLICATION_JSON).
//				build();
//	}
//
//}
