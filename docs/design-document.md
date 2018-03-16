# Design document

This program solves AI action problems by using graph search algorithms.
It essentially means that it can dynamically alter it's plan, in runtime
in reaction to changes to the environment.
Different graph search algorithms may be used to benchmark performance.

## Algorithms

Since the program essentially is a graph search, algorithms such as A\* and
Dijkstra will be implemented. Other algorithms will be implemented if time
allows it.
If possible it would be nice to be able to inject a user's own algorithm and
allow the user to test their own algorithms.

## Time and space complexities

Performance will be dependent on the type of problem that is given to the
algorithm and which algorithm is employed. Time complexities will
be ranging from O(n) to O(n^2). Space should be kept to O(n).

## Input

The algorithm will need information about the "world". A State consists of
data about the environment, in the form of booleans. As input the algorithm
will take:
- BeginState: The state in the beginning
- Actions: A collection of actions that can effect the State
- EndState: The state the algorithm tries to achieve

## Sources

Jeff Orkin - [Applying Goal Oriented Action Planning to Games](http://alumni.media.mit.edu/~jorkin/GOAP_draft_AIWisdom2_2003.pdf)

