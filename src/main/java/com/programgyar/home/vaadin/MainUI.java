package com.programgyar.home.vaadin;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.programgyar.home.service.TempService;
import com.programgyar.home.service.gpio.GpioService;
import com.programgyar.home.service.gpio.PinDto;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("HOME")
@Theme("valo")
@Push(transport = Transport.WEBSOCKET)
public class MainUI extends UI {
public static boolean virgin = true;
	Grid pinList = new Grid();
	Button startButton = new Button("Start " + TempService.getTemp() + " °C", this::startClicked);
	Button addListenerButton = new Button("Add listener", this::addListenerClicked);

	@Override
	protected void init(VaadinRequest request) {
		if(virgin) {
			startGpio();
			virgin = false;
		}
		configureComponents();
		buildLayout();
	}

	private void startGpio() {
		GpioService.startGpio();
	}

	private void configureComponents() {
		pinList.setContainerDataSource(new BeanItemContainer<>(PinDto.class));

		pinList.setColumnOrder("name", "address", "value");
		pinList.setSelectionMode(Grid.SelectionMode.SINGLE);
	}

	private void buildLayout() {
		VerticalLayout mainLayout = new VerticalLayout(pinList, startButton, addListenerButton);
		mainLayout.setSizeFull();

		// Split and allow resizing
		setContent(mainLayout);
	}

	private void startClicked(Button.ClickEvent e) {
		System.out.println("start clicked  " + e);
		// GpioListener.run(this::gpioChange);

		refreshData();
	}

	private void addListenerClicked(Button.ClickEvent e) {
		GpioService.addListenerAllPorts(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				refreshData();
			}
		});
	}

	private void refreshData() {
		pinList.setContainerDataSource(new BeanItemContainer<>(PinDto.class, GpioService.getPinList()));
	}

	private void gpioChange(GpioPinDigitalStateChangeEvent event) {
		System.out.println("callback " + event);
	}
}
