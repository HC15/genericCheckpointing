package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;
import genericCheckpointing.util.SerializeTypes;

import java.lang.StringBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.SecurityException;
import java.lang.IndexOutOfBoundsException;
import java.lang.NoSuchMethodException;
import java.lang.NullPointerException;
import java.lang.IllegalAccessException;
import java.lang.IllegalArgumentException;
import java.lang.reflect.InvocationTargetException;
import java.lang.ExceptionInInitializerError;

public class XMLSerialization implements SerStrategy {

	private SerializeTypes serializer;

	public XMLSerialization() {
		this.serializer = new SerializeTypes();
	}

	public String serialize(SerializableObject sObject) {
		StringBuilder serializeBuilder = new StringBuilder();
		// serialize the top header
		serializeBuilder.append(serializer.header(true));
		serializeBuilder.append(serializer.complexType(true, sObject));
		// iterate through methods to find getters
		Class<?> serializeClass = sObject.getClass();
		String fieldName;
		StringBuilder methodBuilder;
		String methodName;
		Method method;
		boolean other;
		try {
			for (Field field : serializeClass.getDeclaredFields()) {
				fieldName = field.getName(); // get name of field
				methodBuilder = new StringBuilder("get"); // build method name for getters
				methodBuilder.append(Character.toUpperCase(fieldName.charAt(0)));
				methodBuilder.append(fieldName.substring(1));
				methodName = methodBuilder.toString();
				method = serializeClass.getMethod(methodName); // get the method from the class
				other = methodName.contains("Other"); // check if name has Other for correct serialization
				Object invokeRet = method.invoke(sObject); // invoke the method get the field
				serializeBuilder.append(serializer.primitive(other, invokeRet)); // append the return to serialize
			}
		} catch (SecurityException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Security manager denies access to package of this class");
			excep.printStackTrace();
			System.exit(1);
		} catch (IndexOutOfBoundsException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Invalid substring, field length is incorrect");
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
		} catch (IllegalAccessException excep) {
			System.err.println(excep.getMessage());
			System.err.println("Method of class is not accessible");
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
		} catch (ExceptionInInitializerError excep) {
			System.err.println(excep.getMessage());
			System.err.println("Initialization provoked by method has failed");
			excep.printStackTrace();
			System.exit(1);					
		}
		// serialize the closing parts
		serializeBuilder.append(serializer.complexType(false, sObject));
		serializeBuilder.append(serializer.header(false));
		return serializeBuilder.toString();
	}
}
