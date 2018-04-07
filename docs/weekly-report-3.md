# Week 3

The hour report can be found in the same folder as this document.

## Progress

- Documentation for the model
- Step-by-step simulation
- Command-line interface to control the simulation
- Implement Heap-based sudo-A\* algorithm
- Implement traffic scenario with multiple options using cost heuristic
- Proper performance analysis
- Integer-based State's

I am now starting to have all the pieces ready for a demo.

## Problems

- Finding all possible solutions or plans is not a good approach, but it
is necessary for demo purposes. I will address this issue at a later stage.
- The need for a scenario generator is rising (for testing purposes), but
I am not sure if it would become too complex / difficult. After all, it is
not an essential part of the project.
- The change to use integer-based data for the scenario allows for a much
greater range of problems that the project could solve, but it also
increases the likelihood of bugs. I don't fully trust my test suite,
because the coverage percentage is misleading.

## Questions

- During development I noticed a few bugs, bugs that did not show in my tests. The problems
would have been discovered through integration tests. How important are they to use? I
think I can use JUnit + Mockito for integration testing. Opinions or other suggestions?
- Do you have any suggestions for scenario's that I could try and implement?
- Java's own collections seem to use `System.arraycopy`, am I allowed to use it?

## Next week

- Documentation (hopefully before the peer reviews)
    - Introduction to GOAP (README)
    - Getting Started (README)
    - Extending and creating your own algorithms
    - Is it worth to use the Wiki, will I have enough documentation? Will look into it.
- Testing
    - Improve test suite by thoroughly adding unit tests for edge cases
    - Possibly implement integration testing
- Implement resource gathering / crafting scenario (re-occuring actions)
- Design and implement own data structures
    - DynamicArray
    - HashTable: Will probably use chaining with arrays for collision resolution
    - Set: Probably much like the hash table
    - Heap (min)
    - Implement solution to replace `Collections.reverse`
- Think about projects that could benefit from using GOAPer, consider making a small game
that I could demo, keeping the command line as a backup demo.

The easter holiday allowed me to work a lot and add a ton of new features. Sadly, I will
have less time next week to work on the project due to another course taking precedence
over this one.

I feel like GOAPer is nearly done, meaning that I have 90% left :stuck_out_tongue_winking_eye:

> The first 90% of the project time is used for 90% of the work, and the remaining 90% of
> the project time is used for the remaining 10% or the work
