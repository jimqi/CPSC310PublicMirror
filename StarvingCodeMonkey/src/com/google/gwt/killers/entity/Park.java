package com.google.gwt.killers.entity;

import java.io.Serializable;

public class Park extends Place implements Serializable {

	private static final long serialVersionUID = 1522500040121087941L;

	private String address;

	private String url;

	private String neighbourhood;
	
	public int row;

	public Park() {
		this.address = "Park address";
		this.url = "Park URL";
		neighbourhood = "Park neighbourhood";
	}

	public Park(String id, String name, float latitude, float longitude,
			String address, String url, String neighbourhood) {
		super(id, name, latitude, longitude);
		this.address = address;
		this.url = url;
		this.neighbourhood = neighbourhood;
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

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	
	public void setRow(int row){
		this.row = row;
	}
	
	public int getRow() {
		return this.row;
	}

}
