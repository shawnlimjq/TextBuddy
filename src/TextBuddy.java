import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Lim Jun Qi Shawn A0125417L T16
 * 
 *         TextBuddy is a Command Line Interface (CLI) program that uses java to
 *         store text on a .txt file.
 * 
 *         This program will create a new text file or read the specified text
 *         file if it exists.
 * 
 *         The user can input the following commands to manipulate the texts: 1)
 *         add <String> 2) display 3) delete <index> 4) clear 5) exit 6) search
 *         7) sort
 *
 *         This program will terminate and save all texts to the text file when
 *         the exit command is given.
 * 
 *         =====================================================================
 *         Assumptions: 1) Users will exit program by the exit command. 2) Users
 *         will not add/search/delete spaces.
 *         =====================================================================
 *
 */
public class TextBuddy {

	// Global Variables
	private static String fileName;
	private static ArrayList<String> textList;
	private static Scanner sc = new Scanner(System.in);
	private static PrintWriter printWriter = null;

	// Parameter size for no arguments check
	private static int PARAM_SIZE_FOR_NO_AGUMENTS = 0;

	// parameter size check for empty file
	private static int PARAM_SIZE_FOR_EMPTY_FILE = 0;

	// parameter size check for empty add input
	private static int PARAM_SIZE_FOR_EMPTY_ADD_INPUT = 0;

	// parameter size check for empty add input
	private static int PARAM_SIZE_FOR_EMPTY_SEARCH_INPUT = 0;

	// Parameter size for correct .txt file
	private static int PARAM_SIZE_FOR_VALID_FILE = 4;

	// Parameter size for correct delete function
	private static int PARAM_SIZE_FOR_DELETE = 2;

	// Parameter size for correct search function
	private static int PARAM_SIZE_FOR_SEARCH = 2;

	// Parameter size for correct add function
	private static int PARAM_SIZE_FOR_ADD = 2;

	// Parameter size for correct add, delete function splitting
	private static int PARAM_SIZE_FOR_COMMANDS = 2;

	// Command Messages
	private static String MESSAGE_COMMAND_PROMPT = "\ncommand: ";
	private static String MESSAGE_OPENING = "Welcome to TextBuddy. %1$s is ready for use";
	private static String MESSAGE_FILE_ADDED = "added to %1$s: \"%2$s\"";
	private static String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static String MESSAGE_FILE_CLEARED = "all content deleted from %1$s";
	private static String MESSAGE_FILE_EMPTY = "%1$s is empty";
	private static String MESSAGE_NOT_FOUND = "%1$s not found";
	private static String MESSAGE_SORTED = "%1$s sorted in ascending order";

	// Validation/error Messages
	private static String MESSAGE_NO_INPUT = "No Input!";
	private static String MESSAGE_GUIDE = "Please try again!\njava TextBuddy <filename.txt>";
	private static String MESSAGE_INVALID_EXTENSION = "Invalid File Extension. ";
	private static String MESSAGE_INVALID_INPUT = "Invalid Input!";
	private static String MESSAGE_INVALID_COMMAND = "Invalid Command! "
			+ "Please use: add, display, delete, clear, exit.";
	private static String MESSAGE_INVALID_ADD = "Invalid Command! Please use the command: add <String>";
	private static String MESSAGE_INVALID_DELETE = "Invalid Command! " + "Please use the command: delete <Integer>";
	private static String MESSAGE_INVALID_SEARCH = "Invalid Command! " + "Please use the command: search <Integer>";
	private static String MESSAGE_INVALID_DELETE_INPUT = "Invalid Input! Please use an integer that "
			+ "is lower or equal to %1$s";
	private static String MESSAGE_INVALID_SEARCH_INPUT = "Please search one word only";

	// Command Types
	enum CommandType {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	public static void main(String[] args) {
		checkStartupArguments(args);
		executeCommands();
	}

	/*
	 * Checks if the start up arguments that the user input is valid to continue
	 * to check for existing text file and initialize the program else show an
	 * error message and a guide on how to use the program.
	 *
	 */
	private static void checkStartupArguments(String[] args) {
		if (noArgumentsEntered(args)) {
			printMessage(MESSAGE_NO_INPUT);
			startupErrorGuide();
		} else if (invalidFileName(args[0])) {
			printMessage(MESSAGE_INVALID_INPUT);
			startupErrorGuide();
		} else if (invalidFileExtension(args[0])) {
			printMessage(MESSAGE_INVALID_EXTENSION);
			startupErrorGuide();
		} else {
			fileName = args[0];
			textList = new ArrayList<String>();
			printMessage(String.format(MESSAGE_OPENING, fileName));
			checkFile();
		}
	}

	/*
	 * Checks if the user keyed in any inputs
	 */
	public static boolean noArgumentsEntered(String[] args) {
		return args.length == PARAM_SIZE_FOR_NO_AGUMENTS;
	}

	/*
	 * Checks if the user has input a minimum of 4 letters (.txt)
	 */
	public static boolean invalidFileName(String fileName) {
		return fileName.length() <= PARAM_SIZE_FOR_VALID_FILE;
	}

	/*
	 * Checks if the user has input a .txt file
	 */
	public static boolean invalidFileExtension(String fileName) {
		return !(fileName.substring(((fileName.length()) - PARAM_SIZE_FOR_VALID_FILE), fileName.length())
				.equalsIgnoreCase(".txt"));
	}

	/*
	 * Call to method to check if file exists
	 */
	private static void checkFile() {
		try {
			File file = new File(fileName);
			createOrRead(file);

		} catch (IOException e) {
		}
	}

	/*
	 * Creates file if specified file does not exist else read the file
	 */
	private static void createOrRead(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			readFile(file);
		}
	}

	/*
	 * Reads an existing file and extracts all the data that is stored in it
	 */
	private static void readFile(File file) throws IOException {
		String text = "";
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		while ((text = br.readLine()) != null) {
			textList.add(text);
		}
		br.close();
	}

	/*
	 * Writes all text that needs to be stored to the specified text file Each
	 * text on a new line
	 */
	private static void writeToFile() {
		try {
			printWriter = new PrintWriter(new FileOutputStream(fileName, false));
			for (int i = 0; i < textList.size(); i++) {
				printWriter.write(textList.get(i) + "\n");
			}
		} catch (Exception e) {
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	/*
	 * Prompts the user for commands and acts accordingly add: Checks if valid
	 * command before adding display: Displays all the text delete: Checks if
	 * valid command before deleting clear: Clears the text file exit: Commit to
	 * file sort: Sorts all task alphabetically search: Search the text file for
	 * tasks containing input and returns all that contains it default: invalid
	 * input message
	 */
	private static void executeCommands() {
		String userInput = "";
		while (true) {
			System.out.print(MESSAGE_COMMAND_PROMPT);
			userInput = sc.nextLine();
			// splits the commands into 2 parts
			String inputs[] = userInput.split(" ", PARAM_SIZE_FOR_COMMANDS);
			CommandType commandType = getCommandType(inputs[0]);
			switch (commandType) {
			case DISPLAY:
				displayFileData();
				break;

			case ADD:
				executeAddCommand(inputs);
				break;

			case DELETE:
				executeDeleteCommand(inputs);
				break;

			case CLEAR:
				clearFile();
				break;

			case EXIT:
				writeToFile();
				System.exit(0);
				break;

			case SORT:
				sortCommand();
				break;

			case SEARCH:
				executeSearchCommand(inputs);
				break;

			default:
				printMessage(MESSAGE_INVALID_COMMAND);
				break;
			}
		}
	}

	/*
	 * Gets the command according to the input
	 */
	public static CommandType getCommandType(String commandTypeString) {
		if (commandTypeString == null)
			printMessage(MESSAGE_INVALID_COMMAND);
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return CommandType.SORT;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else {
			return CommandType.INVALID;
		}
	}

	/*
	 * Check if valid then sorts the texts in ascending order
	 */
	private static void sortCommand() {
		if (!textList.isEmpty()) {
			Collections.sort(textList);
			printMessage(String.format(MESSAGE_SORTED, fileName));
		} else {
			printMessage(String.format(MESSAGE_FILE_EMPTY, fileName));
		}
	}

	/*
	 * Check if valid search command and returns
	 */
	private static void executeSearchCommand(String[] inputs) {
		if (checkSearchParameters(inputs)) {
			checkValidSearchInput(inputs);
		}
	}

	/*
	 * Check if there is command and search input
	 */
	private static boolean checkSearchParameters(String[] inputs) {
		if (inputs.length != PARAM_SIZE_FOR_SEARCH) {
			printMessage(MESSAGE_INVALID_SEARCH);
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Checks if search command has a space between the search and task
	 */
	private static void checkValidSearchInput(String[] inputs) {
		if (inputs[1].length() != PARAM_SIZE_FOR_EMPTY_SEARCH_INPUT) {
			checkSearchWordLimit(inputs);
		} else {
			printMessage(MESSAGE_INVALID_SEARCH);
		}
	}

	/*
	 * Checks if the search input has one word only
	 */
	private static void checkSearchWordLimit(String[] inputs) {
		if (!inputs[1].contains(" ")) {
			search(inputs[1]);
		} else {
			printMessage(MESSAGE_INVALID_SEARCH_INPUT);
		}
	}

	/*
	 * Checks and returns all lines containing the input else return text not
	 * found
	 */
	private static void search(String text) {
		boolean found = false;
		if (!textList.isEmpty()) {
			for (String currentText : textList) {
				if (currentText.contains(text)) {
					System.out.println("\n" + currentText);
					found = true;
				}
			}
			if (isNotFound(found)) {
				printMessage(String.format(MESSAGE_NOT_FOUND, text));
			}
		} else {
			printMessage(String.format(MESSAGE_FILE_EMPTY, fileName));
		}
	}

	/*
	 * Checks if text is found
	 */
	private static boolean isNotFound(boolean found) {
		return found == false;
	}

	/*
	 * Check if the input is valid else return error messages Checks for empty
	 * file, wrong command usage and invalid inputs Prompts again after this
	 */
	private static void executeDeleteCommand(String[] inputs) {
		checkValidDelete(inputs);
	}

	/*
	 * Calls for methods to check the inputs of delete method
	 */
	private static void checkValidDelete(String[] inputs) {
		if (checkDeleteParameters(inputs)) {
			if (isInteger(inputs[1])) {
				checkEmptyFile();
				checkWithinRange(inputs);
				checkInputValid(inputs);
			}
		} else {
			printMessage(MESSAGE_INVALID_DELETE);
		}
	}

	/*
	 * Check if the input is valid then do the operation
	 */
	private static void checkInputValid(String[] inputs) {
		if (Integer.parseInt(inputs[1]) <= textList.size()
				&& !(Integer.parseInt(inputs[1]) <= PARAM_SIZE_FOR_EMPTY_FILE)) {
			deleteLineOfData(inputs[1]);
		}
	}

	/*
	 * Check if the specified text to be deleted is in range or not
	 */
	private static void checkWithinRange(String[] inputs) {
		if (Integer.parseInt(inputs[1]) > textList.size() && textList.size() != PARAM_SIZE_FOR_EMPTY_FILE) {
			printMessage(String.format(MESSAGE_INVALID_DELETE_INPUT, textList.size()));
		}
	}

	/*
	 * Check for empty file
	 */
	private static void checkEmptyFile() {
		if (textList.size() == PARAM_SIZE_FOR_EMPTY_FILE) {
			printMessage(String.format(MESSAGE_FILE_EMPTY, fileName));
		}
	}

	/*
	 * Check for correct input parameters
	 */
	private static boolean checkDeleteParameters(String[] inputs) {
		if (inputs.length != PARAM_SIZE_FOR_DELETE) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Checks if the input is an integer
	 */
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			printMessage(String.format(MESSAGE_INVALID_DELETE_INPUT, textList.size()));
			return false;
		}
	}

	/*
	 * Calls the validate command then adds if it is valid
	 */
	private static void executeAddCommand(String[] inputs) {
		checkAddInputExists(inputs);
	}

	/*
	 * Checks if the add commands input is empty
	 */
	private static void checkAddInputExists(String[] inputs) {
		if (inputs.length == PARAM_SIZE_FOR_ADD) {
			checkValidAddInput(inputs);
		} else {
			printMessage(MESSAGE_INVALID_ADD);
		}
	}

	/*
	 * Checks if add command has a space between the add and task
	 */
	private static void checkValidAddInput(String[] inputs) {
		if (inputs[1].length() != PARAM_SIZE_FOR_EMPTY_ADD_INPUT) {
			addToFile(inputs[1]);
		} else {
			printMessage(MESSAGE_INVALID_ADD);
		}
	}

	/*
	 * Prints messages with a spacing in front and back for easy readability
	 */
	private static void printMessage(String print) {
		System.out.println("\n" + print);
	}

	/*
	 * Adds the text to the list
	 */
	private static void addToFile(String textToAdd) {
		textList.add(textToAdd);
		printMessage(String.format(MESSAGE_FILE_ADDED, fileName, textToAdd));
	}

	/*
	 * Displays all text that the user currently keyed in and display error
	 * message if the list is empty
	 */
	private static void displayFileData() {
		if (textList.size() == PARAM_SIZE_FOR_EMPTY_FILE) {
			printMessage(String.format(MESSAGE_FILE_EMPTY, fileName));
		} else {
			for (int i = 0; i < textList.size(); i++) {
				printMessage((i + 1) + ". " + textList.get(i));
			}
		}
	}

	/*
	 * Deletes the text with the input index
	 */
	private static void deleteLineOfData(String textToDeleteIndex) {
		int textIndex = Integer.parseInt(textToDeleteIndex);
		printMessage(String.format(MESSAGE_DELETED, fileName, textList.remove(textIndex - 1)));
	}

	/*
	 * Clears all texts that is in the list
	 */
	private static void clearFile() {
		textList.clear();
		printMessage(String.format(MESSAGE_FILE_CLEARED, fileName));
	}

	/*
	 * Exits the program as the user does not know how to initialize the program
	 * and show how to use the program
	 */
	private static void startupErrorGuide() {
		printMessage(MESSAGE_GUIDE);
		System.exit(0);
	}

	/*
	 * Initialise program for testing
	 *
	 */
	public static void initialiseTest(String args) {
		fileName = args;
		textList = new ArrayList<String>();
		printMessage(String.format(MESSAGE_OPENING, fileName));
		checkFile();
	}

	// Test Commands
	public static void testCommands(String userInput) {
		System.out.print(MESSAGE_COMMAND_PROMPT);
		// splits the commands into 2 parts
		String inputs[] = userInput.split(" ", PARAM_SIZE_FOR_COMMANDS);
		CommandType commandType = getCommandType(inputs[0]);
		switch (commandType) {
		case DISPLAY:
			displayFileData();
			break;

		case ADD:
			executeAddCommand(inputs);
			break;

		case DELETE:
			executeDeleteCommand(inputs);
			break;

		case CLEAR:
			clearFile();
			break;

		case EXIT:
			writeToFile();
			System.exit(0);
			break;

		case SORT:
			sortCommand();
			break;

		case SEARCH:
			executeSearchCommand(inputs);
			break;

		default:
			printMessage(MESSAGE_INVALID_COMMAND);
			break;
		}
	}

	public static ArrayList<String> getTextList() {
		return textList;
	}

}
