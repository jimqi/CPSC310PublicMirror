import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.killers.entity.Restaurant;


public class RestTest {

	Restaurant rest1;
	
	@Before
	public void initialize(){
		rest1 = new Restaurant();
	}
	
	@Test
	public void testGets(){
		assertEquals("status", rest1.getstatus());
		assertEquals("vendorType", rest1.getVendorType());
		assertEquals("address", rest1.getAddress());
		assertEquals("food", rest1.getFood());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
