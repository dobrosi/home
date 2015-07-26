package com.programgyar.home.service.gpio;

import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.event.GpioPinListener;

public class GpioService {
	public static List<PinDto> getPinList() {
		final GpioController gpio = GpioFactory.getInstance();

		return gpio.getProvisionedPins().stream().map(f -> new PinDto(f)).collect(Collectors.toList());
	}

	public static void addListenerAllPorts(GpioPinListener listener) {
		final GpioController gpio = GpioFactory.getInstance();

		gpio.getProvisionedPins().forEach(f -> f.addListener(listener));
	}
}
