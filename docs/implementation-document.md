# Implementation

### Project structure
Below are the most important classes in each package.

#### `algorithms`
- HeapAlgorithm.java - Heap-based algorithm using advanced heuristics
- NaiveAlgorithm.java - BFS with simple heuristics

#### `datastructures`
All custom data structures are generic.

- DynamicArray.java - Custom `ArrayList`
- HashSet.java - Custom `HashSet`
- HashTable.java - Custom `HashMap`
- MinHeap.java - Custom `PriorityQueue`

#### `io`
- `io.operators` - Custom operators such as '=' and '+' used in scenarios
- `io.requirements` - Custom operators such as '<' and '>=' used in scenarios
- JSONLoader.java - The class that loads a scenario from JSON
- JSONConverter.java - The class making the heavy-lifting in terms of JSON parsing

#### `model`
- `model.simulation` - Simulation related classes, bridge between `model` and `ui` packages
- Action.java - See other documentation
- Goal.java - See other documentation
- Plan.java - See other documentation
- State.java - See other documentation

#### `ui`
- Console.java - The console interface

### Time complexity analysis
HeapAlgorithm.java
```Java
while (!plans.isEmpty()) {
    SubPlan current = plans.poll();

    if (utilities.isValidSubPlan(current, start)) {
        // ...
        return returnPlans;
    }

    for (Action action : actions) {
        if (utilities.isGoodAction(current, action)) {
            SubPlan newSubPlan = utilities.getNextSubPlan(current, action);
            if (utilities.isUniqueSubPlan(newSubPlan, plansToAdd)) {
                plans.add(newSubPlan);
            }
        }
    }

    plans = trimPlans(plans, 1000);
} 
```
The time complexity should be calculated with multiple variables in mind. One one hand we have the action count (**a**), but we also have the final plan depth (**d**). Intuitively the worst case scenario we will be O(**a**^**d**). But because of optimizations and heuristics such as `isGoodAction` and `isUniqueSubPlan` this can be for the most parts be avoided.

Of course if the wrong algorithm and utilities is applied to a problem, we could be looking at the worst case mentioned above.

`trimPlans(plans, 1000)` is yet another optimization. This ensures that no more than 1000 plans can be evaluated at once.

One of the code reviews mentioned "dp" which I can only assume refers to the [Davis-Putnam algorithm](https://en.wikipedia.org/wiki/Davis%E2%80%93Putnam_algorithm). At this point I have not taken a look at it, but in the future it might be worth a try.

**Note**: If we instead use the number of vertices in the graph as input when calculating time complexity, the algorithm performs in O(n). Each vertex represents a new permutation of a sequence of actions.

### Missing features
- The simulation part of the project is a bit lacking
- The console interface is very crude and could be improved
- An actual example that could clarify the use of this project

