package io;

import io.requirements.Requirement;

/**
 * Data class for a JSON representation of a Requirement.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 * @see Requirement
 */
public class JSONRequirement {
    public String key;
    public int value;
    public String reqCode;
    public float weight = 1f;
}
