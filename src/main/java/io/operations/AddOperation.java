package io.operations;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class AddOperation implements Operation {
    @Override
    public int apply(int a, int b) {
        return a + b;
    }
}
