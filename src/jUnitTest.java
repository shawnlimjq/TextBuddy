import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class jUnitTest {

	private static ArrayList<String> textList;

	@Test
	public void testArguments() {
		String[] noArgs = new String[0];
		String[] args = new String[2];

		assertTrue(TextBuddy.noArgumentsEntered(noArgs));
		assertFalse(TextBuddy.noArgumentsEntered(args));
	}

	@Test
	public void testFileName() {
		assertFalse(TextBuddy.invalidFileName("test.txt"));
		assertTrue(TextBuddy.invalidFileName("txt"));
	}

	@Test
	public void testFileExtention() {
		assertTrue(TextBuddy.invalidFileExtension("test.jpg"));
		assertFalse(TextBuddy.invalidFileName("test.txt"));
	}

	@Test
	public void checkValidInteger() {
		assertTrue(TextBuddy.isInteger("0"));
		assertFalse(TextBuddy.isInteger("hi"));
	}

	@Test
	public void checkCommandType() {
		assertEquals(TextBuddy.CommandType.ADD, TextBuddy.getCommandType("add"));
		assertEquals(TextBuddy.CommandType.ADD, TextBuddy.getCommandType("ADd"));
		assertEquals(TextBuddy.CommandType.DELETE, TextBuddy.getCommandType("delete"));
		assertEquals(TextBuddy.CommandType.DELETE, TextBuddy.getCommandType("deletE"));
		assertEquals(TextBuddy.CommandType.CLEAR, TextBuddy.getCommandType("clear"));
	}

}
