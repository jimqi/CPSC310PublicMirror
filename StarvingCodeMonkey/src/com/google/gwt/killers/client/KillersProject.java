package com.google.gwt.killers.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.killers.entity.UserLocation;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Polyline;
import com.google.maps.gwt.client.PolylineOptions;

//import com.google.gwt.maps.client.overlay.Marker;

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

	private VerticalPanel logoutPanel = new VerticalPanel();

	private TabPanel mainPanel = new TabPanel();
	// private TabLayoutPanel mainPanel = new TabLayoutPanel(2, Unit.EM);

	private Anchor signInLink = new Anchor("Login");
	private Anchor signOutLink = new Anchor("Logout");
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please login to the applicattion using your Google Account.");

	private int parkTableSortColumn = 0;
	private boolean parkTableReverseSort = false;

	private FlexTable parksFlexTable = new FlexTable();
	private FlexTable restaurantFlexTable = new FlexTable();
	private FlexTable favoriteRestaurantTable = new FlexTable();
	private FlexTable favoriteParkTable = new FlexTable();


	private HorizontalPanel BoxPanel = new HorizontalPanel();
	private TextBox userNumTextBox = new TextBox();
	private TextBox userRdTextBox = new TextBox();
	private Button searchUser = new Button("search");
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private List<Park> parks = new ArrayList<Park>();

	private GoogleMap map;
	private List<Marker> markers = new ArrayList<Marker>();
	private List<Polyline> polylines = new ArrayList<Polyline>();

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
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

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
							loadBoxes();
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

	UserLocation ul;

	private void loadBoxes() {

		// text boxes for user location
		BoxPanel.add(userNumTextBox);
		BoxPanel.add(userRdTextBox);
		BoxPanel.add(searchUser);
		RootPanel.get("box-window").add(BoxPanel);
		userNumTextBox.setFocus(true);
		userRdTextBox.setFocus(true);

		// listen for mouse click on search key (to find user location)
		searchUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				final String num = userNumTextBox.getText().trim();
				userNumTextBox.setFocus(true);
				final String rd = userRdTextBox.getText();
				userRdTextBox.setFocus(true);
				ul = new UserLocation(num, rd);
				// logger.log(Level.INFO, "LatLong" + " " + ul.getLat() + "," +
				// ul.getLong());

			}
		});
	}

	private void loadAppData() {
		// Assemble Main panel.
		mainPanel.add(parksFlexTable, "Parks");
		mainPanel.add(restaurantFlexTable, "Restaurants");
		mainPanel.add(favoriteRestaurantTable, "Favorite Restaurant");
		mainPanel.add(favoriteParkTable, "Favorite Park");

		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());
		signOutLink.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		logoutPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		logoutPanel.add(signOutLink);
		logoutPanel.setSpacing(15);

		Anchor parkName = new Anchor("Name");
		parkName.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (0 == parkTableSortColumn) {
					parkTableReverseSort = !parkTableReverseSort;
				} else {
					parkTableSortColumn = 0;
					parkTableReverseSort = false;
				}
				String text = parkTableReverseSort ? "Name &#9660;"
						: "Name &#9650;";
				Anchor col = (Anchor) event.getSource();
				col.setHTML("<span style=\"color: white;\">" + text + "</span>");

				sortParks(parkTableSortColumn, parkTableReverseSort);
				int rowCount = parksFlexTable.getRowCount();
				for (int i = rowCount - 1; i >= 1; i--) {
					parksFlexTable.removeRow(i);
				}
				displayParks(parkList);
			}
		});

		Anchor parkNeighbourhood = new Anchor("Neighbourhood");
		parkNeighbourhood.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (2 == parkTableSortColumn) {
					parkTableReverseSort = !parkTableReverseSort;
				} else {
					parkTableSortColumn = 2;
					parkTableReverseSort = false;
				}
				String text = parkTableReverseSort ? "Neighbourhood &#9660;"
						: "Neighbourhood &#9650;";
				Anchor col = (Anchor) event.getSource();
				col.setHTML("<span style=\"color: white;\">" + text + "</span>");

				sortParks(parkTableSortColumn, parkTableReverseSort);
				int rowCount = parksFlexTable.getRowCount();
				for (int i = rowCount - 1; i >= 1; i--) {
					parksFlexTable.removeRow(i);
				}
				displayParks(parkList);
			}
		});	
		
		Anchor parkAddress = new Anchor("Address");
		parkAddress.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (1 == parkTableSortColumn) {
					parkTableReverseSort = !parkTableReverseSort;
				} else {
					parkTableSortColumn = 1;
					parkTableReverseSort = false;
				}
				String text = parkTableReverseSort ? "Address &#9660;"
						: "Address &#9650;";
				Anchor col = (Anchor) event.getSource();
				col.setHTML("<span style=\"color: white;\">" + text + "</span>");

				sortParks(parkTableSortColumn, parkTableReverseSort);
				int rowCount = parksFlexTable.getRowCount();
				for (int i = rowCount - 1; i >= 1; i--) {
					parksFlexTable.removeRow(i);
				}
				displayParks(parkList);
			}
		});
		
		// Create table for park data.
		parksFlexTable.setWidget(0, 0, parkName);
		parksFlexTable.setWidget(0, 1, parkAddress);
		parksFlexTable.setWidget(0, 2, parkNeighbourhood);
		parksFlexTable.setText(0, 3, "Add Favorite");


		// Create table for restaurant data.
		restaurantFlexTable.setText(0, 0, "Name");
		restaurantFlexTable.setText(0, 1, "Status");
		restaurantFlexTable.setText(0, 2, "Address");
		restaurantFlexTable.setText(0, 3, "Food");
		restaurantFlexTable.setText(0, 4, "Add Favorite");

		// Create table for favorite restuarant data.
		favoriteRestaurantTable.setText(0, 0, "Name");
		favoriteRestaurantTable.setText(0, 1, "Status");
		favoriteRestaurantTable.setText(0, 2, "Address");
		favoriteRestaurantTable.setText(0, 3, "Food");
		favoriteRestaurantTable.setText(0, 4, "Remove Favorite");
		
		// Create table for favorite parks data
		favoriteParkTable.setText(0, 0, "Name");
		favoriteParkTable.setText(0, 1, "Address");
		favoriteParkTable.setText(0, 3, "Neighbourhood");
		favoriteParkTable.setText(0, 4, "Remove Favorite");

		// Add styles to elements in the table.
		parksFlexTable.setCellPadding(6);
		parksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		parksFlexTable.addStyleName("watchList");
		restaurantFlexTable.setCellPadding(6);
		restaurantFlexTable.getRowFormatter()
				.addStyleName(0, "watchListHeader");
		restaurantFlexTable.addStyleName("watchList");
		favoriteRestaurantTable.setCellPadding(6);
		favoriteRestaurantTable.getRowFormatter().addStyleName(0,
				"watchListHeader");
		favoriteRestaurantTable.addStyleName("watchList");
		mainPanel.addSelectionHandler(new SelectionHandler<Integer>() {

			// @Override
			// public void onClick(ClickEvent event) {
			// loadFavoriteRestaurant();
			//
			// }

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() == 2) {
					int numOfRow = favoriteRestaurantTable.getRowCount();
					for (int i = 1; i < numOfRow; i++) {
						favoriteRestaurantTable.removeRow(1);
					}
					loadFavoriteRestaurant();
				}

			}
		});

		loadParks();
		loadRestaurants();

		mainPanel.selectTab(0);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("content-window").add(logoutPanel);
		RootPanel.get("content-window").add(mainPanel);

		buildMapUi();
	}

	private void buildMapUi() {
		// TODO add a map
		SimplePanel widg = new SimplePanel();
		widg.setSize("600px", "800px");
		mainPanel.add(widg, "Map View");

		LatLng myLatLng = LatLng.create(49.255944, -123.1205715);
		MapOptions myOptions = MapOptions.create();
		myOptions.setCenter(myLatLng);
		myOptions.setZoom(11);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		myOptions.setDraggable(true);
		myOptions.setMapTypeControl(true);
		myOptions.setScaleControl(true);
		myOptions.setScrollwheel(true);

		map = GoogleMap.create(widg.getElement(), myOptions);
	}

	private void loadFavoriteRestaurant() {
		int row = 1;

		for (final Restaurant obj : restaurants) {
			favoriteRestaurantTable.setText(row, 0, obj.getName());
			favoriteRestaurantTable.setText(row, 1, obj.getstatus());
			favoriteRestaurantTable.setText(row, 2, obj.getAddress());
			favoriteRestaurantTable.setText(row, 3, obj.getFood());
			obj.setRow(row);
			Button removeFavorite = new Button();
			removeFavorite.setText("remove favorite");
			removeFavorite.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					removeFromFavoriteList(obj);
					refreshindex();
				}

			});
			favoriteRestaurantTable.setWidget(row, 4, removeFavorite);
			row++;
		}
	}

	private void refreshindex() {
		int row = 1;
		for (Restaurant res : restaurants) {
			res.setRow(row);
			row++;
		}
	}

	private void removeFromFavoriteList(Restaurant obj) {

		Restaurant target = null;

		for (Restaurant res : restaurants) {
			if (res.getId().equalsIgnoreCase(obj.getId())) {
				target = res;
			}
		}
		if (target != null) {
			restaurants.remove(target);
		}
		// loadFavoriteRestaurant();
		// favoriteRestaurantTable.removeRow(1);
		// loadFavoriteRestaurant();
		favoriteRestaurantTable.removeRow(obj.getRow());

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
					deleteOverlays();
					addParkMarker(park);
					addUserMarker();
					// addUserMarker(ul.getLat(), ul.getLong());
					// logger.log(Level.INFO, "LatLong" + " " + ul.getLat() +
					// ","
					// + ul.getLong());

					addPathBetweenMarkers();
					mainPanel.selectTab(3);
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

	private void addParkMarker(final Park p) {
		LatLng location = LatLng.create(p.getLatitude(), p.getLongitude());
		map.panTo(location);
		map.setZoom(11.0);
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		Marker marker = Marker.create(markerOpts);
		marker.setTitle(p.getName());
		markers.add(marker);
	}

	private void addUserMarker() {
		// TODO Need to get the actual user location.
		// For now, we use dummy data
		double dummyLatitude = 49.223790;
		double dummyLongitude = -123.148965;

		LatLng location = LatLng.create(dummyLatitude, dummyLongitude);
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		Marker marker = Marker.create(markerOpts);
		marker.setTitle("User's Current Location");
		markers.add(marker);
	}

	private void addUserMarker(double lattt, double longgg) {
		LatLng location = LatLng.create(lattt, longgg);
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		Marker marker = Marker.create(markerOpts);
		marker.setTitle("User's Current Location");
		markers.add(marker);
	}

	private void deleteOverlays() {
		if (markers != null) {
			for (Marker marker : markers) {
				marker.setMap((GoogleMap) null);
			}
			markers.clear();
		}

		if (polylines != null) {
			for (Polyline polyline : polylines) {
				polyline.setMap((GoogleMap) null);
			}
			polylines.clear();
		}
	}

	private void addPathBetweenMarkers() {
		PolylineOptions polyOpts = PolylineOptions.create();
		polyOpts.setStrokeColor("#0000FF");
		polyOpts.setStrokeOpacity(1.0);
		polyOpts.setStrokeWeight(3);

		Polyline poly = Polyline.create(polyOpts);
		poly.setMap(map);

		for (Marker m : markers) {
			poly.getPath().push(m.getPosition());
		}
		polylines.add(poly);
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

	private void loadRestaurants() {
		restaurantService.getRestaurants(new AsyncCallback<List<Restaurant>>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			@Override
			public void onSuccess(List<Restaurant> result) {
				int size = -1;
				if (result != null) {
					size = result.size();
				}
				logger.log(Level.INFO, "Number of loaded parks: " + size);
				restaurantList = new ArrayList<Restaurant>(result);
				displayRestaurants(result);
			}
		});
	}

	private void displayRestaurants(List<Restaurant> restaurants) {
		for (Restaurant restaurant : restaurants) {
			displayRestaurant(restaurant);
		}
	}

	private void addtoFavoriteList(Restaurant obj) {
		if (restaurants.size() == 0) {
			restaurants.add(obj);
		} else {
			for (Restaurant res : restaurants) {
				if (res.getId().equalsIgnoreCase(obj.getId())) {
					return;
				}
			}
			restaurants.add(obj);
		}

		// restaurants.add(obj);

	}

	private void displayRestaurant(final Restaurant obj) {
		int row = restaurantFlexTable.getRowCount();
		final String restaurantId = obj.getId();

		// Add the park to the table.
		row = restaurantFlexTable.getRowCount();
		restaurantFlexTable.setText(row, 0, obj.getName());
		restaurantFlexTable.setText(row, 1, obj.getstatus());
		restaurantFlexTable.setText(row, 2, obj.getAddress());
		restaurantFlexTable.setText(row, 3, obj.getFood());

		Anchor parkName = new Anchor(obj.getName());
		parkName.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Restaurant restaurant = selectRestaurant(restaurantId);
				if (restaurant != null) {
					logger.log(
							Level.INFO,
							"Selected restaurant " + restaurant.getName()
									+ " with lat/lon "
									+ restaurant.getLatitude() + "/"
									+ restaurant.getLongitude());
					addRestaurantMarker(restaurant);
				} else {
					logger.log(Level.SEVERE, "No restaurant was found");
				}
			}
		});
		restaurantFlexTable.setWidget(row, 0, parkName);

		Button addFavorite = new Button();
		addFavorite.setText("add to favorite");
		addFavorite.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addtoFavoriteList(obj);

			}

		});
		// closeButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// dialogBox.hide();
		// sendButton.setEnabled(true);
		// sendButton.setFocus(true);
		// }
		// });
		// restaurantFlexTable.setText(addFavorite);
		restaurantFlexTable.setWidget(row, 4, addFavorite);
	}

	private Restaurant selectRestaurant(final String restaurantId) {
		for (Restaurant r : restaurantList) {
			if (restaurantId.equals(r.getId())) {
				return r;
			}
		}
		return null;
	}

	private void addRestaurantMarker(final Restaurant r) {
		LatLng location = LatLng.create(r.getLatitude(), r.getLongitude());
		map.panTo(location);
		MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setPosition(location);
		markerOpts.setMap(map);
		Marker marker = Marker.create(markerOpts);
		marker.setTitle(r.getName());
	}

	private void sortParks(int parkTableSortColumn, boolean parkTableReverseSort) {
		switch (parkTableSortColumn) {
		case 0: {
			if (parkTableReverseSort) {
				Collections.sort(parkList,
						Collections.reverseOrder(ParkNameComparator));
			} else {
				Collections.sort(parkList, ParkNameComparator);
			}
			break;
		}
		case 2: {
			if (parkTableReverseSort) {
				Collections.sort(parkList,
						Collections.reverseOrder(ParkNeighbourhoodComparator));
			} else {
				Collections.sort(parkList, ParkNeighbourhoodComparator);
			}
			break;
		}
		case 1: {
			if (parkTableReverseSort) {
				Collections.sort(parkList,
						Collections.reverseOrder(ParkAddressComparator));
			} else {
				Collections.sort(parkList, ParkAddressComparator);
			}
			break;
		}
		default: {
			// Nothing to sort
		}
		}
	}

	public static Comparator<Park> ParkNameComparator = new Comparator<Park>() {
		public int compare(Park obj1, Park obj2) {
			if (obj1 == null && obj2 == null) {
				return 0;
			} else if (obj1 == null) {
				return -1;
			} else if (obj2 == null) {
				return 1;
			}
			return obj1.getName().compareTo(obj2.getName());
		}

	};
	public static Comparator<Park> ParkNeighbourhoodComparator = new Comparator<Park>() {
		public int compare(Park obj1, Park obj2) {
			if (obj1 == null && obj2 == null) {
				return 0;
			} else if (obj1 == null) {
				return -1;
			} else if (obj2 == null) {
				return 1;
			}
			return obj1.getNeighbourhood().compareTo(obj2.getNeighbourhood());
		}

	};
	public static Comparator<Park> ParkAddressComparator = new Comparator<Park>() {
		public int compare(Park obj1, Park obj2) {
			if (obj1 == null && obj2 == null) {
				return 0;
			} else if (obj1 == null) {
				return -1;
			} else if (obj2 == null) {
				return 1;
			}
			return obj1.getAddress().compareTo(obj2.getAddress());
		}

	};
}
