package genericCheckpointing.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StringBuilder;

/**
 * The FileProcessor class is used to handle input and output file
 *
 * @author:		Harvey Chen
 * @version: 	1.0
 * @since:		2017-12-07
 */
public class FileProcessor {
	
	private FileReader fileReader;
	private FileWriter fileWriter;
	private BufferedReader bufferReader;
	private BufferedWriter bufferWriter;

	public FileProcessor(String checkpoint, boolean read) {
		try {
			this.fileReader = new FileReader(checkpoint);
			this.fileWriter = new FileWriter(checkpoint, read);
			this.bufferReader = new BufferedReader(this.fileReader);
			this.bufferWriter = new BufferedWriter(this.fileWriter);
		} catch (FileNotFoundException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Not a valid checkpoint file path, file not found");
			excep.printStackTrace();
			System.exit(1);
		} catch (IOException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Error while creating FileWriter class");
			excep.printStackTrace();
			System.exit(1);
		}
	}

	public String readLine() {
		try {
			return this.bufferReader.readLine();
		} catch(IOException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Error when reading lines from the checkpoint file");
			excep.printStackTrace();
			System.exit(1);
		}
		return "";
	}

	public void writeLine(String line) {
		try {
			this.bufferWriter.write(line);
		} catch (IOException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Error when writing line to the checkpoint file");
			excep.printStackTrace();
			System.exit(1);
		}
	}

	public void flush() {
		try {
			this.bufferWriter.flush();
		} catch (IOException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Error when flushing stream to the checkpoint file");
			excep.printStackTrace();
			System.exit(1);			
		}
	}

	public void close() {
		try {
			this.bufferReader.close();
			this.bufferWriter.close();
			this.fileReader.close();
			this.fileWriter.close();
		} catch (IOException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Error while closing FileProcessor");
			excep.printStackTrace();
			System.exit(1);
		}
	}
}
