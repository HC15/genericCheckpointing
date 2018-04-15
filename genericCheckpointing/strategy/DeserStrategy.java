package genericCheckpointing.strategy;

import genericCheckpointing.util.SerializableObject;

import java.util.ArrayList;

public interface DeserStrategy {
	SerializableObject deserialize(ArrayList<String> dString);	
}
