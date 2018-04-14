# Week 4

The hour report can be found in the same folder as this document.

## Progress

- Custom ArrayList (DynamicArray)
- Custom HashMap (HashTable)
- Custom Set (HashSet)

## Problems

## Questions

- Continuation from last week: _Java's own collections seem to use `System.arraycopy`, am I allowed to use it?_
    - `System.arraycopy` is a native method meaning that it will be faster than any custom-made one in Java.
    It is indeed trivial to implement, as a matter of fact I had a solution without it, but my IDE (IntelliJ IDEA)
    suggested to replace it with `System.arraycopy`.
    - Because it is trivial I don't see any point in making my own (would not give much points I assume anyway).

## Next week

I planned optimistically last time so many of the things are still the same from last week:

- Documentation (hopefully before the peer reviews)
    - Introduction to GOAP (README)
    - Getting Started (README)
    - Extending and creating your own algorithms
    - How to model a problem (Example)
    - JavaDoc comments (currently very sparse)
- Testing
    - Improve test suite by thoroughly adding unit tests for edge cases
- Design and implement own data structures
    - Heap (min)
    - Implement solution to replace `Collections.reverse`
- Implement resource gathering / crafting scenario (re-occuring actions)
