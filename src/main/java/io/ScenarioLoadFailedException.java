package io;

/**
 * Custom exception thrown when the loading of a scenario fails.
 * <p/>
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class ScenarioLoadFailedException extends Exception {

    /**
     * Class constructor specifying a message
     * @param message The exception message
     */
    public ScenarioLoadFailedException(String message) {
        super(message);
    }
}
