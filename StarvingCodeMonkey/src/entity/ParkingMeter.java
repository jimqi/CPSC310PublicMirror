package entity;

public class ParkingMeter extends Place {
	private double rate;
	private int timeLimit;

	public ParkingMeter(String name, int id, LatLong location) {
		super(name, id, location);
		//this.rate = rate;
		//this.timeLimit = 0;
		// TODO Auto-generated constructor stub
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	
}
