package com.programgyar.home.vaadin;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.programgyar.home.domain.PinDto;
import com.programgyar.home.service.TempService;
import com.programgyar.home.service.gpio.GpioService;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Title("HOME")
@Theme("valo")
@Push(transport = Transport.WEBSOCKET)
public class MainUI extends UI implements GpioPinListenerDigital {
	Grid pinList = new Grid();
	Button startButton = new Button("Start " + TempService.getTemp() + " °C", this::startClicked);
	Button addListenerButton = new Button("Add listener", this::addListenerClicked);

	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		pinList.setContainerDataSource(new BeanItemContainer<>(PinDto.class));

		pinList.setColumnOrder("name", "address", "value");
		pinList.setSelectionMode(Grid.SelectionMode.SINGLE);
	}

	private void buildLayout() {
		GridLayout mainLayout = new GridLayout(1, 3);
		mainLayout.setSizeFull();
		mainLayout.addComponent(pinList);
		mainLayout.addComponent(startButton);
		mainLayout.addComponent(addListenerButton);

		// Split and allow resizing
		setContent(mainLayout);
	}

	private void startClicked(Button.ClickEvent e) {
		System.out.println("start clicked  " + e);

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

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("callback " + event);
		Notification.show("gpioChange: " + event);
	}
}