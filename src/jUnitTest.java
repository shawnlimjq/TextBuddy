import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class jUnitTest {

	private static ArrayList<String> textList;

	// testing output
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();

	// setup output stream
	private void setupOutput() {
		System.setOut(new PrintStream(output));
	}

	// reset output to test other functions
	private void resetOutput() {
		output.reset();
	}

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

	@Before @Test
	public void initialiseFileTest() {
		setupOutput();
		TextBuddy.initialiseTest("mytextfile.txt");
		assertEquals("Welcome to TextBuddy. mytextfile.txt is ready for use", output.toString().trim());
		resetOutput();
	}
	
	@Test
	public void invalidCommandTest() {
		setupOutput();
		TextBuddy.testCommands("a");
		assertEquals("command: \nInvalid Command! "
	                 + "Please use: add, display, delete, clear, exit.", output.toString().trim());
		resetOutput();
	}

	@Test
	public void addSuccessTest() {
		setupOutput();
		TextBuddy.testCommands("add fish");
		assertTrue("fish", TextBuddy.getTextList().contains("fish"));
		assertEquals("\ncommand: \nadded to mytextfile.txt: \"fish\"", "\n" + output.toString().trim());
		resetOutput();
	}

	@Test
	public void addInvalidTest() {
		setupOutput();
		TextBuddy.testCommands("add ");
		assertEquals("command: \nInvalid Command! Please use the command: add <String>", output.toString().trim());
		resetOutput();
	}

	@After
	public void endTests() {
		File file = new File("mytextfile.txt");
		file.delete();
	}

}
