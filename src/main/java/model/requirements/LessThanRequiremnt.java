package model.requirements;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class LessThanRequiremnt implements Requirement {
    @Override
    public boolean check(int a, int b) {
        return a < b;
    }
}