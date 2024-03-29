package io.github.oliviercailloux.json;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableMap;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Has at least one of delegate, prettyPrinted or raw form when created, initializes the rest as
 * needed.
 *
 * @author Olivier Cailloux
 *
 */
class JsonValueGeneralWrapper implements PrintableJsonValue {

  public static JsonValueGeneralWrapper wrapRaw(String raw) {
    return new JsonValueGeneralWrapper(Optional.empty(), Optional.of(raw), Optional.empty());
  }

  public static JsonValueGeneralWrapper wrapPrettyPrinted(String prettyPrinted) {
    return new JsonValueGeneralWrapper(Optional.of(prettyPrinted), Optional.empty(),
        Optional.empty());
  }

  public static JsonValueGeneralWrapper wrapUnknown(String unknownForm) {
    return new JsonValueGeneralWrapper(unknownForm);
  }

  public static JsonValueGeneralWrapper wrapDelegate(JsonValue delegate) {
    return new JsonValueGeneralWrapper(Optional.empty(), Optional.empty(), Optional.of(delegate));
  }

  private static JsonValue asJsonValue(String data) {
    final JsonValue json;
    try (JsonReader jr = Json.createReader(new StringReader(data))) {
      json = jr.readValue();
    }
    return json;
  }

  private static String asPrettyString(JsonValue json) {
    if (json == null) {
      return "null";
    }
    final StringWriter stringWriter = new StringWriter();
    final JsonWriterFactory writerFactory =
        Json.createWriterFactory(ImmutableMap.of(JsonGenerator.PRETTY_PRINTING, true));
    try (JsonWriter jsonWriter = writerFactory.createWriter(stringWriter)) {
      jsonWriter.write(json);
    }
    return stringWriter.toString();
  }

  private JsonValue delegate;

  private String prettyPrinted;

  private String raw;

  private JsonValueGeneralWrapper(Optional<String> prettyPrinted, Optional<String> raw,
      Optional<JsonValue> delegate) {
    assert prettyPrinted.isPresent() || raw.isPresent() || delegate.isPresent();

    if (prettyPrinted.isPresent()) {
      this.prettyPrinted = prettyPrinted.get();
      checkArgument(!this.prettyPrinted.startsWith("\n"));
    } else {
      this.prettyPrinted = null;
    }

    if (raw.isPresent()) {
      this.raw = raw.get();
      checkArgument(!this.raw.startsWith("\n"));
    } else {
      this.raw = null;
    }

    if (delegate.isPresent()) {
      this.delegate = delegate.get();
    } else {
      this.delegate = null;
    }
  }

  private JsonValueGeneralWrapper(String unknownForm) {
    this(Optional.empty(), Optional.empty(), Optional.of(asJsonValue(requireNonNull(unknownForm))));
  }

  @Override
  public JsonArray asJsonArray() {
    return getDelegate().asJsonArray();
  }

  @Override
  public JsonObject asJsonObject() {
    return getDelegate().asJsonObject();
  }

  @Override
  public boolean equals(Object obj) {
    return getDelegate().equals(obj);
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
  public String toRawString() {
    if (raw == null) {
      raw = getDelegate().toString();
    }

    return raw;
  }

  @Override
  public String toString() {
    if (prettyPrinted == null) {
      prettyPrinted = asPrettyString(getDelegate());
    }

    return prettyPrinted;
  }

  JsonValue getDelegate() {
    if (delegate == null) {
      if (raw != null) {
        delegate = asJsonValue(raw);
      } else {
        assert prettyPrinted != null;
        delegate = asJsonValue(prettyPrinted);
      }
    }

    return delegate;
  }
}
