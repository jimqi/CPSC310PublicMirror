package com.google.gwt.killers.entity;

import java.io.Serializable;

public class Park extends Place implements Serializable {

	private static final long serialVersionUID = 1522500040121087941L;

	private String address;

	private String url;

	public Park() {
		this.address = "Park address";
		this.url = "Park URL";
	}

	public Park(String name, float latitude, float longitude, String address,
			String url) {
		super(name, latitude, longitude);
		this.address = address;
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
