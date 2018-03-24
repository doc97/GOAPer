# Week 2

The hour report can be found in the same folder as this document.

## Progress

- Scenario loading from json
- Fix naive algorithm
- Unit test coverage improved
- Documentation
- Algorithm design

I thought about if modeling the problem as a graph would over-complicate
things and possibly come with some overhead. I decided to leave it as it is now.

An optimized algorithm could use heuristics similar to A*. The current model
looks like this:
- Each "State" is a node in a graph
- Each "Action" is an edge in the graph
Picture the graph as a game tree with the width of each level depending on how
many actions there are. Edges that lead to a node that is less similar to the goal node
than the node the edge came from can be ignored.

The new algorithm will use a PriorityQueue the same way A* does to try and optimize.
This has the same benefits and drawbacks as A*, it does not actually improve the worst
case scenario.

## Problems

- I wondered if modeling the problem as a proper graph would provide any benefits,
apart from readability. I do not think it would benefit me at all. It might even come
with some overhead.
- I am concerned that the model might not scale well. The initial performance analysis
allowed me to fix a flaw, and it seems to be able to handle that scenario okay. Because
of the variety of scenarios that could be given, I am not certain that I have covered all
edge cases.

## Questions

- How should one properly test algorithms, their edge cases and correctness?
Currently I have to manually come up with scenarios and give them to the
algorithm and check the output. This approach seems error-prone and could
generate false positives.
- Is it enough to show a command-line representation of the algorithm, or is
a graphical demo required?

## Next week

- Document the model properly
- Step-by-step simulation to allow demonstration of algorithm. It also allows
for dynamically changing plan during the course of the simulation.
- Command-line interface to control the simulation
- Implement Heap-based sudo-A* algorithm.
- Implement traffic scenario with multiple options using cost heuristic.
