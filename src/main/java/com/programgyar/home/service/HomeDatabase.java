package com.programgyar.home.service;

import com.programgyar.home.domain.Settings;
import com.programgyar.home.domain.Users;
import com.programgyar.memdb.Database;
import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;

public class HomeDatabase extends Database {
	final static Logger logger = LoggerFactory.getLogger(HomeDatabase.class);
	public static Settings settings;
	public static Users users;

	public static void open() {
		Database.open();
		settings = load(Settings.class, new Settings());
		users = load(Users.class, new Users());
		
		settings.counter++;
		logger.info("counter: " + settings.counter);
	}

	public static void commit() {
		save(Settings.class, settings);
		Database.commit();
	}
}