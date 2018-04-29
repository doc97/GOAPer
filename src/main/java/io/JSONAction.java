package io;

import model.Action;

/**
 * Data class for a JSON representation of an Action.
 * <p/>
 * Created by Daniel Riissanen on 17.3.2018.
 * @see Action
 */
public class JSONAction {
    public String name = "";
    public int cost = 0;
    public JSONRequirement[] precondition = new JSONRequirement[0];
    public JSONOperator[] postcondition = new JSONOperator[0];
    public JSONOperator[] consumption = new JSONOperator[0];
}
