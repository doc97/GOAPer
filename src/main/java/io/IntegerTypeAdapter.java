package io;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Custom integer JsonDeserializer.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class IntegerTypeAdapter implements JsonDeserializer<Integer> {
    /**
     * Adds support for interpreting <code>true</code> and <code>false</code> as 1 and 0.
     * @param json The JSON element
     * @param typeOfT The type of the element.
     * @param context The deserialization context
     * @return The integer representation of the element
     * @throws JsonParseException If the element can't be parsed as an integer
     */
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String code = json.getAsString();
        try {
            if (code.equals("true"))
                return 1;
            else if (code.equals("false"))
                return 0;
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            throw new JsonParseException(json.getAsString() + " cannot be parsed as an integer");
        }
    }
}
