package com.bgip.controller;

import static javax.ws.rs.core.Response.status;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.bgip.model.ResponseBean;



public class BaseController {
	
	
	 public void buildErrorResponse(final Response.Status x, final String errorCode, final String message) throws WebApplicationException {
	        ResponseBean response  = new ResponseBean(errorCode, message);
	        
	        throw new WebApplicationException(status(x).entity(response).build());

	    }

}
