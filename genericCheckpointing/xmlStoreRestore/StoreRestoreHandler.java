package genericCheckpointing.xmlStoreRestore;

import genericCheckpointing.util.FileProcessor;
import genericCheckpointing.util.SerializableObject;

import genericCheckpointing.strategy.SerStrategy;
import genericCheckpointing.strategy.DeserStrategy;
import genericCheckpointing.strategy.XMLSerialization;
import genericCheckpointing.strategy.XMLDeserialization;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.Throwable;
import java.util.ArrayList;

public class StoreRestoreHandler implements InvocationHandler {

	private FileProcessor processor;

	public StoreRestoreHandler(FileProcessor processorIn) {
		this.processor = processorIn;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String className = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();

		if (className.equals("StoreI") && methodName.equals("writeObj")) {
			String serializeStr = new String();
			if (args[2].equals("XML")) {
				serializeStr = serializeObject((SerializableObject) args[0], new XMLSerialization());
			}
			processor.writeLine(serializeStr);
			processor.flush();
		} else if (className.equals("RestoreI") && methodName.equals("readObj")) {
			// figure out string to deserialize
			ArrayList<String> dString = new ArrayList<String>();
			String line = processor.readLine();
			if (line.equals("<DPSerialization>")) { // if equal we know it is the start of seriaization
				line = processor.readLine(); //get the next line for while loop
				while (line != null && !line.equals("</DPSerialization>")) { // keep reading until null or line indicates stop
					dString.add(line); // store the line 
					line = processor.readLine(); // read the next line
				}
			}

			SerializableObject deserializeObj = new SerializableObject();
			if (dString.size() >= 2) { // dString needs to be atleast size 3 for it work;
				if (args[0].equals("XML")) {
					deserializeObj = deserializeString(dString, new XMLDeserialization());
				}
			}
			return deserializeObj;
		}

		return null;
	}

	public String serializeObject(SerializableObject sObject, SerStrategy sStrategy) {
		return sStrategy.serialize(sObject);
	}

	public SerializableObject deserializeString(ArrayList<String> dString, DeserStrategy dStrategy) {
		return dStrategy.deserialize(dString);
	}
}
