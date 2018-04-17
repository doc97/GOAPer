# What is GOAP?

> Goal-Oriented Action Planning (GOAP) is a decision-making architecture that
> takes the next step, and allows characters to decide not only what to do, but
> how to do it.

_- Jeff Orkin, [Applying Goal-Oriented Action Planning to Games][orkin]_

Most programmers are familiar with finite-state machines\[[1][fsm]] and
decision trees\[[2][dt]]\[[3][dtp]]. A decision tree is in its simplest form
just a bunch of if-statements. It is very intuitive and easy to get started
with. It comes with a few challenges though. The first challenge is handling
complexity growth. As a problem domain grows, more and more dependencies are
introduced and it is easy to create hairy and hard-to-follow logic. The second
challenge is dealing with the static nature of a decision tree. One of the most
important differences between GOAP and and decision trees is the ability to
react to external change. GOAP can dynamically alter the plan during runtime.

Before continuing, I urge you to read the article mentioned above, or head over
to the [documentation][docs-model] for a quick rundown on the terms used when
discussing GOAP.

### Design considerations

I followed the design described in the article quite closely, meaning that if
seen as a graph, the nodes would be the states and the edges would be actions.
The graph is a directed acyclic graph (DAG)\[[4][dag]], a tree which starts at
the root node. For the algorithms supplied with the project I have used
regressive search as suggested in the article. You are of course allowed to
create your own algorithm, in which case please refer to the
[documentation][docs-custom-algorithms].

The algorithms that the project contains at the moment are based on A\* and
the method described by Orkin. Since the graph is a DAG, it might be possible
to implement an algorithm that makes use of topological sort\[[5][topo]].

### How it works

From the root node, the planner will choose an action (edge) which best fills
the requirements of the goal. Any preconditions that the action may have will
be added to the overall requirements and the search for a new action continues
until all requirements have been met. Once all requirements have been met, a
valid sequence of actions, a plan, have been formed.

#### Sources

\[1\]: https://en.wikipedia.org/wiki/Finite-state_machine  
\[2\]: https://en.wikipedia.org/wiki/Decision_tree  
\[3\]: http://www.public.asu.edu/~kirkwood/DAStuff/decisiontrees/DecisionTreePrimer-1.pdf  
\[4\]: https://en.wikipedia.org/wiki/Directed_acyclic_graph  
\[5\]: http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/topoSort.htm  

[orkin]: http://alumni.media.mit.edu/%7Ejorkin/GOAP_draft_AIWisdom2_2003.pdf
[fsm]: https://en.wikipedia.org/wiki/Finite-state_machine
[dt]: https://en.wikipedia.org/wiki/Decision_tree
[dtp]: http://www.public.asu.edu/~kirkwood/DAStuff/decisiontrees/DecisionTreePrimer-1.pdf
[dag]: https://en.wikipedia.org/wiki/Directed_acyclic_graph
[topo]: http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/topoSort.htm
[docs-model]: docs/model.md
[docs-custom-algorithms]: docs/custom-algorithms.md
