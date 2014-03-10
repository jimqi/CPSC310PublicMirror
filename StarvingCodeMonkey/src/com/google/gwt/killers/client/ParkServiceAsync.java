package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ParkServiceAsync {
	public void getParks(AsyncCallback<List<Park>> async);
}