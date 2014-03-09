package com.google.gwt.killers.entity;

public class Restaurant extends Place {
	
	private String status;
	private String vendorType;
	private String Address;

	public Restaurant(String name, String status, String vendorType, String Address, float latitude, float longitude) {
		super(name, latitude, longitude);
		this.status = status;
		this. vendorType = vendorType;
		this.Address = Address;
	}

	public String getstatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getvendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	
	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}
}
