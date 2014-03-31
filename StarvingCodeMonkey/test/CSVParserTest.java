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
	
	//numbers
	@Test
	public void testParseLineNum() throws Exception{
		String monkey[] = csvParser.parseLine("-11, 310,111.");
		assertEquals(3, monkey.length);
		assertEquals("-11", monkey[0]);
		assertEquals(" 310", monkey[1]);
		assertEquals("111.", monkey[2]);
	}
	
	// quotes test
	@Test
	public void parseQuotes() throws IOException {
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
	
	// test quotes within it
	@Test
	public void testParseLineQuotesInside() throws IOException {
		String[] monkey = csvParser.parseLine("cs,cs310\"ishard\"sigh,class");
		assertEquals(3, monkey.length);
		assertEquals("cs310\"ishard\"sigh", monkey[1]);
	}
	
	// see if commas work properly for default seperator
	@Test
	public void testParseLineCommas() throws IOException {
		String[] monkey = csvParser.parseLine("a,\"b,c,d\",z");
		assertEquals(3, monkey.length);
		assertEquals("b,c,d", monkey[1]);
	}
	//empty cases
	@Test
	public void testParseEmptyLines() throws IOException {
		String[] monkey = csvParser.parseLine(",,,2");
		assertEquals(4, monkey.length);
		assertEquals("", monkey[0]);
		assertEquals("2", monkey[3]);
	}
	
	@Test //test the constructor with 5 inputs (covers all)
	public void test5Constructor() throws IOException {
		csvParser = new CSVParser(',', '\"', '\\', true);
		String[] monkey = csvParser.parseLine(" \t \"cs\",\"310\"    \t , \"hurts\"  ");
		assertEquals(3, monkey.length);
		assertEquals("cs", monkey[0]);
		assertEquals("310", monkey[1]);
		assertEquals("hurts", monkey[2]);
	}
	
	// see if pending changes correctly
	@Test
	public void testPending() throws IOException {
		
		String[] monkey = csvParser.parseLineMulti("Compsci,\"blah \"boop\" beep\\\\ bumbum");
		assertEquals(1, monkey.length);
		assertEquals("Compsci", monkey[0]);
		assertTrue(csvParser.isPending());
		
		monkey = csvParser.parseLine("cs310 hard");
		assertEquals(1, monkey.length);
		assertEquals("cs310 hard", monkey[0]);
		assertFalse(csvParser.isPending());
	}
	
	
	// basic tests for parameters
	
	// quote and esc cant be same chars
	@Test(expected = UnsupportedOperationException.class)
	public void testQuoteEscape() {
		CSVParser csvparser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER);
		}
	
	//  sep and esc cant be same chars
	@Test(expected = UnsupportedOperationException.class)
	public void testSeperatorEscape(){
		CSVParser csvparser =  new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_SEPARATOR);
	}
	
	// sep cant be null
	@Test(expected = UnsupportedOperationException.class)
	public void seperatorNotNull(){
		 CSVParser p = new CSVParser(CSVParser.NULL_CHARACTER);
	}
	
	// can if null
	@Test
	public void testQuoteEscapeNull(){
		 CSVParser csvparser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.NULL_CHARACTER, CSVParser.NULL_CHARACTER);
	}
	
	
	// seperator and quot cant b same
	@Test(expected = UnsupportedOperationException.class)
	public void testSeperatorQuote(){
		CSVParser p = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER);
	}
	
	
	
}

