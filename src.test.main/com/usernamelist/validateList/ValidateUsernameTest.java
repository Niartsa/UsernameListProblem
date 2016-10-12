package com.usernamelist.validateList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usernamelist.entities.Result;

public class ValidateUsernameTest {

	final static Logger logger = Logger.getLogger(ValidateUsernameTest.class.getName());

	private static final int SUGGESTEDLISTSIZE = 14;

	@BeforeClass
	public static void beforeValidateUsernameTest() {
		logger.info("Starts ValidateUsername test class");
	}

	@AfterClass
	public static void afterValidateUsernameTest() {
		logger.info("Ends ValidateUsername test class");
	}

	@Test
	public void testCheckUsernameOk() {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername("Test" + new Date().getTime());
		assertTrue("The username must be a valid and accepted.", result.getT().booleanValue());
	}

	@Test
	public void testCheckUsernameNok() {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername("Ronald");
		assertFalse("The username must be already taked.", result.getT().booleanValue());
		assertNotNull("The suggested usernames list must not be empty.", result.getA());
		assertEquals("The suggested usernames list must contain 14 elements at least.", result.getA().size(),
				SUGGESTEDLISTSIZE);
	}

	@Test
	public void testCheckUsernameLess6Characters() {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername("Anne");
		assertFalse("The username must be already taked.", result.getT().booleanValue());
	}

	@Test
	public void testCheckUsernameNull() {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername(null);
		assertFalse("The username must be null.", result.getT().booleanValue());
		assertNull("The suggested usernames list must be empty.", result.getA());
	}

	@Test
	public void testCheckUsernameEmpty() {
		Result<Boolean, List<String>> result = new ValidateUsername().checkUsername("    ");
		assertFalse("The username must be emptyS.", result.getT().booleanValue());
		assertNull("The suggested usernames list must be empty.", result.getA());
	}
}
