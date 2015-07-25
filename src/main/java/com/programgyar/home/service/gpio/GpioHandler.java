package com.programgyar.home.service.gpio;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

@FunctionalInterface
public interface GpioHandler {
	public void callback(GpioPinDigitalStateChangeEvent event);
}
