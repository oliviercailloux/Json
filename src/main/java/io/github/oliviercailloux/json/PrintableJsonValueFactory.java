package io.github.oliviercailloux.json;

import jakarta.json.JsonValue;

public class PrintableJsonValueFactory {

	public static PrintableJsonValue wrapRawString(String raw) {
		return JsonValueGeneralWrapper.wrapRaw(raw);
	}

	public static PrintableJsonValue wrapPrettyPrintedString(String prettyPrinted) {
		return JsonValueGeneralWrapper.wrapPrettyPrinted(prettyPrinted);
	}

	public static PrintableJsonValue wrapString(String unknownForm) {
		return JsonValueGeneralWrapper.wrapUnknown(unknownForm);
	}

	public static PrintableJsonValue wrapValue(JsonValue value) {
		if (value instanceof PrintableJsonValue) {
			return (PrintableJsonValue) value;
		}
		return JsonValueGeneralWrapper.wrapDelegate(value);
	}

	private PrintableJsonValueFactory() {
		/** Can’t be instantiated. */
	}

}
