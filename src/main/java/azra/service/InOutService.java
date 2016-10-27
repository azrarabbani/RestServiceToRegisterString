package azra.service;

import java.util.List;
import java.util.Map;

/**
 * in out service interface
 * sazrai@hotmail.com
 */
public interface InOutService {

	
	/**
	 * @return map of string and list of string
	 * 
	 */
    Map<String,List<String>>  loadContents() ;
	
	/**
	 * @param map  map to be written.
	 * 
	 */
	void writeContents(Map<String,List<String>> map);
	
}
