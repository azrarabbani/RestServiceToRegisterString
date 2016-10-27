package azra.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * file in out service sazrai@hotmail.com
 */
@Service
public class FileInOutService implements InOutService {
	private String fileName;
	private static final String DEFAULT_FILE_NAME_FOR_ID_REGISTER = "register.txt";

	/**
	 * default
	 */
	public FileInOutService() {
		this.fileName = DEFAULT_FILE_NAME_FOR_ID_REGISTER;
	}

	/**
	 * @param fileName
	 *            name of file
	 */
	public FileInOutService(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @see azra.service.InOutService#writeContents(java.util.Map)
	 */
	public void writeContents(Map<String, List<String>> map) {
		// write to file :
		try (PrintWriter printWriter = new PrintWriter(new File(this.fileName))) {
			StringBuilder sb;
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				sb = new StringBuilder();
				sb.append(entry.getKey());
				for (String str : entry.getValue()) {
					sb.append(",");
					sb.append(str);
				}
				printWriter.println(sb.toString());
			}

		} catch (Exception e) {
			// log
		}

	}

	/**
	 * @see azra.service.InOutService#loadContents()
	 */
	public Map<String, List<String>> loadContents() {
		Map<String, List<String>> mapOfIds = new ConcurrentHashMap<>();
		try (FileInputStream fis = new FileInputStream(new File(this.fileName));
				InputStreamReader fisReader = new InputStreamReader(fis);
				BufferedReader bf = new BufferedReader(fisReader)) {
			String line = "";
			while ((line = bf.readLine()) != null) {
				putInMap(line, mapOfIds);
			}
		} catch (Exception e) {
			// log
		}

		return mapOfIds;
	}

	private void putInMap(String line, Map<String, List<String>> mapOfIds) {
		String[] values = line.split(",");
		List<String> registeredIds = new Vector<>();
		if (values.length > 0) {
			String key = values[0];
			for (int i = 1; i < values.length; i++) {
				registeredIds.add(values[i]);
			}
			mapOfIds.put(key, registeredIds);
		}
	}
}
