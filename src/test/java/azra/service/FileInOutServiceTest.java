package azra.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * 
 */
public class FileInOutServiceTest {

	private FileInOutService service;

	/**
	 * 
	 */
	@Before
	public void setup() {
		service = new FileInOutService();
	}

	/**
	 * test write contents
	 */
	@Test
	public void testWriteContents() {
		Map<String, List<String>> inputMap = createValidInput();
		service.writeContents(inputMap);
		Map<String, List<String>> outputMap = service.loadContents();
		assertEquals(inputMap.size(), outputMap.size());
		for (Map.Entry<String, List<String>> outputEntry : outputMap.entrySet()) {
			String key = outputEntry.getKey();
			List<String> inputValues = inputMap.get(key);
			List<String> outputValues = outputEntry.getValue();
			Assert.notNull(inputValues);
			assertEquals(inputValues.size(), outputValues.size());
			for (int i = 0; i < inputValues.size(); i++) {
				assertEquals(inputValues.get(i), outputValues.get(i));
			}
		}
	}

	private Map<String, List<String>> createValidInput() {
		Map<String, List<String>> inputMap = new ConcurrentHashMap<>();
		List<String> values = new Vector<>();
		values.add("hlhlhlk");
		values.add("hgoajjjj");
		values.add("hlhlmmm");
		inputMap.put("678", values);

		values = new Vector<>();
		values.add("hlhlhlkmm");
		values.add("hgoajjffjj");
		values.add("hnjklhlmmm");
		inputMap.put("887", values);
		return inputMap;
	}
}
