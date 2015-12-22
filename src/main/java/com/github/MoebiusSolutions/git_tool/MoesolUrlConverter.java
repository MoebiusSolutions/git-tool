package com.github.MoebiusSolutions.git_tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoesolUrlConverter {

	private static Pattern[] oldUrlPatterns = {
        Pattern.compile("ssh://([\\w\\.]+@)?git.moesol.com:?/git/([^\t]+)")
	};

	/**
	 * Static accessor
	 */
	public static final MoesolUrlConverter I = new MoesolUrlConverter();

	public boolean isOldMoesolUrl(String url) {
		for (Pattern urlPattern : oldUrlPatterns) {
			if (urlPattern.matcher(url).matches()) {
				return true;
			}
		}
		return false;
	}

	public String toNewUrl(String url) {
		for (Pattern urlPattern : oldUrlPatterns) {
			Matcher matcher = urlPattern.matcher(url);
			if (!matcher.matches()) {
				continue;
			}
			String name = matcher.group(1);
			if (name == null) { name = ""; }
			String subUrl = matcher.group(2);
			if (subUrl == null) { subUrl = ""; }
			
			return String.format("ssh://%sgit.moesol.com/%s", name, subUrl);
		}
		return null;
	}
}
