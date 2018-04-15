package genericCheckpointing.util;

import java.io.File;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.lang.NullPointerException;
import java.lang.SecurityException;

/**
 * The Validation class is a utility to check user input
 * it contains a method that checks if the command line arguments
 * are valid for this program
 *
 * @author:		Harvey Chen
 * @version: 	1.2
 * @since:		2017-12-06
 */
public class Validation {

	/** 
	 * Validation() is the default constructor of the Validation class.
	 *
	 * @param: None.
	 * @return: None.
	 */
	public Validation() {}

	/** 
	 * commandLine() checks if command line arguments are valid
	 *
	 * @param: input: a String array that contains the command line arguments from main
	 * @return: Boolean that indicates wheter command line arguments are valid
	 */
	public boolean commandLine(String[] input) {
		if (input.length == 3) {
			String mode = input[0];
			if (!mode.equals("serdeser") && !mode.equals("deser")) {
				System.err.println("Invalid mode input, should be either serdeser or deser");
				return false;
			}

			try {
				int numOfObjects = Integer.parseInt(input[1]);

				File checkpointFile = new File(input[2]);
				if (checkpointFile.isFile()) {
					if (mode.equals("serdeser") && !checkpointFile.canWrite()) {	
						System.err.println("Invalid checkpoint file for serdeser, file can't be written to");
						return false;
					}
					if (!checkpointFile.canRead()) {
						System.err.println("Invalid checkpoint file, file can't be read");
						return false;
					}
				} else {
					if (mode.equals("deser")) {
						System.err.println("Invalid checkpoint file for deser, file does not exist");
						return false;
					} else {
						checkpointFile.createNewFile();
					}
				}
			} catch (NumberFormatException excep) {
				System.err.println(excep.getMessage());
				System.err.println("Second argument is not a number, invalid number of objects");
				excep.printStackTrace();
				return false;
			} catch (NullPointerException excep) {
				System.err.println(excep.getMessage());
				System.err.println("Invalid path of input or output file, cannot be null");
				excep.printStackTrace();
				return false;
			} catch (SecurityException excep) {
				System.err.println(excep.getMessage());
				System.err.println("Security manager denies access to input or output file, file can't be accessed");
				excep.printStackTrace();
				return false;
			} catch (IOException excep) {
				System.err.println(excep.getMessage());
				System.err.println("IOException occured while creating output file");
				excep.printStackTrace();
				return false;
			}

			return true;
		} else {
			System.err.println("Invalid number of arguments, should have 4 arguments");
			System.err.println("First argument should be mode, either serdeser or deser");
			System.err.println("Second argument should be number of objects");
			System.err.println("In serdeser mode this refers to number of MyAllTypesFirst and MyAllTypesSecond");
			System.err.println("In deser mode this refers to number of objects to be deserialized");
			System.err.println("Third argument should be path of input file");
			System.err.println("Fourth argument should be path of output file");
			return false;
		}
	}
}
