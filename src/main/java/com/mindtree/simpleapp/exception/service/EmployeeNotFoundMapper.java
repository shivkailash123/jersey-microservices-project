package com.mindtree.simpleapp.exception.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * 
 * @author M1057719
 *
 */

@Provider
public class EmployeeNotFoundMapper implements ExceptionMapper<EmployeeNotFoundException> {
	/**
	 * This method take take exception message and map to status code.
	 */
	@Override
	public Response toResponse(EmployeeNotFoundException ex) {
		return Response.status(404).entity(ex.getMessage()).type("text/plan").build();
	}

}