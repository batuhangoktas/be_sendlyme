package com.sendlyme.utils;

import java.util.regex.Pattern;

public class DataUtil {
	static Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean ipValidate(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}
}
