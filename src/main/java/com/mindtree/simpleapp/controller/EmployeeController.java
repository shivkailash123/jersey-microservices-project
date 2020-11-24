package com.mindtree.simpleapp.controller;

import java.util.Collection;
import java.util.UUID;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mindtree.simpleapp.dao.EmployeeDao;
import com.mindtree.simpleapp.entity.Employee;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

/**
 * 
 * @author M1057719
 *
 */
//Rest Endpoint
@Path("/employee")
public class EmployeeController {

	@Context
	EmployeeDao dao;

	/**
	 * This method return list of Employee using Asynchronous method.
	 * 
	 * @param response
	 */
	@GET
	@Produces({ "application/json;qs=1", "application/xml;qs=0.5" })
	@ManagedAsync
	public void getEmployee(@Suspended final AsyncResponse response) {
		response.resume(dao.getEmployees());
		ListenableFuture<Collection<Employee>> employeeFuture = dao.getEmployeesAsync();
		Futures.addCallback(employeeFuture, new FutureCallback<Collection<Employee>>() {
			public void onSuccess(Collection<Employee> addedEmployee) {
				response.resume(addedEmployee);
			}
			public void onFailure(Throwable thrown) {
				response.resume(thrown);
			}
		});
	}

	/**
	 * This method return specific employee detail using ID as a path parameter. If
	 * employee details is not found then it will throw an exception. Employee not
	 * found
	 * 
	 * @param id
	 * @param response
	 */
	@Path("/{id}")
	@GET
	@Produces({ "application/json;qs=1", "application/xml;qs=0.5" })
	@ManagedAsync
	public void getEmployee(@PathParam("id") UUID id, @Suspended final AsyncResponse response) {
		ListenableFuture<Employee> employeeFuture = dao.getEmployeeAsync(id);
		Futures.addCallback(employeeFuture, new FutureCallback<Employee>() {
			public void onSuccess(Employee addedEmployee) {
				response.resume(addedEmployee);
			}

			public void onFailure(Throwable thrown) {
				response.resume(thrown);
			}
		});
	}

	/**
	 * This method take id as parameter and delete that particular employee from he
	 * record if that employee is not found then it will through an exception.
	 * @param id
	 * @param response
	 */
	@Path("/{id}")
	@DELETE
	@Produces({ "application/json;qs=1", "application/xml;qs=0.5" })
	@ManagedAsync
	public void deleteEmployee(@PathParam("id") UUID id, @Suspended final AsyncResponse response) {
		ListenableFuture<Employee> employeeFuture = dao.deleteEmployeeAsync(id);
		Futures.addCallback(employeeFuture, new FutureCallback<Employee>() {
			public void onSuccess(Employee deletedEmployee) {
				response.resume(deletedEmployee);
			}

			public void onFailure(Throwable thrown) {
				response.resume(thrown);
			}
		});
	}
	/**
	 * This method take Employee data as parameter and add data to the list. By
	 * using Asynchronous method
	 * @param employee
	 * @param response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void addEmployee(@Valid Employee employee, @Suspended final AsyncResponse response) {
		ListenableFuture<Employee> employeeFuture = dao.addEmployeeAsync(employee);
		Futures.addCallback(employeeFuture, new FutureCallback<Employee>() {
			public void onSuccess(Employee addedEmployee) {
				response.resume(addedEmployee);
			}

			public void onFailure(Throwable thrown) {
				response.resume(thrown);
			}
		});
	}

}
