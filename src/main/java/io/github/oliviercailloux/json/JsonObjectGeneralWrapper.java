package io.github.oliviercailloux.json;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

@SuppressWarnings("unlikely-arg-type")
class JsonObjectGeneralWrapper implements PrintableJsonObject {
	public static PrintableJsonObject wrapRaw(String raw) {
		return new JsonObjectGeneralWrapper(JsonValueGeneralWrapper.wrapRaw(raw));
	}

	public static PrintableJsonObject wrapPrettyPrinted(String prettyPrinted) {
		return new JsonObjectGeneralWrapper(JsonValueGeneralWrapper.wrapPrettyPrinted(prettyPrinted));
	}

	public static PrintableJsonObject wrapUnknown(String unknownForm) {
		return new JsonObjectGeneralWrapper(JsonValueGeneralWrapper.wrapUnknown(unknownForm));
	}

	public static PrintableJsonObject wrapDelegate(JsonObject delegate) {
		return new JsonObjectGeneralWrapper(JsonValueGeneralWrapper.wrapDelegate(delegate));
	}

	/**
	 * The string representation of this json object (not just a value!); and
	 * through it, a delegate to the real json object implementation.
	 */
	private PrintableJsonValue stringRepresentationDelegate;

	private JsonObjectGeneralWrapper(PrintableJsonValue stringRepresentationDelegate) {
		this.stringRepresentationDelegate = requireNonNull(stringRepresentationDelegate);
	}

	@Override
	public void clear() {
		getDelegate().clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return getDelegate().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return getDelegate().containsValue(value);
	}

	@Override
	public Set<Entry<String, JsonValue>> entrySet() {
		return getDelegate().entrySet();
	}

	@Override
	public boolean equals(Object obj) {
		return getDelegate().equals(obj);
	}

	@Override
	public JsonValue get(Object key) {
		return getDelegate().get(key);
	}

	@Override
	public boolean getBoolean(String name) {
		return getDelegate().getBoolean(name);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		return getDelegate().getBoolean(name, defaultValue);
	}

	@Override
	public int getInt(String name) {
		return getDelegate().getInt(name);
	}

	@Override
	public int getInt(String name, int defaultValue) {
		return getDelegate().getInt(name, defaultValue);
	}

	@Override
	public JsonArray getJsonArray(String name) {
		return getDelegate().getJsonArray(name);
	}

	@Override
	public JsonNumber getJsonNumber(String name) {
		return getDelegate().getJsonNumber(name);
	}

	@Override
	public JsonObject getJsonObject(String name) {
		return getDelegate().getJsonObject(name);
	}

	@Override
	public JsonString getJsonString(String name) {
		return getDelegate().getJsonString(name);
	}

	@Override
	public String getString(String name) {
		return getDelegate().getString(name);
	}

	@Override
	public String getString(String name, String defaultValue) {
		return getDelegate().getString(name, defaultValue);
	}

	@Override
	public ValueType getValueType() {
		return getDelegate().getValueType();
	}

	@Override
	public int hashCode() {
		return getDelegate().hashCode();
	}

	@Override
	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	@Override
	public boolean isNull(String name) {
		return getDelegate().isNull(name);
	}

	@Override
	public Set<String> keySet() {
		return getDelegate().keySet();
	}

	@Override
	public JsonValue put(String key, JsonValue value) {
		return getDelegate().put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends JsonValue> m) {
		getDelegate().putAll(m);
	}

	@Override
	public JsonValue remove(Object key) {
		return getDelegate().remove(key);
	}

	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public String toRawString() {
		return stringRepresentationDelegate.toRawString();
	}

	@Override
	public String toString() {
		return stringRepresentationDelegate.toString();
	}

	@Override
	public Collection<JsonValue> values() {
		return getDelegate().values();
	}

	private JsonObject getDelegate() {
		return stringRepresentationDelegate.asJsonObject();
	}

}
