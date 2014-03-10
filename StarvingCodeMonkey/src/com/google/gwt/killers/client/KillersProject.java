package com.google.gwt.killers.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.GoogleMap;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KillersProject implements EntryPoint {

	Logger logger = Logger.getLogger("KillersProjectLogger");

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private VerticalPanel mainPanel = new VerticalPanel();

	private VerticalPanel mapPanel = new VerticalPanel();

	private Anchor signInLink = new Anchor("Login");
	private Anchor signOutLink = new Anchor("Logout");
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please login to the applicattion using your Google Account.");

	private FlexTable parksFlexTable = new FlexTable();
	private Button changeButtonRestaurant = new Button("Add");

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final ParkServiceAsync parkService = GWT.create(ParkService.class);
	private final RestaurantServiceAsync restaurantService = GWT
			.create(RestaurantService.class);

	private List<Park> parkList = new ArrayList<Park>();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
						handleError(error);
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							loadAppData();
						} else {
							loadLogin();
						}
					}
				});

		// //TODO Create CellTable
		//
		// // Create a CellTable.
		// final CellTable<Restaurant> table = new CellTable<Restaurant>();
		//
		// // Create name column.
		// TextColumn<Restaurant> nameColumn = new TextColumn<Restaurant>() {
		// @Override
		// public String getValue(Restaurant restaurant) {
		// return restaurant.getName();
		// }
		// };
		//
		// // Make the name column sortable.
		// nameColumn.setSortable(true);
		//
		// // Create address column.
		// TextColumn<Restaurant> addressColumn = new TextColumn<Restaurant>() {
		// @Override
		// public String getValue(Restaurant restaurant) {
		// return restaurant.getAddress();
		// }
		// };
		//
		// // Add the columns.
		// table.addColumn(nameColumn, "Name");
		// table.addColumn(addressColumn, "Address");
		//
		// // Set the total row count. You might send an RPC request to
		// determine the
		// // total row count.
		// table.setRowCount(100, true);
		//
		// // Set the range to display.
		// table.setVisibleRange(0, 3);
		//
		// // Add to rootPanel
		// RootPanel.get("content-window").add(table);
		// //
		// ------------------------------------------------------------------------
		// final Button sendButton = new Button("Send");
		// final TextBox nameField = new TextBox();
		// nameField.setText("GWT User");
		// final Label errorLabel = new Label();
		//
		// // We can add style names to widgets
		// sendButton.addStyleName("sendButton");
		//
		// // Add the nameField and sendButton to the main panel
		// mainPanel.add(nameField);
		// mainPanel.add(sendButton);
		// mainPanel.add(errorLabel);
		//
		// // Focus the cursor on the name field when the app loads
		// nameField.setFocus(true);
		// nameField.selectAll();
		//
		// // Create the popup dialog box
		// final DialogBox dialogBox = new DialogBox();
		// dialogBox.setText("Remote Procedure Call");
		// dialogBox.setAnimationEnabled(true);
		// final Button closeButton = new Button("Close");
		// // We can set the id of a widget by accessing its Element
		// closeButton.getElement().setId("closeButton");
		// final Label textToServerLabel = new Label();
		// final HTML serverResponseLabel = new HTML();
		// VerticalPanel dialogVPanel = new VerticalPanel();
		// dialogVPanel.addStyleName("dialogVPanel");
		// dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		// dialogVPanel.add(textToServerLabel);
		// dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		// dialogVPanel.add(serverResponseLabel);
		// dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		// dialogVPanel.add(closeButton);
		// dialogBox.setWidget(dialogVPanel);
		//
		//
		// // Add a handler to close the DialogBox
		// closeButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// dialogBox.hide();
		// sendButton.setEnabled(true);
		// sendButton.setFocus(true);
		// }
		// });
		//
		// // Create a handler for the sendButton and nameField
		// class MyHandler implements ClickHandler, KeyUpHandler {
		// /**
		// * Fired when the user clicks on the sendButton.
		// */
		// public void onClick(ClickEvent event) {
		// sendNameToServer();
		// }
		//
		// /**
		// * Fired when the user types in the nameField.
		// */
		// public void onKeyUp(KeyUpEvent event) {
		// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		// sendNameToServer();
		// }
		// }
		//
		// /**
		// * Send the name from the nameField to the server and wait for a
		// * response.
		// */
		// private void sendNameToServer() {
		// // First, we validate the input.
		// errorLabel.setText("");
		// String textToServer = nameField.getText();
		// if (!FieldVerifier.isValidName(textToServer)) {
		// errorLabel.setText("Please enter at least four characters");
		// return;
		// }
		//
		// // Then, we send the input to the server.
		// sendButton.setEnabled(false);
		// textToServerLabel.setText(textToServer);
		// serverResponseLabel.setText("");
		// greetingService.greetServer(textToServer,
		// new AsyncCallback<String>() {
		// public void onFailure(Throwable caught) {
		// // Show the RPC error message to the user
		// dialogBox
		// .setText("Remote Procedure Call - Failure");
		// serverResponseLabel
		// .addStyleName("serverResponseLabelError");
		// serverResponseLabel.setHTML(SERVER_ERROR);
		// dialogBox.center();
		// closeButton.setFocus(true);
		// }
		//
		// public void onSuccess(String result) {
		// dialogBox.setText("Remote Procedure Call");
		// serverResponseLabel
		// .removeStyleName("serverResponseLabelError");
		// serverResponseLabel.setHTML(result);
		// dialogBox.center();
		// closeButton.setFocus(true);
		// }
		// });
		// }
		// }
	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("content-window").add(loginPanel);
	}

	private void loadAppData() {

		// Listen for mouse events on the Add button.
		changeButtonRestaurant.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				changeRestaurant();
			}
		});

		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());

		// Create table for stock data.
		parksFlexTable.setText(0, 0, "Name");
		parksFlexTable.setText(0, 1, "Address");
		parksFlexTable.setText(0, 2, "Neighbourhood");

		// Add styles to elements in the stock list table.
		parksFlexTable.setCellPadding(6);

		// Add styles to elements in the stock list table.
		parksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		parksFlexTable.addStyleName("watchList");
		loadParks();

		// Assemble Main panel.
		mainPanel.add(signOutLink);
		mainPanel.add(changeButtonRestaurant);
		mainPanel.add(parksFlexTable);

		MapOptions options = MapOptions.create();
		options.setCenter(LatLng.create(49.195944, 123.1775715));
		options.setZoom(10);
		options.setMapTypeId(MapTypeId.ROADMAP);
		options.setDraggable(true);
		options.setMapTypeControl(true);
		options.setScaleControl(true);
		options.setScrollwheel(true);

		SimplePanel widg = new SimplePanel();
		widg.setSize("600px", "500px");

		GoogleMap theMap = GoogleMap.create(widg.getElement(), options);

		mainPanel.add(widg);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("content-window").add(mainPanel);
	}

	private void loadParks() {
		parkService.getParks(new AsyncCallback<List<Park>>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			@Override
			public void onSuccess(List<Park> result) {
				int size = -1;
				if (result != null) {
					size = result.size();
				}
				logger.log(Level.INFO, "Number of loaded parks: " + size);
				parkList = new ArrayList<Park>(result);
				displayParks(result);
			}
		});
	}

	private void displayParks(List<Park> parks) {
		for (Park park : parks) {
			displayPark(park);
		}
	}

	private void displayPark(final Park obj) {
		// Add the park to the table.
		int row = parksFlexTable.getRowCount();
		final String parkId = obj.getId();

		parksFlexTable.setText(row, 1, obj.getAddress());
		parksFlexTable.setText(row, 2, obj.getNeighbourhood());

		// Add a button to remove this stock from the table.
		Anchor parkName = new Anchor(obj.getName());
		parkName.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Park park = selectPark(parkId);
				if (park != null) {
					logger.log(Level.INFO, "Selected park " + park.getName()
							+ " with lat/lon " + park.getLatitude() + "/"
							+ park.getLongitude());
				} else {
					logger.log(Level.SEVERE, "No park was found");
				}
			}
		});
		parksFlexTable.setWidget(row, 0, parkName);
	}

	private Park selectPark(final String parkId) {
		for (Park p : parkList) {
			if (parkId.equals(p.getId())) {
				return p;
			}
		}
		return null;
	}

	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		} else {
			Label errorLabel = new Label(SERVER_ERROR);
			mainPanel.add(errorLabel);
		}
	}

	private void changeRestaurant() {
		parksFlexTable.setText(0, 1, "Status");
		parksFlexTable.setText(0, 2, "Address");
		loadRestaurants();
	}

	private void loadRestaurants() {
		restaurantService.getRestaurants(new AsyncCallback<List<Restaurant>>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			@Override
			public void onSuccess(List<Restaurant> result) {
				displayRestaurants(result);
			}
		});
	}

	private void displayRestaurants(List<Restaurant> restaurants) {
		for (Restaurant restaurant : restaurants) {
			displayRestaurant(restaurant);
		}
	}

	private void displayRestaurant(final Restaurant obj) {
		// Add the park to the table.
		int row = parksFlexTable.getRowCount();
		parksFlexTable.setText(row, 0, obj.getName());
		parksFlexTable.setText(row, 1, obj.getstatus());
		parksFlexTable.setText(row, 2, obj.getAddress());
	}
}
