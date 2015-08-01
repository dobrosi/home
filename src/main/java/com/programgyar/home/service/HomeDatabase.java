package com.programgyar.home.service;

import com.programgyar.home.domain.Settings;
import com.programgyar.home.domain.Users;
import com.programgyar.memdb.Database;

public class HomeDatabase extends Database {
	public static Settings settings;

	public static void open() {
		Database.open();
		settings = load(Settings.class, new Settings());
	}

	public static Settings getSettings() {
		return settings;
	}

	public static Users getUsers() {
		return load(Users.class, new Users());
	}
}
