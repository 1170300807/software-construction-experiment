/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of every
	 *         tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		int n = tweets.size();
		Instant min = tweets.get(0).getTimestamp();
		Instant max = tweets.get(0).getTimestamp();
		for (int i = 1; i < n; i++) {
			Instant now = tweets.get(i).getTimestamp();
			if (now.isBefore(min))
				min = now;
			if (now.isAfter(max))
				max = now;
		}
		Timespan timespan = new Timespan(min, max);
		return timespan;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a Twitter username (as defined by
	 *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
	 *         preceded or followed by any character valid in a Twitter username.
	 *         For this reason, an email address like bitdiddle@mit.edu does NOT
	 *         contain a mention of the username mit. Twitter usernames are
	 *         case-insensitive, and the returned set may include a username at most
	 *         once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		Set<String> usernameInText = new HashSet<String>();
		int n = tweets.size();
		Pattern pattern = Pattern.compile("(^|[^A-Za-z0-9_-]+)@([A-Za-z0-9_-]+)");
		for (int i = 0; i < n; i++) {

			String text = tweets.get(i).getText();
			Matcher username_mention = pattern.matcher(text);
			while (username_mention.find()) {
				String now = new String(username_mention.group(2).toString().toLowerCase());
				usernameInText.add(now);
			}
		}
		return usernameInText;
	}

}
