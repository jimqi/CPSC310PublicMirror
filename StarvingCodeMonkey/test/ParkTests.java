import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.client.KillersProject;

public class ParkTests {
	
	
	Park park1, park2;
	@Before
	public void initialize(){
		park1 = new Park("a","b", 1,2,"c","d","e");
		park2 = new Park();
	}
	
	@Test
	public void getTests() {
		assertEquals("c", park1.getAddress());
		assertEquals("a", park1.getId());
		assertEquals(1, park1.getLatitude());
		assertEquals(2, park1.getLongitude());
		assertEquals("b", park1.getName());
		assertEquals("e", park1.getNeighbourhood());
		assertEquals("d", park1.getUrl());
	}
	
	public void rowTest(){
		park1.setRow(1);
		assertEquals(1, park1.getRow());
	}
	
	public void setTests(){
		park2.setAddress("c");
		park2.setId("a");
		park2.setLatitude(1);
		park2.setLongitude(2);
		park2.setName("b");
		park2.setNeighbourhood("e");
		park2.setUrl("d");
		assertEquals("c", park2.getAddress());
		assertEquals("a", park2.getId());
		assertEquals(1, park2.getLatitude());
		assertEquals(2, park2.getLongitude());
		assertEquals("b", park2.getName());
		assertEquals("e", park2.getNeighbourhood());
		assertEquals("d", park2.getUrl());
	}

}
