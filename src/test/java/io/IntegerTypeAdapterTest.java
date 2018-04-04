package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class IntegerTypeAdapterTest {

    @Test
    public void testDeserializeInt() {
        IntegerTypeAdapter testSubject = new IntegerTypeAdapter();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(int.class, testSubject);
        Gson gson = builder.create();

        MockJsonClass result = gson.fromJson("{ \"value\": 1 }", MockJsonClass.class);
        assertEquals(1, result.value);
    }

    @Test
    public void testDeserializeBooleanTrue() {
        IntegerTypeAdapter testSubject = new IntegerTypeAdapter();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(int.class, testSubject);
        Gson gson = builder.create();

        MockJsonClass result = gson.fromJson("{ \"value\": true }", MockJsonClass.class);
        assertEquals(1, result.value);
    }

    @Test
    public void testDeserializeBooleanFalse() {
        IntegerTypeAdapter testSubject = new IntegerTypeAdapter();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(int.class, testSubject);
        Gson gson = builder.create();

        MockJsonClass result = gson.fromJson("{ \"value\": false }", MockJsonClass.class);
        assertEquals(0, result.value);
    }

    @Test
    public void testDeserializeException() {
        IntegerTypeAdapter testSubject = new IntegerTypeAdapter();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(int.class, testSubject);
        Gson gson = builder.create();

        try {
            MockJsonClass result = gson.fromJson("{ \"value\": asdf }", MockJsonClass.class);
            fail("Handled invalid JSON even if it should not have");
        } catch (JsonParseException ignored) { }
    }

    private class MockJsonClass {
        int value;
    }
}
