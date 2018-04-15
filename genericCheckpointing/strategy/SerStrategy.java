package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

public interface SerStrategy {
	String serialize(SerializableObject sObject);
}
