package io.github.oliviercailloux.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;

class JsonWrapperTest {

	@Test
	void testRaw() {
		final PrintableJsonObject obj = PrintableJsonObjectFactory.wrapRawString("{\"name\":\"value\"}");
		assertEquals("{\n    \"name\": \"value\"\n}", obj.toString());
		assertEquals("{\"name\":\"value\"}", obj.toRawString());
		assertEquals("value", obj.getString("name"));
	}

	@Test
	void testUnknown() {
		final PrintableJsonObject obj = PrintableJsonObjectFactory.wrapString("{\"name\": \"value\"}");
		assertEquals("value", obj.getString("name"));
		assertEquals("{\"name\":\"value\"}", obj.toRawString());
		assertEquals("{\n    \"name\": \"value\"\n}", obj.toString());
	}

	@Test
	void testDelegate() {
		final JsonObject realJson = Json.createObjectBuilder().add("name", "value").build();
		final PrintableJsonObject obj = PrintableJsonObjectFactory.wrapObject(realJson);
		assertEquals("{\n    \"name\": \"value\"\n}", obj.toString());
		assertEquals("{\"name\":\"value\"}", obj.toRawString());
		assertEquals("value", obj.getString("name"));
	}

}
