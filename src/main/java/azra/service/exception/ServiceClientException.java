package azra.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * Service client exception
 * sazrai@hotmail.com
 */
public class ServiceClientException extends RuntimeException {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private HttpStatus httpStatus;

	/**
     * Construct a ClientException from a list of error message
     *
     * @param errorMessage error message for client
     * @param httpStatus  http status
     */
    public ServiceClientException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        Assert.notNull(httpStatus, "http status must be provided");
        Assert.notNull("http status may not be null");
        this.httpStatus = httpStatus;
    }

	/**
	 * @return The value of errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return The value of httpStatus.
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
