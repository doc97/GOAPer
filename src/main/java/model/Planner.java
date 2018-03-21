package model;

import algorithms.PlanningAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Planner {
    public Plan execute(State start, State goal, Action[] actions, PlanningAlgorithm algorithm) {
        return algorithm.formulatePlan(start, goal, actions);
    }
}
