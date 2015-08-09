package com.programgyar.home.vaadin;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.programgyar.home.domain.PinDto;
import com.programgyar.home.service.gpio.GpioService;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("HOME")
@Theme("valo")
@Push(transport = Transport.WEBSOCKET)
public class MainUI extends UI implements GpioPinListenerDigital {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1431076197221681802L;
	Grid pinList = new Grid();
	Button refreshButton = new Button("Refresh");
	Button newPinButton = new Button("New pin");
	Button deletePinButton = new Button("Delete pin");

	PinForm contactForm = new PinForm();

	@Override
	protected void init(VaadinRequest request) {
		VaadinSession.getCurrent().setErrorHandler(new ErrorHandler() {

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				event.getThrowable().printStackTrace();
				Notification.show(event.getThrowable().getCause().getLocalizedMessage(), Type.ERROR_MESSAGE);
			}
		});

		configureComponents();
		buildLayout();
		// refreshPins();
	}

	private void configureComponents() {
		setResponsive(true);
		pinList.setResponsive(true);

		refreshButton = new Button("Refresh", this::refreshPins);
		newPinButton = new Button("New pin", this::newPin);
		deletePinButton = new Button("Delete pin", this::deletePin);

		pinList.setContainerDataSource(new BeanItemContainer<>(PinDto.class));

		pinList.setColumnOrder("name", "address", "state");
		pinList.setSelectionMode(Grid.SelectionMode.SINGLE);
		pinList.addSelectionListener(e -> selectedRow(e));
		pinList.setSelectionMode(SelectionMode.SINGLE);
	}

	private void selectedRow(SelectionEvent e) {
		PinDto selected = (PinDto) pinList.getSelectedRow();
		contactForm.edit(selected);
		deletePinButton.setEnabled(selected != null);
	}

	private void buildLayout() {

		HorizontalLayout actions = new HorizontalLayout(refreshButton, newPinButton);
		actions.setWidth("100%");
		actions.setExpandRatio(newPinButton, 1);

		VerticalLayout left = new VerticalLayout(actions, pinList);
		left.setSizeFull();
		pinList.setSizeFull();
		left.setExpandRatio(pinList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		setContent(mainLayout);
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("callback " + event);
		Notification.show("gpioChange: " + event);
	}

	public void refreshPins(ClickEvent e) {
		pinList.setContainerDataSource(new BeanItemContainer<>(PinDto.class, GpioService.getPinList()));
		contactForm.setVisible(false);
	}

	public void newPin(ClickEvent e) {
		contactForm.edit(new PinDto());
	}
	
	public void deletePin(ClickEvent e) {
		GpioService.deletePin((PinDto)pinList.getSelectedRow());
	}
}