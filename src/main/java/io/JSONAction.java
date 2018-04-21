package io;

import model.Action;

/**
 * Data class for a JSON representation of an Action.
 * <p/>
 * Created by Daniel Riissanen on 17.3.2018.
 * @see Action
 */
public class JSONAction {
    public String name;
    public int cost;
    public JSONRequirement[] precondition;
    public JSONOperator[] postcondition;
}
