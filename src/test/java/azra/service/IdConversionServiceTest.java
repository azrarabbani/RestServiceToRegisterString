package azra.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class IdConversionServiceTest {
	
	
	private IdConversionService service;
	
	/**
	 * 
	 */
	@Before
	public void setup() {
		service = new IdConversionService();
	}

	/**
	 * test write contents
	 */
	@Test
	public void testWriteContents() {
		String encodedStr = service.encodeString("abbc");
		assertEquals("587", encodedStr);
		encodedStr = service.encodeString("");
		assertEquals("", encodedStr);
		encodedStr = service.encodeString(null);
		assertEquals(null, encodedStr);
	}

}
