package com.google.gwt.killers.entity;

import java.io.Serializable;

public class Restaurant extends Place implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4482117465037115147L;
	
	private String status;
	private String vendorType;
	private String Address;
	
	public Restaurant() {
		this.status = "status";
		this.vendorType = "vendorType";
		this.Address = "Address";
	}

	public Restaurant(String id, String name, String status, String vendorType,
			String Address, float latitude, float longitude) {
		super(id, name, latitude, longitude);
		this.status = status;
		this.vendorType = vendorType;
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
