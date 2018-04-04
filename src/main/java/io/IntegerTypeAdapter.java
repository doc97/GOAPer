package io;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class IntegerTypeAdapter implements JsonDeserializer<Integer> {
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
