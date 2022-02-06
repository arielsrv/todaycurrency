package com.sdk;

import java.util.regex.Matcher;

public class RegexHelper {

	public static String getString(Matcher matcher, int group) {
		boolean hasGroup = matcher.find() && matcher.groupCount() > group - 1;
		return hasGroup ? matcher.group(group) : null;
	}
}
