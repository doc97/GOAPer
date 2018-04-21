package datastructures;

/**
 * Custom interface to replace {@link Comparable}
 * Created by Daniel Riissanen on 21.4.2018.
 */
public interface IntComparable<T> {
    /**
     * Returns 1 if <code>'this' > other</code>, -1 otherwise.
     * @param other The object to compare with
     * @return 1, 0 or -1
     */
    int compare(T other);
}
