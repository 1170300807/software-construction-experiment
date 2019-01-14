/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-03-18T01:00:00Z");
	private static final Instant d4 = Instant.parse("2016-02-15T23:00:00Z");
	private static final Instant d5 = Instant.parse("2016-02-17T21:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(3, "xpchf", "my email is chf980825@vip.qq.com, especially @xpchf.",
			d3);
	private static final Tweet tweet4 = new Tweet(4, "xpchf", "@@@@XPCHF@@Hsuppr@@HIT_TOM@@@Xpchf.xpchf@xpchf@@xp-chf.",
			d4);
	private static final Tweet tweet5 = new Tweet(5, "hsuppr", "@@@what@@@hsuppr@@.sdgsd@abc@@", d5);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGetTimespan() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));

		assertEquals("expected start", d4, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());
	}

	@Test
	public void testGetMentionedUsers() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
		Set<String> transUsers = new HashSet<String>();
		for (String username : mentionedUsers) {
			StringBuffer transString = new StringBuffer();
			char[] charOfUser = username.toCharArray();
			for (int i = 0; i < charOfUser.length; i++) {
				if ('a' <= charOfUser[i] && charOfUser[i] <= 'z')
					charOfUser[i] -= 'a' - 'A';
				transString.append(charOfUser[i]);
			}
			transUsers.add(transString.toString());
		}
		System.out.println(mentionedUsers.size());
		System.out.println(mentionedUsers);
		assertTrue("expected size of set", mentionedUsers.size() == 5);
		assertTrue("expected set to contain users",
				transUsers.containsAll(Arrays.asList("XPCHF", "XP-CHF", "HSUPPR", "HIT_TOM", "WHAT")));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Extract
	 * class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting (temporarily)
	 * your version of Extract with the staff's version. DO NOT strengthen the spec
	 * of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Extract, because that means you're testing a stronger spec
	 * than Extract says. If you need such helper methods, define them in a
	 * different class. If you only need them in this test class, then keep them in
	 * this test class.
	 */

}
