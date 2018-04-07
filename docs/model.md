# GOAPer Model
The model is the underlying structure of the data that the problem will be expressed in. This document
explains the different terms and concepts in GOAPer.

## Components

### Actor
While not explicitly represented, the `Actor` represents the entity formulating a `Plan` using GOAP.

### Plan
The plan is a series of `Action`s that takes the `Actor` from the start `State` to the goal `State`. The
final cost of the plan is the sum of the costs of all the `Action`s.

### Scenario
A `Scenario` consists of a starting `State` and a goal `State` that the `Actor` wishes to achieve. It also
contains a complete set of actions that can be performed during the `Simulation`.

### State
A `State` is a set of key-value pairs describing the problem domain. Currently GOAPer supports
`String` to `Integer` pairs such as, `("steps", 3)` and `("lives", 10)`. Boolean values are supported, such
as, `("hasWeapon", 1)`. '1' represents `true` and everything else is `false`.

### Action
An `Action` represents something the `Actor` can _do_ in order to change the current `State`. An
`Action` consists of 3 parts, a `Precondition`, a `Postcondition` and a `cost`.

#### Precondition
To be able to perform the `Action`, it's `Precondition` must be met. The `Precondition` returns an `integer`
representing how much more is required for the condition to be met.

#### Postcondition
The `Postcondition` of an `Action` specifies the changes to the current `State` that the `Action` will make.

#### Cost
Each `Action` has a cost assigned to it. This can be used to choose between different plans of action or to
apply heuristics to the planning algorithm.

### Simulation
A `Simulation` is as the name suggest a simulation of a `Scenario`. After each step of the simulation the plan
must be verified or checked to ensure that the plan still works. In each simulation step the environment, or the
current `State`, can be altered through an `Event`. By verifying the plan each step, the `Actor` can dynamically
change it's plan during the course of the simulation.

#### Event
An `Event` is an "external" `Action` with no `Precondition` or `cost`. So essentially it is an effect, or a
`Postcondition`. The `Event` is used to simulate an external change to the `State` during a `Simulation` step.

## What is this used for?
The `State` and `Action` are the main building blocks for the planning algorithms. Every algorithm will return a
`Plan`, but how it does it, is up to the developer themselves. By providing an easy-to-use data model the
design of planning algorithms is simplified. You can for example extend the model to a graph representation
that a graph search algorithm can utilize. But you can also use `State` and `Action` directly.
