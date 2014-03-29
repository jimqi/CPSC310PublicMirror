import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.killers.server.CSVParser;
public class CSVParserTest {

	CSVParser csvParser;
	
	@Before
	public void initialize(){
		csvParser = new CSVParser();
	}
	
	@Test
	public void testParseLine() throws Exception{
		String monkey[] = csvParser.parseLine("monkey, 310,money.");
		assertEquals(3, monkey.length);
		assertEquals("monkey", monkey[0]);
		assertEquals(" 310", monkey[1]);
		assertEquals("money.", monkey[2]);
	}
	
	@Test
	public void parseQuotedString() throws IOException {
		String[] monkey = csvParser.parseLine("\"1\",\"b\"");
		assertEquals(2, monkey.length);
		assertEquals("1", monkey[0]);
		assertEquals("b", monkey[1]);
		assertFalse(csvParser.isPending());
		
		
	}
	
	
	@Test
	public void parseLineMulti() throws IOException {
		String[] monkey = csvParser.parseLineMulti("\"310 \"\"is a big\"\" money\",donkey,monkey\n");
		assertEquals(3, monkey.length);
	    assertEquals("310 \"is a big\" money", monkey[0]);
        assertEquals("donkey", monkey[1]);
        assertEquals("monkey\n", monkey[2]);
	}
	
	
	
	
	
	
	
	
	
	
	
}

