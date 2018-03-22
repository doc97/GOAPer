# Performance analysis

## 22.03

Scenario used: scenarioExplode
Algorithm used: Naive algorithm
Average of: 10 runs (in a for loop, is a bit faster than separate executions)

Iterations(n) and their results:

|  n  | Average time (ms) |
|-----|------------------:|
| 1   | 74.9              |
| 10  | 402.7             |
| 20  | 716.3             |
| 30  | 1065.4            |
| 40  | 1379.8            |
| 50  | 1696.3            |
| 100 | 3296.5            |

![](img/naive-explode-plot.img?raw=true)

_The linear function in the graph is y = x/2.2._

**Conclusion**: We can see that the algorithm performs in O(n).
