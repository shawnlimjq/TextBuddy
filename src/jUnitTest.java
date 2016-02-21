import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class jUnitTest {

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

	// Test Arguments
	@Test
	public void testArguments() {
		String[] noArgs = new String[0];
		String[] args = new String[2];

		assertTrue(TextBuddy.noArgumentsEntered(noArgs));
		assertFalse(TextBuddy.noArgumentsEntered(args));
	}

	// Test for file name
	@Test
	public void testFileName() {
		assertFalse(TextBuddy.invalidFileName("test.txt"));
		assertTrue(TextBuddy.invalidFileName("txt"));
	}

	// Test for file extension, need .txt
	@Test
	public void testFileExtention() {
		assertTrue(TextBuddy.invalidFileExtension("test.jpg"));
		assertFalse(TextBuddy.invalidFileExtension("test.txt"));
	}

	// Check if is an integer
	@Test
	public void checkValidInteger() {
		assertTrue(TextBuddy.isInteger("0"));
		assertFalse(TextBuddy.isInteger("hi"));
	}

	// Check for command type
	@Test
	public void checkCommandType() {
		assertEquals(TextBuddy.CommandType.ADD, TextBuddy.getCommandType("add"));
		assertEquals(TextBuddy.CommandType.ADD, TextBuddy.getCommandType("ADd"));
		assertEquals(TextBuddy.CommandType.DELETE, TextBuddy.getCommandType("delete"));
		assertEquals(TextBuddy.CommandType.DELETE, TextBuddy.getCommandType("deletE"));
		assertEquals(TextBuddy.CommandType.CLEAR, TextBuddy.getCommandType("clear"));
	}

	// Initialise the file before main tests
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
		// Test for invalid commands
		TextBuddy.testCommands("a");
		assertEquals("command: \nInvalid Command! " + "Please use: add, display, delete, clear, exit.",
				     output.toString().trim());
		resetOutput();
	}

	@Test
	public void addSuccessTest() {
		setupOutput();
		TextBuddy.testCommands("add fish");
		// Check if the input is in the textlist
		assertTrue("fish", TextBuddy.getTextList().contains("fish"));
		// Check if the message is correct
		assertEquals("\ncommand: \nadded to mytextfile.txt: \"fish\"", "\n" + output.toString().trim());
		resetOutput();
	}

	@Test
	public void addInvalidTest() {
		setupOutput();
		TextBuddy.testCommands("add ");
		// Check if the message is correct
		assertEquals("command: \nInvalid Command! Please use the command: add <String>", output.toString().trim());
		resetOutput();
	}

	@Test
	public void noItemsToDisplayTest() {
		setupOutput();
		TextBuddy.testCommands("display");
		// Check if the message is correct
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void displayTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("display");
		// Check if the message is correct
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
		// Check if the message is correct
		assertEquals("command: \nInvalid Input! Please use an integer that is lower or equal to 3",
				     output.toString().trim());
		resetOutput();
	}

	@Test
	public void noFileDeleteTest() {
		setupOutput();
		TextBuddy.testCommands("delete 1");
		// Check if the message is correct
		assertEquals("command: \nmytextfile.txt is empty", output.toString().trim());
		resetOutput();
	}

	@Test
	public void invalidDeleteTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("delete v");
		// Check if the message is correct
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
		// Check if the message is correct
		assertEquals("command: \nInvalid Command! Please use the command: delete <Integer>", output.toString().trim());
		resetOutput();
	}

	@Test
	public void noDeleteInputTest() {
		setupOutput();
		TextBuddy.testCommands("add a");
		resetOutput();
		TextBuddy.testCommands("delete ");
		// Check if the message is correct
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
		// Check if the message is correct
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
		// Check if the message is correct
		assertEquals("command: \nall content deleted from mytextfile.txt", output.toString().trim());
		resetOutput();
		TextBuddy.testCommands("display");
		// Check if the message is correct
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
		// Check if the message is correct
		assertEquals("command: \nmytextfile.txt sorted in ascending order", output.toString().trim());
		resetOutput();
		// Prints the data and checks if correct
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
		// Check if the message is correct
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
		// Check if the data is correct
		assertEquals("command:  apple  banana", output.toString().trim().replace("\n", " ").replace("\r", ""));
		resetOutput();
	}

	// Search for more than one word
	@Test
	public void searchWordsTest() {
		setupOutput();
		TextBuddy.testCommands("add apple pie");
		TextBuddy.testCommands("add b");
		TextBuddy.testCommands("add banana");
		resetOutput();
		TextBuddy.testCommands("search ple pie");
		// Check if the message is correct
		assertEquals("command: \nPlease search one word only", output.toString().trim());
		resetOutput();
	}

	@Test
	public void searchNoInputTest() {
		setupOutput();
		TextBuddy.testCommands("search a");
		// Check if the message is correct
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
		// Check if the message is correct
		assertEquals("command: \nc not found", output.toString().trim());
		resetOutput();
	}

	@After
	public void endTests() {
		// Deletes the file so that the next run will not have extra data
		File file = new File("mytextfile.txt");
		file.delete();
	}

}
