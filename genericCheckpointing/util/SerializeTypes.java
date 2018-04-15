package genericCheckpointing.util;

import java.lang.StringBuilder;

public class SerializeTypes {

	public SerializeTypes() {}

	public String header(boolean top) {
		StringBuilder serializeBuilder = new StringBuilder();
		serializeBuilder.append('<');
		if (!top) {
			serializeBuilder.append('/');
		}
		serializeBuilder.append("DPSerialization>\n");
		return serializeBuilder.toString();
	}

	public String complexType(boolean top, Object type) {
		if (top) {
			StringBuilder serializeBuilder = new StringBuilder(); 
			serializeBuilder.append(" <complexType xsi:type=\"");
			serializeBuilder.append(type.getClass().getName());
			serializeBuilder.append("\">\n");
			return serializeBuilder.toString();
		} else {
			return " </complexType>\n";
		}
	}

	// Name of method isn't entirely accurate since String isn't a primitive
	public String primitive(boolean other, Object value) {
		String name = "";
		String type = "";
		switch (value.getClass().getSimpleName()) {
			case "Integer":
				if ((int) value > 10) {
					name = "Int";
					type = "int";
				}
				break;
			case "Long":
				if ((long) value > 10L) {
					name = "Long";
					type = "long";
				}
				break;
			case "String":
				name = "String";
				type = "string";
				break;
			case "Boolean":
				name = "Bool";
				type = "boolean";
				break;
			case "Double":
				if ((double) value > 10.0d) {
					name = "DoubleT";
					type = "double";
				}
				break;
			case "Float":
				name = "FloatT";
				type = "float";
				break;
			case "Short":
				name = "ShortT";
				type = "short";
				break;
			case "Character":
				name = "CharT";
				type = "char";
				break;
		}
		// if name and type weren't set then object can't be serialized
		if (name.equals("") && type.equals("")) {
			return name;
		}

		StringBuilder serializeBuilder = new StringBuilder();
		// start of serialization, write variable name
		serializeBuilder.append("  <my");
		if (other) {
			serializeBuilder.append("Other");
		}
		serializeBuilder.append(name);
		// serialize type
		serializeBuilder.append(" xsi:type=\"xsd:");
		serializeBuilder.append(type);
		serializeBuilder.append("\">");
		// serialize value of inputted object
		serializeBuilder.append(value);
		// close serialization, by writing variable name
		serializeBuilder.append("</my");
		if (other) {
			serializeBuilder.append("Other");
		}
		serializeBuilder.append(name);
		serializeBuilder.append(">\n");
		// return the serialized primitive as a string
		return serializeBuilder.toString();
	}
}
