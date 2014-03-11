import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.killers.entity.UserLocation;


public class UserLocationTest {

	
	
	@Test
	public void testGetLong() {
		// lat lng are values of actual latitude and longitude of 2329 West Mall, Vancouver BC
		double lng = -123.2536718;
		double gotLng;
		UserLocation ul = new UserLocation("2329", "West Mall");
		gotLng = ul.getLong();
		
		assertEquals(lng, gotLng);
	}

	@Test
	public void testGetLat() {
		// lat lng are values of actual latitude and longitude of 2329 West Mall, Vancouver BC
		double lat = 49.2613746;
		double gotLat;
		UserLocation ul = new UserLocation("2329", "West Mall");
		gotLat = ul.getLat();
		
		assertEquals(lat, gotLat);
	}
	
	
}
