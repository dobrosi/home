package com.programgyar.home.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.programgyar.home.service.HomeDatabase;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

@WebListener
public class HomeWebServletContextListener implements javax.servlet.ServletContextListener {
	final static Logger logger = LoggerFactory.getLogger(HomeWebServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Home app started. Database open.");
		HomeDatabase.open();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		HomeDatabase.commit();
		logger.info("Home app stoped. Database commit.");
	}
}