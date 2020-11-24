package com.mindtree.simpleapp;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.mindtree.simpleapp.dao.EmployeeDao;

public class EmployeeApplication extends ResourceConfig{
      public EmployeeApplication(final EmployeeDao dao) {
    	  packages("com.mindtree.simpleapp").
			register(new AbstractBinder() {
				protected void configure() {
					bind(dao).to(EmployeeDao.class);
				}
			});
		JacksonXMLProvider xml = new JacksonXMLProvider().
				configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
				configure(SerializationFeature.INDENT_OUTPUT, true);
		register(xml);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	}
}
