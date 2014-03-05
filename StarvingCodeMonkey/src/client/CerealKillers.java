/**
 * GUI file and RPC function calls
 */
//TODO: Charles
package client;

import java.util.ArrayList;

import client.DataService;
import client.DataServiceAsync;
import client.LoginService;
import client.LoginServiceAsync;
import client.StorageService;
import client.StorageServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Charles
 * 
 */
public class CerealKillers implements EntryPoint {

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the application.");
	private Anchor signInLink = new Anchor("Login");

	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox textBox = new TextBox();
	private Button loadButton = new Button("Load");

	private FlexTable dataFlexTable = new FlexTable();
	private DataServiceAsync dataSvc = GWT.create(DataService.class);
	private StorageServiceAsync storageSvc = GWT.create(StorageService.class);
	private LoginServiceAsync loginSvc = GWT.create(LoginService.class);
	private String url;

	// Login GUI
	private Button loginbutton = new Button("login");
	private Button adduser = new Button("Add User");
	private Button setupadmin = new Button("Set up default admin");

	private HorizontalPanel login = new HorizontalPanel();
	private HorizontalPanel register = new HorizontalPanel();

	private TextBox loginuser = new TextBox();
	private PasswordTextBox loginpswd = new PasswordTextBox();
	private TextBox registeruser = new TextBox();
	private PasswordTextBox registerpsw = new PasswordTextBox();

	private ListBox registertype = new ListBox();
	private Label loginusername = new Label("Username:");
	private Label loginpassword = new Label("Password:");
	private Label registusername = new Label("Username:");
	private Label registpassword = new Label("Password:");

	private Button logoutbutton = new Button("logout");
	private Button clearbutton = new Button("clear");
	private Button saveButton = new Button("save");

	// TODO: Construct A Panel
	ArrayList<Panel> panels = new ArrayList<Panel>();

	private HandlerRegistration loginaction;
	private HandlerRegistration saveaction;
	private HandlerRegistration logoutaction;
	private HandlerRegistration loaddata;
	private HandlerRegistration setadmin;
	private HandlerRegistration adduseraction;
	private HandlerRegistration clearaction;

	/** * Entry point method. */
	@Override
	public void onModuleLoad() {
		System.out.println("Starting the application !!!!!");
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
							// TODO Load the main page
						} else {
							loadLogin();
						}
					}
				});
	}

	private void loadLogin() {
		// TODO Auto-generated method stub
		login.add(loginusername);
		login.add(loginuser);
		login.add(loginpassword);
		login.add(loginpswd);
		login.add(loginbutton);

		login.add(setupadmin);

		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("stockList").add(loginPanel);
	}

	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

}
