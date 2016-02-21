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

	@Before
	@Test
	public void initialiseFileTest() {
		resetOutput();
		setupOutput();
		TextBuddy.initialiseTest("mytextfile.txt");
		assertEquals("Welcome to TextBuddy. mytextfile.txt is ready for use", output.toString().trim());
		resetOutput();
	}

	@Test
	public void invalidCommandTest() {
		setupOutput();
		TextBuddy.testCommands("a");
		assertEquals("command: \nInvalid Command! " + "Please use: add, display, delete, clear, exit.",
				output.toString().trim());
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

	@Test
	public void noItemsToDisplayTest() {
		setupOutput();
		TextBuddy.testCommands("display");
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void displayTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("display");
		assertEquals("command: \n1. a", output.toString().trim());
		resetOutput();
	}

	@Test
	public void invalidDeleteInputTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add c");
		resetOutput();
		TextBuddy.testCommands("delete 4");
		assertEquals("command: \nInvalid Input! Please use an integer that is lower or equal to 3",
				output.toString().trim());
		resetOutput();
	}

	@Test
	public void noFileDeleteTest() {
		setupOutput();
		TextBuddy.testCommands("delete 1");
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void invalidDeleteTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("delete v");
		assertEquals("command: \nInvalid Input! Please use an integer that is lower or equal to 1",
				output.toString().trim());
		resetOutput();
	}

	@Test
	public void invalidParameterDeleteTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("delete");
		assertEquals("command: \nInvalid Command! Please use the command: delete <Integer>", output.toString().trim());
		resetOutput();
	}

	@Test
	public void noDeleteInputTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("delete ");
		assertEquals("command: \nInvalid Input! Please use an integer that is lower or equal to 1",
				output.toString().trim());
		resetOutput();
	}

	@Test
	public void deleteTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add c");
		resetOutput();
		TextBuddy.testCommands("delete 2");
		assertEquals("command: \ndeleted from mytextfile.txt: \"b\"", output.toString().trim());
		resetOutput();
	}

	@Test
	public void clearTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		TextBuddy.testCommands("add b");
		resetOutput();
		TextBuddy.testCommands("clear");
		assertEquals("command: \nall content deleted from mytextfile.txt", output.toString().trim());
		resetOutput();
		TextBuddy.testCommands("display");
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void sortTest() {
		setupOutput();
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add c");
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("sort");
		assertEquals("command: \nmytextfile.txt sorted in ascending order", output.toString().trim());
		resetOutput();
		for (int i = 0; i < TextBuddy.getTextList().size(); i++) {
			System.out.print(TextBuddy.getTextList().get(i) + " ");
		}
		assertEquals("a b c", output.toString().trim());
		resetOutput();
	}

	@Test
	public void sortEmptyTest() {
		setupOutput();
		TextBuddy.testCommands("sort");
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void searchTest() {
		setupOutput();
		TextBuddy.testCommands("add apple");
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add banana");
		resetOutput();
		TextBuddy.testCommands("search a");
		assertEquals("command:  apple  banana", output.toString().trim().replace("\n", " ").replace("\r", ""));
		resetOutput();
	}

	@Test
	public void searchNoInputTest() {
		setupOutput();
		TextBuddy.testCommands("search a");
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void searchNotFoundTest() {
		setupOutput();
		TextBuddy.testCommands("add apple");
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add banana");
		resetOutput();
		TextBuddy.testCommands("search c");
		assertEquals("command: \nc not found", output.toString().trim());
		resetOutput();
	}

	@After
	public void endTests() {
		File file = new File("mytextfile.txt");
		file.delete();
	}

}
