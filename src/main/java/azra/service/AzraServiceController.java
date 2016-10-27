package azra.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import azra.service.exception.ServiceClientException;

/**
 * Created by arabbani on 10/26/16. sazrai@hotmail.com
 */
@RestController
public class AzraServiceController {

	private IdConversionService idConversionService;

	private FileInOutService fileInOutService;

	private static Map<String, List<String>> registeredIds;

	private static final Logger logger = LoggerFactory.getLogger(AzraServiceController.class);

	private static HttpHeaders responseHeaders;

	static {
		responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
	}

	/**
	 * @param fileService
	 * @param conversionService
	 * 
	 */
	@Autowired
	public AzraServiceController(IdConversionService conversionService, FileInOutService fileService) {
		this.fileInOutService = fileService;
		this.idConversionService = conversionService;
	}

	/**
	 * 
	 */
	@PostConstruct
	public void initialize() {
		registeredIds = fileInOutService.loadContents();
	}

	/**
	 * 
	 */
	@PreDestroy
	public void destroy() {
		fileInOutService.writeContents(registeredIds);
	}

	/**
	 * @param idToRegister
	 *            id to register in unicode format
	 * @return response with message to indicate success/failure.
	 */
	@RequestMapping(value = "/service/register", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public <T> ResponseEntity<T> registerMyId(@RequestParam("idToRegister") String idToRegister) {
		String convertedString = idConversionService.encodeString(idToRegister);
		String message = "";
		List<String> registeredStrs = registeredIds.get(convertedString);
		if (registeredStrs == null) {
			registeredStrs = new Vector<>();
			registeredIds.put(convertedString, registeredStrs);
		}
		if (registeredStrs.contains(idToRegister)) {
			throw new ServiceClientException("Id is already registered", HttpStatus.BAD_REQUEST);
		} else {
			registeredStrs.add(idToRegister);
			message = convertedString;
		}
		return (ResponseEntity<T>) new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * @param registeredId
	 *            encoded id
	 * @return list registered ids associated to given id
	 */
	@RequestMapping(value = "/service/{registeredId}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public <T> ResponseEntity<T> getRegisteredIds(@PathVariable("registeredId") String registeredId) {
		logger.info("Recieved new get request for id  {}", registeredId);
		if (registeredId == null || registeredId.isEmpty()) {
			throw new ServiceClientException("Given id is not valid!", HttpStatus.BAD_REQUEST);
		}
		List<String> registeredStrs = registeredIds.get(registeredId);
		if (registeredStrs == null) {
			throw new ServiceClientException("Given id is not registered!", HttpStatus.NOT_FOUND);
		}

		logger.info("Successfully retrieved output from job {}", registeredId);
		return (ResponseEntity<T>) new ResponseEntity(registeredStrs, HttpStatus.OK);

	}

	/**
	 * Returns the header.
	 * 
	 * @return the headers
	 */
	public HttpHeaders getHeaders() {
		return responseHeaders;
	}
}
