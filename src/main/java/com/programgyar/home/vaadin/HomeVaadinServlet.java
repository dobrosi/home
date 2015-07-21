package com.programgyar.home.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = HomeUI.class)
public class HomeVaadinServlet extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2047602391674106706L;
}