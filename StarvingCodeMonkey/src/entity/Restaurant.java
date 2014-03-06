package entity;

public class Restaurant extends Place {
	private boolean status;
	private String foodType;

	public Restaurant(String name, int id, LatLong location) {
		super(name, id, location);
		// TODO Auto-generated constructor stub
		status = false;
       
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

}
