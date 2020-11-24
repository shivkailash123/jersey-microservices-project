package com.mindtree.simpleapp;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import com.mindtree.simpleapp.dao.EmployeeDao;
import com.mindtree.simpleapp.dao.daoimp.EmployeeDaoImplementation;
import com.mindtree.simpleapp.entity.Employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author M1057719
 *
 */
public class EmployeeTest extends JerseyTest {

	private UUID employee1_id;
	private UUID employee2_id;

	protected Application configure() {
		// enable(TestProperties.LOG_TRAFFIC);
		// enable(TestProperties.DUMP_ENTITY);
		final EmployeeDao dao = new EmployeeDaoImplementation();
		return new EmployeeApplication(dao);
	}

	/**
	 * Initializing parameter
	 */
	@Before
	public void setupEmployee() {
		employee1_id = addEmployee("shiv", 24000, 24, "engineer").readEntity(Employee.class).getId();
		employee2_id = addEmployee("vinay", 24000, 24, "engineer").readEntity(Employee.class).getId();
	}

	/**
	 * Making response body to return this.
	 * 
	 * @param name
	 * @param salary
	 * @param age
	 * @param desg
	 * @return
	 */
	protected Response addEmployee(String name, double salary, int age, String desg) {
		Employee employee = new Employee();
		employee.setAge(age);
		employee.setDesg(desg);
		employee.setName(name);
		employee.setSalary(salary);
		Entity<Employee> empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE);
		return (target("employee").request().post(empEntity));
	}

	protected HashMap<UUID, Object> toHashMap(Response response) {
		return (response.readEntity(new GenericType<HashMap<UUID, Object>>() {
		}));
	}

	/**
	 * Testing Employee method for particular id.
	 */
	@Test
	public void testAddEmployee() {
		Employee emp = new Employee();
		emp.setAge(24);
		emp.setDesg("Engineer");
		emp.setName("shiv");
		emp.setSalary(24000);
		Entity<Employee> empEntity = Entity.entity(emp, MediaType.APPLICATION_JSON_TYPE);
		Response response = target("employee").request().post(empEntity);
		assertEquals(200, response.getStatus());
		Employee responseEmployee = response.readEntity(Employee.class);
		assertNotNull(responseEmployee.getId());
		assertEquals("shiv", responseEmployee.getName());

	}

	@Test
	public void testEmployeeWithNullValue() {
		Employee emp = null;
		Entity<Employee> empEntity = Entity.entity(emp, MediaType.APPLICATION_JSON_TYPE);
		Response response = target("employee").request().post(empEntity);
		System.out.println(response.getStatus());
	}

	@Test
	public void fetchingEmployeeData() {

		Collection<Employee> response = target("employee").request().get(new GenericType<Collection<Employee>>() {
		});
		System.out.println("checking "+response);
	
	}

	/**
	 * Testing employee method and checking response is not null.
	 */
	@Test
	public void testGetEmployee() {
		Employee response = target("employee").path(employee1_id.toString()).request().get(Employee.class);
		assertNotNull(response);
	}

	/**
	 * Testing collection of employee method.
	 */
	@Test
	public void testGetEmployeeSize() {
		Collection<Employee> response = target("employee").request().get(new GenericType<Collection<Employee>>() {
		});
		assertEquals(2, response.size());
	}
//    @Test
//    public void testDao() {
//    	Employee response1 = target("employee").path("1").request().get(Employee.class);
//    	Employee response2 = target("employee").path("1").request().get(Employee.class);
//    	assertEquals(response1.getAge(), response2.getAge());
//    }

	/**
	 * checking employee name is present or not
	 */
	@Test
	public void missingEmployeeName() {
		Response response = addEmployee(null, 24000, 24, "engineer");
		assertEquals(400, response.getStatus());
		String message = response.readEntity(String.class);
		assertTrue(message.contains("name is required"));
	}

	/**
	 * checking if employee present with particular id or not.
	 */

	@Test
	public void employeeNotFoundForDelete() {
		Response response = target("employee").path("92318e54-c6a7-47b7-b7b6-6da556a7e5fc").request().delete();
		assertEquals(404, response.getStatus());
		String message = response.readEntity(String.class);
		System.out.println(message);
		assertTrue(message.contains("Employee 92318e54-c6a7-47b7-b7b6-6da556a7e5fc Not found"));
	}

	@Test
	public void employeeDelete() {
		Employee response = target("employee").path(employee1_id.toString()).request().delete(Employee.class);
		assertNotNull(response);
	}

	@Test
	public void EmployeeNotFoundWithMessage() {
		Response response = target("employee").path("92318e54-c6a7-47b7-b7b6-6da556a7e5fc").request().get();
		assertEquals(404, response.getStatus());
		String message = response.readEntity(String.class);
		System.out.println(message);
		assertTrue(message.contains("Employee 92318e54-c6a7-47b7-b7b6-6da556a7e5fc Not found"));
	}
}
