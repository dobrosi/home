package com.programgyar.home.service;

import java.io.File;
import java.io.IOException;

import com.google.gwt.thirdparty.guava.common.base.Charsets;
import com.google.gwt.thirdparty.guava.common.io.Files;

public class TempService {
	public static Double getTemp() {
		File f = new File("/sys/bus/w1/devices/28-00000636f3bc/w1_slave");

		try {
			String str = Files.toString(f, Charsets.UTF_8);
			return new Double(Double.parseDouble(str.split("=")[2]) / 1000D);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
