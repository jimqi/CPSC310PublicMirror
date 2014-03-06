/**
 * Store Userinfo to Database 
 */
package entity;
//TODO:

/**
 * @author Charles
 *
 */
public class UserInfo {
	private String userName;  // first/last name
	private char[] emailAddr; // facebook login name
	private int phoneNum;     // facebook login number
	private String[] favPlaces; // places rated as favs
	private String[] placesVisited; // place the user has visited before, with ratings if available
	public UserInfo(String userName, char[] emailAddr, int phoneNum,
			String[] favPlaces, String[] placesVisited) {
		super();
		this.userName = userName;
		this.emailAddr = emailAddr;
		this.phoneNum = phoneNum;
		this.favPlaces = favPlaces;
		this.placesVisited = placesVisited;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public char[] getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(char[] emailAddr) {
		this.emailAddr = emailAddr;
	}
	public int getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String[] getFavPlaces() {
		return favPlaces;
	}
	public void setFavPlaces(String[] favPlaces) {
		this.favPlaces = favPlaces;
	}
	public String[] getPlacesVisited() {
		return placesVisited;
	}
	public void setPlacesVisited(String[] placesVisited) {
		this.placesVisited = placesVisited;
	}
	
	
}
