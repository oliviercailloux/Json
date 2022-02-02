package io.github.oliviercailloux.json;

import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import java.lang.reflect.Type;
import java.util.function.Function;

public class JsonbUtils {
  private static JsonbBuilder jsonbDefaultBuilder = null;

  public static Jsonb getDefaultJsonb() {
    if (jsonbDefaultBuilder == null) {
      jsonbDefaultBuilder =
          JsonbBuilder.newBuilder().withConfig(new JsonbConfig().withFormatting(true));
    }
    return jsonbDefaultBuilder.build();
  }

  public static <T> T fromJson(String source, Type type,
      @SuppressWarnings("rawtypes") JsonbAdapter... adapters) {
    final T asObj;
    try (Jsonb jsonb = getInstance(adapters)) {
      asObj = jsonb.fromJson(source, type);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return asObj;
  }

  public static PrintableJsonValue toJsonValue(Object source,
      @SuppressWarnings("rawtypes") JsonbAdapter... adapters) {
    final String asStr = toPrettyPrinted(source, adapters);
    return PrintableJsonValueFactory.wrapPrettyPrintedString(asStr.substring(1));
  }

  public static PrintableJsonObject toJsonObject(Object source,
      @SuppressWarnings("rawtypes") JsonbAdapter... adapters) {
    final String asStr = toPrettyPrinted(source, adapters);
    return PrintableJsonObjectFactory.wrapPrettyPrintedString(asStr.substring(1));
  }

  private static Jsonb getInstance(@SuppressWarnings("rawtypes") JsonbAdapter... adapters) {
    if (adapters.length == 0) {
      return getDefaultJsonb();
    }
    return JsonbBuilder.create(new JsonbConfig().withAdapters(adapters).withFormatting(true));
  }

  private static String toPrettyPrinted(Object source,
      @SuppressWarnings("rawtypes") JsonbAdapter... adapters) {
    final String asStr;
    try (Jsonb jsonb = getInstance(adapters)) {
      asStr = jsonb.toJson(source);
      assert asStr.startsWith("\n");
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return asStr;
  }

  /** TODO doesn’t work because type inference fails, it seems. Bug or feature? */
  public static <T> JsonbAdapter<T, JsonObject> getAdapter(Function<JsonObject, T> asOriginalFct,
      Function<T, JsonObject> asJsonFct) {
    return new JsonbAdapter<T, JsonObject>() {
      @Override
      public JsonObject adaptToJson(T obj) throws Exception {
        return asJsonFct.apply(obj);
      }

      @Override
      public T adaptFromJson(JsonObject obj) throws Exception {
        return asOriginalFct.apply(obj);
      }
    };
  }
}
