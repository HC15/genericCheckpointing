package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

import java.lang.StringBuilder;
import java.lang.reflect.Method;
import java.lang.IndexOutOfBoundsException;
import java.lang.ClassNotFoundException;
import java.lang.ExceptionInInitializerError;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.lang.SecurityException;
import java.lang.NoSuchMethodException;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class XMLDeserialization implements DeserStrategy {

	public XMLDeserialization() {}

	public SerializableObject deserialize(ArrayList<String> dString) {
		SerializableObject failure = new SerializableObject();
		// check if object is serialized correct
		if (!dString.get(0).contains("complexType") || 
			!dString.get(dString.size() - 1).contains("</complexType>")) {
			System.err.println("Object was not serialized correctly");
			return failure;
		}
		// parse the first line for name of class
		String line = dString.get(0);
		int substrBegin = line.indexOf('\"') + 1;
		int substrEnd = line.lastIndexOf('\"');
		try {
			String className = line.substring(substrBegin, substrEnd);
			Class<?> serializeClass = Class.forName(className);
			SerializableObject serialObj = (SerializableObject) serializeClass.newInstance();
			// loop the middles lines to set the values in the newly created object
			String name;
			String value;
			StringBuilder methodBuilder;
			Method method;
			Class<?>[] signature = new Class<?>[1];
			Class<?> caster = Integer.class;
			Object arg = new Object();
			for (int i = 1; i < dString.size() - 1; i++) {
				line = dString.get(i);
				// get name of variable, every line ends with </myName>
				substrBegin = line.lastIndexOf('/') + 1;
				substrEnd = line.lastIndexOf('>');
				name = line.substring(substrBegin, substrEnd);
				// get value, every format is >value<
				substrBegin = line.indexOf('>') + 1;
				substrEnd = line.lastIndexOf('<');
				value = line.substring(substrBegin, substrEnd);
				// build the method name
				methodBuilder = new StringBuilder("set");
				methodBuilder.append(Character.toUpperCase(name.charAt(0)));
				methodBuilder.append(name.substring(1));
				// use the switch on name to figure out parameter and what to cast
				switch (name) {
					case "myInt":
					case "myOtherInt":
						signature[0] = int.class;
						caster = Integer.class;
						arg = Integer.parseInt(value);
						break;
					case "myLong":
					case "myOtherLong":
						signature[0] = long.class;
						caster = Long.class;
						arg = Long.parseLong(value);
						break;
					case "myString":
						signature[0] = String.class;
						caster = String.class;
						arg = value;
						break;
					case "myBool":
						signature[0] = boolean.class;
						caster = Boolean.class;
						arg = Boolean.parseBoolean(value);
						break;
					case "myDoubleT":
					case "myOtherDoubleT":
						signature[0] = double.class;
						caster = Double.class;
						arg = Double.parseDouble(value);
						break;
					case "myFloatT":
						signature[0] = float.class;
						caster = Float.class;
						arg = Float.parseFloat(value);
						break;
					case "myShortT":
					case "myOtherShortT":
						signature[0] = short.class;
						caster = Short.class;
						arg = Short.parseShort(value);
						break;
					case "myCharT":
						signature[0] = char.class;
						caster = Character.class;
						arg = value.charAt(0);
						break;
				}
				// get the method and invoke it on the object created earlier
				method = serializeClass.getMethod(methodBuilder.toString(), signature);
				method.invoke(serialObj, caster.cast(arg));
			}
			return serialObj;
		} catch (IndexOutOfBoundsException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Invalid substring, line has not been serialized correctly");
			excep.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Class not found, object so class can't be set");
			excep.printStackTrace();
			System.exit(1);
		} catch (ExceptionInInitializerError excep) {
			System.err.println(excep.getMessage());
			System.err.println("Initialization provoked by method has failed");
			excep.printStackTrace();
			System.exit(1);					
		} catch (InstantiationException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Instantiation on this object has failed");
			excep.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Constructor or method of class is not accessible");
			excep.printStackTrace();
			System.exit(1);
		} catch (SecurityException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Security manager denies access to package of this class");
			excep.printStackTrace();
			System.exit(1);
		} catch (NoSuchMethodException excep) {
			System.err.println(excep.getMessage());
			System.err.println("No matching method is found in class");
			excep.printStackTrace();
			System.exit(1);
		} catch (NullPointerException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Method name is null or specified object is null");
			excep.printStackTrace();
			System.exit(1);
		} catch (IllegalArgumentException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Method has been passed illegal or incorrect argument");
			excep.printStackTrace();
			System.exit(1);
		} catch (InvocationTargetException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Invoked method has thrown an exception");
			excep.printStackTrace();
			System.exit(1);
		}
		// shouldn't reach her if object was returned correctly;
		return failure;
	}
}
