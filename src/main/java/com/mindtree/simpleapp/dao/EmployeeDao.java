package com.mindtree.simpleapp.dao;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mindtree.simpleapp.entity.Employee;
import com.mindtree.simpleapp.exception.service.EmployeeNotFoundException;
import com.mindtree.simpleapp.exception.service.EmployeeNotNullException;
import com.mindtree.simpleapp.exception.service.ServiceException;

public interface EmployeeDao {
	

	/**
	 * This method will return list of employee.
	 * 
	 * @return Employee
	 */
	public Collection<Employee> getEmployees();

	public ListenableFuture<Collection<Employee>> getEmployeesAsync();

	
	Employee deleteEmployee(UUID id) throws ServiceException;

	public ListenableFuture<Employee> deleteEmployeeAsync(final UUID id);

	Employee getEmployee(UUID id) throws ServiceException;

	public ListenableFuture<Employee> getEmployeeAsync(final UUID id);

	Employee addEmployee(Employee employee) throws ServiceException;
	public ListenableFuture<Employee> addEmployeeAsync(final Employee employee);

}
