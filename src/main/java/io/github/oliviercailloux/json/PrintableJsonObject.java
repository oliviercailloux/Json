package io.github.oliviercailloux.json;

import jakarta.json.JsonObject;

public interface PrintableJsonObject extends JsonObject, PrintableJsonValue {
  /** Just to combine these two interfaces. */
}
