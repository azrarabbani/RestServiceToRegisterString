package azra.service;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import azra.service.exception.ServiceClientException;

/**
 * test scenario for rest controller
 */
public class AzraServiceControllerTest {

	private static final String FILE_TEST_REGISTER_TXT = "test_register.txt";
	private static final Logger logger = LoggerFactory.getLogger(AzraServiceControllerTest.class);
	AzraServiceController controller;
	IdConversionService conversionService;
	FileInOutService fileInOutService;

	/**
	 * setup test data
	 */
	@Before
	public void setup() {
		conversionService = Mockito.mock(IdConversionService.class);
		fileInOutService = new FileInOutService(FILE_TEST_REGISTER_TXT);
		controller = new AzraServiceController(conversionService, fileInOutService);
		controller.initialize();
	}

	/**
	 * clean up
	 */
	@After
	public void destroy() {
		try {
			Path path = FileSystems.getDefault().getPath(FILE_TEST_REGISTER_TXT);
			Files.delete(path);
		} catch (Exception e) {
			logger.error("Exception occurred while cleaning up resources {}", e);
		}
	}

	/**
	 * test to register id
	 */
	@Test
	public void testRegisterId() {
		String idToRegister = "abbc";
		String code = "587";
		setupMockEnvironemnt(idToRegister, code);
		ResponseEntity<String> response = controller.registerMyId(idToRegister);
		String message = response.getBody();
		assertEquals(code, message);
		controller.destroy();
		logger.info("Test for AzraServiceController#testRegisterId Successfully executed.");
	}

	/**
	 * test to register id which already exists
	 */
	@Test(expected = ServiceClientException.class)
	public void testAlreadyRegisteredId() {
		String idToRegister = "abbc";
		String code = "587";
		setupMockEnvironemnt(idToRegister, code);
		ResponseEntity<String> response = controller.registerMyId(idToRegister);
		String message = response.getBody();
		assertEquals(code, message);
		response = controller.registerMyId(idToRegister);
		logger.info("Test for AzraServiceController#testRegisterId Successfully executed.");
	}

	/**
	 * test to get ids
	 */
	@Test
	public void testGetRegisteredIds() {
		String idToRegister = "abbc";
		String code = "587";
		setupMockEnvironemnt(idToRegister, code);
		ResponseEntity<String> response = controller.registerMyId(idToRegister);
		String message = response.getBody();
		assertEquals(code, message);
		ResponseEntity<List<String>> listOfIdsResponse = controller.getRegisteredIds(code);
		List<String> ids = listOfIdsResponse.getBody();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(ids.get(0), idToRegister);
		logger.info("Test for AzraServiceController#testGetRegisteredIds Successfully executed.");
	}

	/**
	 * test to get non registered ids
	 */
	@Test(expected = ServiceClientException.class)
	public void testGetNonRegisteredIds() {
		String idToRegister = "abbc";
		String code = "587";
		setupMockEnvironemnt(idToRegister, code);
		ResponseEntity<String> response = controller.registerMyId(idToRegister);
		String message = response.getBody();
		assertEquals(code, message);
		controller.getRegisteredIds("666");
	}

	private void setupMockEnvironemnt(String inputStr, String encodedStr) {
		Mockito.doReturn(encodedStr).when(this.conversionService).encodeString(inputStr);
	}
}
