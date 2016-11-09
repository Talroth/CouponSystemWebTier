package com.CouponSystem.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.CouponSystem.FacadeException.FacadeException;

@Provider
public class FacadeExceptionMapper implements ExceptionMapper<FacadeException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Override
	public Response toResponse(FacadeException ex) {

		return Response.status(Response.Status.NOT_ACCEPTABLE)
				.entity(ex)
				.type(MediaType.APPLICATION_JSON).
				build();
	}





}
