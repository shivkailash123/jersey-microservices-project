package com.mindtree.simpleapp.exception.service;

public class EmployeeNotNullException extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// this method take exception message as parameter. And pass it to Exception
	// class
	public EmployeeNotNullException(String m) {
			super(m);
		}
}
