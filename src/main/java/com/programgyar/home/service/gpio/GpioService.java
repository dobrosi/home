package com.programgyar.home.service.gpio;

import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListener;

public class GpioService {
	private static List<PinDto> pinList = null;

	public static List<PinDto> getPinList() {
		final GpioController gpio = GpioFactory.getInstance();

		if (pinList == null) {
			pinList = gpio.getProvisionedPins().stream().map(f -> new PinDto(f)).collect(Collectors.toList());
		}

		return pinList;
	}

	public static void addListenerAllPorts(GpioPinListener listener) {
		final GpioController gpio = GpioFactory.getInstance();
		gpio.getProvisionedPins().forEach(f -> f.addListener(listener));
	}

	public static void startGpio(GpioPinListener l) {
		final GpioController gpio = GpioFactory.getInstance();

		gpio.provisionDigitalInputPin(RaspiPin.GPIO_00).addListener(l);
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
	}
}