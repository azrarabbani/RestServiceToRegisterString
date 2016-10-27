package azra.service;

import org.springframework.stereotype.Service;

/**
 * Service to encode ids to register. sazrai@hotmail.com
 */
@Service
public class IdConversionService {

	/**
	 * Service to encode given strings into unicode
	 * 
	 * @param idToConvert
	 * @return encoded String
	 */
	public String encodeString(String idToConvert) {
		// String id is a number calculated as follows:
		// String id is a sum of each character's id
		// A character id is a sum of the current and previous character's
		// Unicode code point
		// The character id of the first character in the string is the
		// character's Unicode code point
		// If the current and previous characters are the same, the character id
		// is the current character's Unicode code point
		// String id must be calculated without using a loop
		// Example:
		// "abc" => 97 + (97 + 98) + (98 + 99)
		// "abbc" => 97 + (97 + 98) + (98) + (98 + 99)
		if (idToConvert == null) {
			return null;
		}
		if (idToConvert.isEmpty()) {
			return "";
		}
		return encodeString(idToConvert, 0, 0) + "";
	}

	private int encodeString(String str, int index, int stringId) {
		if (index >= str.length()) {
			return stringId;
		}
		stringId += getCharacterId(str, index);
		index++;
		return encodeString(str, index, stringId);

	}

	private int getCharacterId(CharSequence str, int index) {
		int previousCharCodePoint = 0;
		if (index > 0) {
			previousCharCodePoint = Character.codePointAt(str, index - 1);
		}
		int currentCharCodePoint = Character.codePointAt(str, index);
		if (previousCharCodePoint == currentCharCodePoint) {
			return currentCharCodePoint;
		} else {
			return previousCharCodePoint + currentCharCodePoint;
		}

	}
}
