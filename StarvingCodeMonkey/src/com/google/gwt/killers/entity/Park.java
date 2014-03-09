package com.google.gwt.killers.entity;

public class Park extends Place {

	private String address;

	private String url;

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
