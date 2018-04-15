package genericCheckpointing.util;

import java.lang.StringBuilder;
import java.util.Objects;

public class MyAllTypesFirst extends SerializableObject {

	private int myInt;
	private int myOtherInt;
	private long myLong;
	private long myOtherLong;
	private String myString;
	private boolean myBool;

	public MyAllTypesFirst() {
		this.myInt = 0;
		this.myOtherInt = 0;
		this.myLong = 0L;
		this.myOtherLong = 0L;
		this.myString = "";
		this.myBool = false;
	}

	public MyAllTypesFirst(int myIntIn, int myOtherIntIn, long myLongIn,
							long myOtherLongIn, String myStringIn, boolean myBoolIn) {
		this.myInt = myIntIn;
		this.myOtherInt = myOtherIntIn;
		this.myLong = myLongIn;
		this.myOtherLong = myOtherLongIn;
		this.myString = myStringIn;
		this.myBool = myBoolIn;
	}

	public int getMyInt() {
		return this.myInt;
	}

	public int getMyOtherInt() {
		return this.myOtherInt;
	}
		
	public long getMyLong() {
		return this.myLong;
	}

	public long getMyOtherLong() {
		return this.myOtherLong;
	}

	public String getMyString() {
		return this.myString;
	}

	public boolean getMyBool() {
		return this.myBool;
	}

	public void setMyInt(int myIntIn) {
		this.myInt = myIntIn;
	}

	public void setMyOtherInt(int myOtherIntIn) {
		this.myOtherInt = myOtherIntIn;
	}

	public void setMyLong(long myLongIn) {
		this.myLong = myLongIn;
	}

	public void setMyOtherLong(long myOtherLongIn) {
		this.myOtherLong = myOtherLongIn;
	}

	public void setMyString(String myStringIn) {
		this.myString = myStringIn;
	}

	public void setMyBool(boolean myBoolIn) {
		this.myBool = myBoolIn;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MyAllTypesFirst)) {
			return false;
		}

		MyAllTypesFirst objFirst = (MyAllTypesFirst) obj;
		return (this.myInt == objFirst.getMyInt() &&
				this.myOtherInt == objFirst.getMyOtherInt() &&
				this.myLong == objFirst.getMyLong() &&
				this.myOtherLong == objFirst.getMyOtherLong() &&
				this.myString.equals(objFirst.getMyString()) &&
				this.myBool == objFirst.getMyBool());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.myInt, this.myOtherInt, this.myLong,
							this.myOtherLong, this.myString, this.myBool);
	}

	@Override
	public String toString() {
		StringBuilder infoBuilder = new StringBuilder("MyAllTypesFirst Information:\n");
		
		infoBuilder.append("myInt = ");
		infoBuilder.append(this.myInt);
		infoBuilder.append('\n');

		infoBuilder.append("myOtherInt = ");
		infoBuilder.append(this.myOtherInt);
		infoBuilder.append('\n');

		infoBuilder.append("myLong = ");
		infoBuilder.append(this.myLong);
		infoBuilder.append('\n');

		infoBuilder.append("myOtherLong = ");
		infoBuilder.append(this.myOtherLong);
		infoBuilder.append('\n');

		infoBuilder.append("myString = ");
		infoBuilder.append(this.myString);
		infoBuilder.append('\n');

		infoBuilder.append("myBool = ");
		infoBuilder.append(this.myBool);
		infoBuilder.append('\n');

		return infoBuilder.toString();
	}
}
