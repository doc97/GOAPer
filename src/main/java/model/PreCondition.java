package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface PreCondition {
    boolean checkState(State state);
}
