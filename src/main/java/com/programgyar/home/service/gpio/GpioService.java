package com.programgyar.home.service.gpio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.programgyar.home.domain.PinDto;
import com.programgyar.home.service.HomeDatabase;

public class GpioService {
	public static Map<String, PinDto> pinMap = new HashMap<>();

	public static void addListenerAllPorts(GpioPinListener listener) {
		final GpioController gpio = GpioFactory.getInstance();
		gpio.getProvisionedPins().forEach(f -> f.addListener(listener));
	}

	public static void startGpio(GpioPinListener l) {
		final GpioController gpio = GpioFactory.getInstance();

		gpio.provisionDigitalInputPin(RaspiPin.GPIO_00).addListener(l);
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
	}

	public static GpioPin createPin(PinDto pinDto) {
		final GpioController gpio = GpioFactory.getInstance();
		GpioPin pin = null;
		Pin p = getPin(pinDto);
		if (pinDto.mode == PinMode.DIGITAL_INPUT) {
			pin = gpio.provisionDigitalInputPin(p);
		} else if (pinDto.mode == PinMode.DIGITAL_OUTPUT) {
			pin = gpio.provisionDigitalOutputPin(p);
		}
		pin.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				pinDto.state = event.getState();
			}
		});
		return pinDto.pin = pin;
	}

	private static Pin getPin(PinDto pinDto) {
		return RaspiPin.getPinByName("GPIO " + pinDto.address);
	}

	public static void createPins(List<PinDto> pinList) {
		pinList.forEach(pin -> createPin(pin));
	}

	public static void deletePin(PinDto pin) {
		final GpioController gpio = GpioFactory.getInstance();
		gpio.unprovisionPin(pin.pin);
	}

	public static List<PinDto> getPinList() {
		final GpioController gpio = GpioFactory.getInstance();
		List<PinDto> res = gpio.getProvisionedPins().stream().map(p -> new PinDto(p)).collect(Collectors.toList());
		return res;
	}
}