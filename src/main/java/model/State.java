package model;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class State {

    private HashMap<String, Boolean> keys;
    private int cost;

    public State(State state) {
        if (state != null) {
            this.keys = new HashMap<>(state.keys);
            this.cost = state.cost;
        } else {
            this.keys = new HashMap<>();
            this.cost = 0;
        }
    }

    public State(HashMap<String, Boolean> keys, int cost) {
        this.keys = keys;
        this.cost = cost;
    }

    public State() {
        keys = new HashMap<>();
    }

    public void addCost(int value) {
        cost += value;
    }

    public void addKey(String key, boolean value) {
        if (key != null)
            keys.putIfAbsent(key, value);
    }

    public boolean query(String key) {
        return keys.containsKey(key) && keys.get(key);
    }

    public void apply(String key, boolean value) {
        if (key != null)
            keys.put(key, value);
    }

    public void update(String key, boolean value) {
        if (keys.containsKey(key)) {
            keys.put(key, value);
        }
    }

    public int getCost() {
        return cost;
    }

    public Set<String> getKeys() {
        return keys.keySet();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys.keySet()) {
            stringBuilder.append(key).append(": ").append(keys.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof State))
            return false;
        State other = (State) o;

        if (this.cost != other.cost)
            return false;
        if (this.keys.keySet().size() != other.keys.keySet().size())
            return false;

        for (String key : this.keys.keySet()) {
            if (this.keys.get(key) != other.keys.get(key))
                return false;
        }
        return true;
    }
}
