package genericCheckpointing.server;

import genericCheckpointing.util.SerializableObject;

public interface StoreI extends StoreRestoreI {
	void writeObj(SerializableObject record, int authID, String wireFormat);
}
