package com.mindtree.simpleapp.dao.daoimp;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mindtree.simpleapp.dao.EmployeeDao;
import com.mindtree.simpleapp.entity.Employee;
import com.mindtree.simpleapp.exception.service.EmployeeNotFoundException;
import com.mindtree.simpleapp.exception.service.EmployeeNotNullException;
import com.mindtree.simpleapp.exception.service.ServiceException;

public class EmployeeDaoImplementation implements EmployeeDao {
	private Map<UUID, Employee> employees;
	private ListeningExecutorService service;

	public EmployeeDaoImplementation() {
		employees = new ConcurrentHashMap<UUID, Employee>();
		service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
	}

	/**
	 * This method will return list of employee.
	 * 
	 * @return Employee
	 */
	public Collection<Employee> getEmployees() {
		return (employees.values());
	}

	public ListenableFuture<Collection<Employee>> getEmployeesAsync() {
		ListenableFuture<Collection<Employee>> future = service.submit(new Callable<Collection<Employee>>() {
			public Collection<Employee> call() throws Exception {
				return getEmployees();
			}
		});
		return future;
	}

	/**
	 * This method takes an id as parameter and if employee is not present to that
	 * particular id then it will through an exception otherwise wise fetch the data
	 * and return it.
	 * 
	 * @param id
	 * @return
	 * @throws EmployeeNotFoundException
	 */
	public Employee deleteEmployee(UUID id) throws ServiceException {

		if (employees.containsKey(id)) {
			Employee reValue = employees.get(id);
			employees.remove(id);
			return reValue;
		} else {
			// System.out.println("Employee "+id+" Not found");
			throw new EmployeeNotFoundException("Employee " + id + " Not found");
		}

	}

	public ListenableFuture<Employee> deleteEmployeeAsync(final UUID id) {
		ListenableFuture<Employee> future = service.submit(new Callable<Employee>() {
			public Employee call() throws Exception {
				return deleteEmployee(id);
			}
		});
		return future;
	}

	/**
	 * this method take id as parameter and retun that particular employee
	 * 
	 * @param id
	 * @return employee detail
	 * @throws EmployeeNotFoundException
	 */
	public Employee getEmployee(UUID id) throws ServiceException {

		if (employees.containsKey(id)) {
			System.out.println(employees);
			return (employees.get(id));
		} else {
			// System.out.println("Employee "+id+" Not found");
			throw new EmployeeNotFoundException("Employee " + id + " Not found");
		}

	}

	public ListenableFuture<Employee> getEmployeeAsync(final UUID id) {
		ListenableFuture<Employee> future = service.submit(new Callable<Employee>() {
			public Employee call() throws Exception {
				return getEmployee(id);
			}
		});
		return future;
	}

	public Employee addEmployee(Employee employee) throws ServiceException {
		if (employee != null) {
			employee.setId(UUID.randomUUID());
			employees.put(employee.getId(), employee);
			return employee;
		}else {
			throw new EmployeeNotNullException("Employee data should not be null");
		}

	}

	public ListenableFuture<Employee> addEmployeeAsync(final Employee employee) {
		ListenableFuture<Employee> future = service.submit(new Callable<Employee>() {
			public Employee call() throws Exception {
				return addEmployee(employee);
			}
		});
		return future;
	}

}
