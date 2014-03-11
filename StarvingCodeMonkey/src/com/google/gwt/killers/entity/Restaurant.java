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
	private String food;
	private int row;
	
	public Restaurant() {
		this.status = "status";
		this.vendorType = "vendorType";
		this.Address = "Address";
		this.food = "food";
	}

	public Restaurant(String id, String name, String status, String vendorType,
			String Address, float latitude, float longitude, String food) {
		super(id, name, latitude, longitude);
		this.status = status;
		this.vendorType = vendorType;
		this.Address = Address;
		this.food = food;
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
	
	public void setRow(int row){
		this.row = row;
	}
	
	public int getRow(){
		return this.row;
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

	public String getFood() {
		return food;
	}

	public void setFood(String Food) {
		this.food = Food;
	}
}
