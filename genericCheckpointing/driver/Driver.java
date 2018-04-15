package genericCheckpointing.driver;

import genericCheckpointing.util.Validation;
import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.ProxyCreator;
import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.MyAllTypesFirst;
import genericCheckpointing.util.MyAllTypesSecond;

import genericCheckpointing.server.StoreRestoreI;
import genericCheckpointing.server.StoreI;
import genericCheckpointing.server.RestoreI;

import genericCheckpointing.xmlStoreRestore.StoreRestoreHandler;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {

	public static void main(String[] args) {
		Validation validator = new Validation();
		if (!validator.commandLine(args)) {
			System.err.println("Invalid command line arguments, system will exit");
			System.exit(1);
		}

		String mode = args[0];
		int NUM_OF_OBJECTS = Integer.parseInt(args[1]);
		FileProcessor processor = new FileProcessor(args[2], mode.equals("deser"));

		ProxyCreator pc = new ProxyCreator();
		StoreRestoreHandler handler = new StoreRestoreHandler(processor);
		StoreRestoreI cpointRef = (StoreRestoreI) pc.createProxy(new Class<?>[] {StoreI.class, RestoreI.class}, handler);

		SerializableObject myRecordRet;
		ArrayList<SerializableObject> deserializeList = new ArrayList<SerializableObject>();
		switch (mode) {
			case "serdeser":
				ThreadLocalRandom randomizer = ThreadLocalRandom.current();
				StringBuilder randomBuilder;
				SerializableObject myFirst;
				SerializableObject mySecond;
				ArrayList<SerializableObject> serializeList = new ArrayList<SerializableObject>();
				for (int i = 0; i < NUM_OF_OBJECTS; i++) {

					randomBuilder = new StringBuilder(); // make a random string using characters
					for (int stringIndex = 0; stringIndex < ((i % 10) + 5); stringIndex++) { // use i to get length, between 5 - 15
						randomBuilder.append((char) randomizer.nextInt(32, 128)); // first 32 ascii values are nonsense
					}

					myFirst = new MyAllTypesFirst(randomizer.nextInt(Integer.MAX_VALUE) / (i + 1), // divide int by i + 1
													randomizer.nextInt(Integer.MAX_VALUE / (i + 1)), // divide the max possible int by i + 1
													randomizer.nextLong(Long.MAX_VALUE) / ((i * i) + 1), // divide long by i squared 
													randomizer.nextLong(Long.MAX_VALUE / ((i * i) + 1)), // divide max long by i squared
													randomBuilder.toString(), // use string generated earlier
													randomizer.nextBoolean()); // random boolean
					mySecond = new MyAllTypesSecond(randomizer.nextDouble(Double.MAX_VALUE) / (i * 100000), // divide by (i * 100000)
													randomizer.nextDouble(Double.MAX_VALUE / (i * 100000)), // divide max possible by (i * 100000)
													randomizer.nextFloat() * ((i * i) + 1), // mutiply random float by i squared
													(short) randomizer.nextInt(Short.MAX_VALUE), // just a random short
													(short) randomizer.nextInt(Short.MAX_VALUE - (short) (i * i)), // subtract max possibly by i squared
													(char) randomizer.nextInt(32, 128)); // no random char so random int 32-127 to char

					((StoreI) cpointRef).writeObj(myFirst, 0, "XML");
					((StoreI) cpointRef).writeObj(mySecond, 0, "XML");

					serializeList.add(myFirst);
					serializeList.add(mySecond);
				}

				for (int j = 0; j < 2 * NUM_OF_OBJECTS; j++) {
					myRecordRet = ((RestoreI) cpointRef).readObj("XML");
					deserializeList.add(myRecordRet);
				}

				int mismatchedObjs = 0; // mismatch can be greater than 1 if some fields are less than 10 but unlikely with the random
				for (SerializableObject write : serializeList) {
					if (!deserializeList.contains(write)) {
						System.out.println(write.toString()); // if it isn't match then print out the write info to see fields
						mismatchedObjs++;
					}
				}
				System.out.println(String.valueOf(mismatchedObjs) + " mismatched objects");
				break;

			case "deser":
				for (int k = 0; k < NUM_OF_OBJECTS; k++) {
					myRecordRet = ((RestoreI) cpointRef).readObj("XML");
					deserializeList.add(myRecordRet);
				}
				for (SerializableObject read : deserializeList) {
					System.out.println(read.toString());
				}
				break;
		}
		
		processor.close();
	}
}
