package io.github.oliviercailloux.json;

import jakarta.json.JsonObject;

public class PrintableJsonObjectFactory {

  public static PrintableJsonObject wrapRawString(String raw) {
    return JsonObjectGeneralWrapper.wrapRaw(raw);
  }

  public static PrintableJsonObject wrapPrettyPrintedString(String prettyPrinted) {
    return JsonObjectGeneralWrapper.wrapPrettyPrinted(prettyPrinted);
  }

  public static PrintableJsonObject wrapString(String unknownForm) {
    return JsonObjectGeneralWrapper.wrapUnknown(unknownForm);
  }

  public static PrintableJsonObject wrapObject(JsonObject content) {
    if (content instanceof PrintableJsonObject) {
      return (PrintableJsonObject) content;
    }
    return JsonObjectGeneralWrapper.wrapDelegate(content);
  }

  private PrintableJsonObjectFactory() {
    /* Can’t be instantiated. */
  }
}
