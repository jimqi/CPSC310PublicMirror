package com.google.gwt.killers.entity;

import java.io.Serializable;

public abstract class Place implements Serializable {

	private static final long serialVersionUID = -4904011851500496233L;

	private String name;
	private float latitude;
	private float longitude;

	public Place() {
		this.name = "Place name";
		this.latitude = 0;
		this.longitude = 100;
	}

	public Place(String name, float latitude, float longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
