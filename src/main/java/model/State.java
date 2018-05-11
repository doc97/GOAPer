package model;

import datastructures.HashSet;
import datastructures.HashTable;

/**
 * A state is a collection of custom integer variables.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class State {

    /** The variables, called keys */
    private HashTable<String, Integer> keys;

    /**
     * Class copy constructor.
     * @param state The state to copy
     */
    public State(State state) {
        this.keys = new HashTable<>();
        if (state != null) {
            String[] strKeys = new String[state.getKeys().count()];
            state.getKeys().asArray(strKeys);
            for (String key : strKeys)
                addKey(key, state.query(key));
        }
    }

    /**
     * Class constructor specifying the keys.
     * @param keys The keys
     */
    public State(HashTable<String, Integer> keys) {
        this.keys = keys;
    }

    /**
     * Class constructor.
     */
    public State() {
        keys = new HashTable<>();
    }

    /**
     * Adds a key-value mapping, will NOT overwrite an existing mapping.
     * @param key The key
     * @param value The mapping
     */
    public void addKey(String key, int value) {
        if (key != null)
            keys.putIfAbsent(key, value);
    }

    /**
     * Queries the value of a key.
     * @param key The key
     * @return The value if the key exists or the default value of 0
     */
    public int query(String key) {
        return keys.getOrDefault(key, 0);
    }

    /**
     * Query the value of a key as a boolean.
     * @param key The key
     * @return <code>true</code> if the value is 1, otherwise <code>false</code>
     */
    public boolean queryBoolean(String key) {
        return keys.containsKey(key) && keys.get(key) == 1;
    }

    /**
     * Adds a key-value mapping, will OVERWRITE an existing mapping.
     * @param key The key
     * @param value The value
     */
    public void apply(String key, int value) {
        if (key != null)
            keys.put(key, value);
    }

    /**
     * Updates a key-value mapping, does nothing if the key does not exist.
     * @param key The key
     * @param value The value
     */
    public void update(String key, int value) {
        if (keys.containsKey(key)) {
            keys.put(key, value);
        }
    }

    /**
     * Returns a set containing the keys.
     * @return The set of keys
     */
    public HashSet<String> getKeys() {
        return keys.keys();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strKeys = new String[keys.keys().count()];
        keys.keys().asArray(strKeys);
        for (String key : strKeys)
            stringBuilder.append(key).append(": ").append(keys.get(key)).append("\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State))
            return false;
        State other = (State) o;

        if (this.keys.keys().count() != other.keys.keys().count())
            return false;

        String[] strKeys = new String[keys.keys().count()];
        keys.keys().asArray(strKeys);
        for (String key : strKeys) {
            if (!this.keys.get(key).equals(other.keys.get(key)))
                return false;
        }
        return true;
    }
}
