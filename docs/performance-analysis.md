# Performance analysis

## 05.04

Scenario used: scenarioExplode  
Algorithm used: Heap algorithm  
Average time of: 10 runs (in a for loop, is a bit faster than separate executions)

scenarioExplode{n} and the results:

| n  | Average time (ms) |
|----|------------------:|
| 1  | 0.225             |
| 2  | 0.080             |
| 3  | 0.724             |
| 4  | 0.536             |
| 5  | 1.515             |
| 6  | 2.053             |
| 7  | 4.591             |
| 8  | 7.748             |
| 9  | 21.548            |
| 10 | 57.588            |
| 11 | 229.843           |
| 12 | 864.717           |
| 13 | 3236.963          |


![](img/heap-explode-plot.png?raw=true)

_The function in the graph is `f(x) = (x^(x/2.7))/80`_

**Discussion**: We can see that the algorithm performs in O(n^n) which might at
first glance seem extremely bad. But considering that the algorithm tries to
find all possible solutions to determine the one with the lowest cost, this
result is expected. They same result can be observed when using the
Naive algorithm.

Let's next look at the space Heap algorithm uses. Here is a graph of the maximum
amount of plans simultaneously being checked.

scenarioExplode{n} and the results:

| n  | Max plans | Theoretical max (n^n) | Percentage (%) |
|----|-----------|-----------------------|----------------|
| 1  | 1         | 1                     | 100            |
| 2  | 2         | 4                     | 50             |
| 3  | 3         | 27                    | 11.111         |
| 4  | 6         | 256                   | 2.344          |
| 5  | 10        | 3125                  | 0.32           |
| 6  | 20        | 46656                 | 0.043          |
| 7  | 35        | 823543                | 0.00425        |
| 8  | 70        | 16777216              | 0.000417       |
| 9  | 126       | 387420489             | 0.325E-6       |
| 10 | 252       | 10000000000           | 0.256E-7       |
| 11 | 462       | 285311670611          | 0.162E-8       |
| 12 | 924       | 8.916100558E12        | 0.104E-9       |
| 13 | 1716      | 3.028751066E14        | 0.057E-10      |

![](img/heap-explode-space-plot.png?raw=true)

_The function in the graph is `f(x) = (x^8)/500000`_

**Discussion**: We can see that the algorithm performs in O(n^k) in terms of space.
Since the "action graph" is extremely dense, it makes sense. The optimization of
throwing away duplicate plans that have the same result is huge, which can be seen
by the `Percentage` column

### Conclusion

The algorithms used thus far have been based on BFS, and the scenario above would not
be any problem for a DFS based approach. However, a pure DFS would also have it's
own problems. A balance must be struck.  
**The algorithm used should heavily depend on the problem at hand.**
