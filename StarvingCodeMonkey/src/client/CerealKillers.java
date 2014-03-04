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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Charles
 *
 */
public class CerealKillers implements EntryPoint {

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

	//TODO: Construct A Panel
	ArrayList<Panel> panels = new ArrayList<Panel>();
	
	private HandlerRegistration loginaction;
	private HandlerRegistration saveaction;
	private HandlerRegistration logoutaction;
	private HandlerRegistration loaddata;
	private HandlerRegistration setadmin;
	private HandlerRegistration adduseraction;
	private HandlerRegistration clearaction;
	
	@Override
	public void onModuleLoad() {
		// User Login
		userlogin();

	}

	private void userlogin() {
		// TODO Auto-generated method stub
		login.add(loginusername);
		login.add(loginuser);
		login.add(loginpassword);
		login.add(loginpswd);
		login.add(loginbutton);
		
		login.add(setupadmin);

	}
	
	

}
