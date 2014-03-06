package entity;
import entity.LatLong;
/**
 * Author  Anastasiya
 */
public class Place {

private String Name;
private int id;
private LatLong location;	

public Place(String name, int id, LatLong location) {
	super();
	Name = name;
	this.id = id;
	this.location = location;
}


public String getName(int ID){
	// TODO
	return Name ;
	
}

public int getId(){
	//TODO
	return id;
}
public LatLong getLocation(int id){
	//TODO
	return location;
}
}
