package genericCheckpointing.util;

import java.util.Objects;

public class MyAllTypesSecond extends SerializableObject {

	private double myDoubleT;
	private double myOtherDoubleT;
	private float myFloatT;
	private short myShortT;
	private short myOtherShortT;
	private char myCharT;

	public MyAllTypesSecond() {
		this.myDoubleT = 0.0d;
		this.myOtherDoubleT = 0.0d;
		this.myFloatT = 0.0f;
		this.myShortT = (short) 0;
		this.myOtherShortT = (short) 0;
		this.myCharT = '\0';
	}

	public MyAllTypesSecond(double myDoubleTIn, double myOtherDoubleTIn, float myFloatTIn, 
							short myShortTIn, short myOtherShortTIn, char myCharTIn) {
		this.myDoubleT = myDoubleTIn;
		this.myOtherDoubleT = myOtherDoubleTIn;
		this.myFloatT = myFloatTIn;
		this.myShortT = myShortTIn;
		this.myOtherShortT = myOtherShortTIn;
		this.myCharT = myCharTIn;
	}

	public double getMyDoubleT() {
		return this.myDoubleT;
	}

	public double getMyOtherDoubleT() {
		return this.myOtherDoubleT;
	}
		
	public float getMyFloatT() {
		return this.myFloatT;
	}
	
	public short getMyShortT() {
		return this.myShortT;
	}

	public short getMyOtherShortT() {
		return this.myOtherShortT;
	}

	public char getMyCharT() {
		return this.myCharT;
	}

	public void setMyDoubleT(double myDoubleTIn) {
		this.myDoubleT = myDoubleTIn;
	}

	public void setMyOtherDoubleT(double myOtherDoubleTIn) {
		this.myOtherDoubleT = myOtherDoubleTIn;
	}

	public void setMyFloatT(float myFloatTIn) {
		this.myFloatT = myFloatTIn;
	}

	public void setMyShortT(short myShortTIn) {
		this.myShortT = myShortTIn;
	}

	public void setMyOtherShortT(short myOtherShortTIn) {
		this.myOtherShortT = myOtherShortTIn;
	}

	public void setMyCharT(char myCharTIn) {
		this.myCharT = myCharTIn;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MyAllTypesSecond)) {
			return false;
		}

		MyAllTypesSecond objSecond = (MyAllTypesSecond) obj;
		return (this.myDoubleT == objSecond.getMyDoubleT() &&
				this.myOtherDoubleT == objSecond.getMyOtherDoubleT() &&
				this.myFloatT == objSecond.getMyFloatT() &&
				this.myShortT == objSecond.getMyShortT() &&
				this.myOtherShortT == objSecond.getMyOtherShortT() &&
				this.myCharT == objSecond.getMyCharT());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.myDoubleT, this.myOtherDoubleT, this.myFloatT,
							this.myShortT, this.myOtherShortT, this.myCharT);
	}

	@Override
	public String toString() {
		StringBuilder infoBuilder = new StringBuilder("MyAllTypesSecond Information:\n");
		
		infoBuilder.append("myDoubleT = ");
		infoBuilder.append(this.myDoubleT);
		infoBuilder.append('\n');

		infoBuilder.append("myOtherDoubleT = ");
		infoBuilder.append(this.myOtherDoubleT);
		infoBuilder.append('\n');

		infoBuilder.append("myFloatT = ");
		infoBuilder.append(this.myFloatT);
		infoBuilder.append('\n');

		infoBuilder.append("myShortT = ");
		infoBuilder.append(this.myShortT);
		infoBuilder.append('\n');

		infoBuilder.append("myOtherShortT = ");
		infoBuilder.append(this.myOtherShortT);
		infoBuilder.append('\n');

		infoBuilder.append("myCharT = ");
		infoBuilder.append(this.myCharT);
		infoBuilder.append('\n');

		return infoBuilder.toString();
	}
}
