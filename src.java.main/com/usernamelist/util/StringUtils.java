package com.usernamelist.util;

public class StringUtils {

	/**
	 * A single class to
	 * 
	 * @param string
	 * @return True if is a valid string, false otherwise.
	 */
	public static final boolean isValid(final String string) {
		if (null == string) {
			return false;
		}
		if (string.trim().isEmpty()) {
			return false;
		}
		return true;
	}
}