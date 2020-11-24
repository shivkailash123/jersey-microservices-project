package com.mindtree.simpleapp.exception.service;

/**
 * 
 * @author M1057719
 *
 */
public class EmployeeNotFoundException extends ServiceException {

	
	private static final long serialVersionUID = 1L;
	
    //this method take exception message as parameter. And pass it to Exception class
	public EmployeeNotFoundException(String m) {
		super(m);
	}

}
