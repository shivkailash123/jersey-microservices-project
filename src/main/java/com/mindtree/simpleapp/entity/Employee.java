package com.mindtree.simpleapp.entity;

import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author M1057719
 * Employee entity class
 */
public class Employee {
	/*
	 * Employee class hold all parameter like name, salary,age and designation
	 */
	private UUID id;
	@NotNull(message="name is required")
	private String name;
	@NotNull(message="salary is required")
	private double salary;
	private int age;
	private String desg;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", age=" + age + ", desg=" + desg + "]";
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID uuid) {
		this.id = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDesg() {
		return desg;
	}

	public void setDesg(String desg) {
		this.desg = desg;
	}

}