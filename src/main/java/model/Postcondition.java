package model;

/**
 * Describes the changes made to a state.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface Postcondition {
    /**
     * The method makes changes to the state.
     * @param state The state to change
     */
    void activate(State state);
}
