# Snake and Ladder: Low-Level Design

## Part 2 · Entity Identification and Responsibility Assignment

*From requirements to a clear class inventory, before a single line of Java is written.*

---

## Contents

1. [Introduction](#1-introduction)
2. [Extracting Entities from Requirements](#2-extracting-entities-from-requirements)
3. [Player](#3-player)
4. [Dice](#4-dice)
5. [Snake](#5-snake)
6. [Ladder](#6-ladder)
7. [Board](#7-board)
8. [Game](#8-game)
9. [Client](#9-client)
10. [Why There Is No Cell Class](#10-why-there-is-no-cell-class)
11. [Why There Is No TurnManager Class](#11-why-there-is-no-turnmanager-class)
12. [Final Class Responsibilities](#12-final-class-responsibilities)
13. [Object Interaction: An Example Turn](#13-object-interaction-an-example-turn)
14. [Design Principles Applied](#14-design-principles-applied)
15. [Decisions Finalized](#15-decisions-finalized)
16. [Next Step: UML Class Diagram](#16-next-step-uml-class-diagram)
17. [Key Takeaways](#17-key-takeaways)

---

## 1. Introduction

With the requirements finalized in `REQUIREMENT.md`, the next step is to identify the entities, classes, and responsibilities needed to build the game.

The goal is **not** to turn every noun in the requirements into a Java class. The goal is to find meaningful abstractions, give each one a clear job, and decide how the objects work together.

### Guiding principles

- Identify the important concepts in the problem.
- Assign clear responsibilities to each class.
- Avoid unnecessary classes and abstractions.
- Separate game orchestration from board configuration and player state.
- Make the game easy to demonstrate with predictable dice outcomes.
- Stay flexible enough for reasonable future extensions.

> **Note:** This document records the design decisions made *before* implementation. The UML class diagram and the Java code come in later steps.

---

## 2. Extracting Entities from Requirements

We begin by reading the requirements and listing the concepts they mention.

| Requirement | Candidate entity or concept |
|---|---|
| Multiple players participate | `Player` |
| The game has 100 cells | `Board` |
| Players roll a dice | `Dice` |
| Snakes have heads and tails | `Snake` |
| Ladders have bottoms and tops | `Ladder` |
| Players take turns | `Game` |
| Players move across the board | `Player`, `Game` |
| Snakes and ladders change landing positions | `Board` |
| The first player to reach cell 100 wins | `Game` |
| The game must be demonstrated | `Client` |
| Dice outcomes should be predictable in demos | `Dice`, `StandardDice`, `FixedDice` |

### 2.1 A noun is not automatically a class

Take the word **Cell**. A board has 100 cells, so a `Cell` class looks tempting. But a noun in the requirements does not, by itself, justify a class.

For every proposed entity we ask:

1. Does it have independent state?
2. Does it have meaningful behavior?
3. Does it carry responsibilities that should be kept separate?
4. Would a separate class make the design clearer or easier to maintain?
5. Can the requirement be met more simply without it?

We apply this test to every entity below.

---

## 3. Player

### Responsibility

`Player` represents one participant. It holds the player's identity and current position on the board.

### Attributes

| Attribute | Purpose |
|---|---|
| `id` | Uniquely identifies the player |
| `name` | The player's display name |
| `position` | The player's current board position |

Every player starts at position `0`, the point before cell 1.

### Behavior

A player exposes just enough to maintain and share its state:

- Get the identifier.
- Get the name.
- Get the current position.
- Update the position when a move is processed.

### Design decision

The player **owns its position but does not run the game.** These do not belong in `Player`:

- Rolling the dice
- Choosing the next player
- Searching for snakes and ladders
- Deciding whether the game has ended
- Declaring the winner

> **Decision:** `Player` represents player state. `Game` coordinates player actions.

---

## 4. Dice

### Responsibility

The dice decides how many cells a player moves in a turn. The initial version uses one six-sided dice, and every valid result is an integer from **1 to 6**, inclusive.

### Why separate dice from game logic?

Say a player is on cell 10 and the dice produces 4, so the player moves to cell 14. The game does not need to know *how* the 4 was produced.

- The **dice** is responsible for producing a result.
- The **game** is responsible for using it.

This keeps the game rules independent of any particular random-number generator.

### Proposed design

We introduce a `Dice` interface with two implementations:

| Implementation | Responsibility |
|---|---|
| `StandardDice` | Produces random results between 1 and 6 |
| `FixedDice` | Produces predetermined results for demonstrations |

```
Game  ──►  Dice (interface)  ◄──  StandardDice
                              ◄──  FixedDice
```

The game depends on the abstraction, not on a concrete dice.

### Why have `FixedDice`?

Suppose we want to show a player landing on a snake. With a random dice we cannot guarantee that sequence will occur. With a fixed dice we can script it.

| Turn | Player | Dice result | Expected movement |
|---|---|---|---|
| 1 | Player A | 3 | 0 → 3 |
| 2 | Player B | 2 | 0 → 2 |
| 3 | Player A | 4 | 3 → 7 |

The same approach can demonstrate:

- Landing on a snake
- Climbing a ladder
- Overshooting cell 100
- Winning the game

This is especially handy when demonstrating the project to an interviewer.

### Design principle

This is programming to an abstraction, with dependency inversion at work. The interface is not here for its own sake: we have a concrete need for interchangeable dice behavior, and it makes the game easy to test and demonstrate without depending on a random sequence.

> **Decision:** Use `Dice`, `StandardDice`, and `FixedDice`.

---

## 5. Snake

### Responsibility

`Snake` represents one snake, defined by two endpoints.

| Attribute | Meaning |
|---|---|
| `head` | The cell where the snake begins |
| `tail` | The cell where the snake ends |

Example: **head = 45, tail = 12.** A player landing on cell 45 slides down to cell 12.

### Validation

- Both endpoints are between 1 and 100.
- The head is greater than the tail.
- A snake never moves a player forward.

### Responsibility boundaries

`Snake` only describes a snake's configuration. It does **not**:

- Select the current player
- Roll the dice
- Advance turns
- Determine the winner
- Control the game

The board uses the snake's configuration to resolve a landing position.

> **Decision:** `Snake` stores the head and tail. `Board` manages its role in board navigation.

---

## 6. Ladder

### Responsibility

`Ladder` represents one ladder, defined by two endpoints.

| Attribute | Meaning |
|---|---|
| `bottom` | The cell where the ladder begins |
| `top` | The cell where the ladder ends |

Example: **bottom = 8, top = 31.** A player landing on cell 8 climbs to cell 31.

### Validation

- Both endpoints are between 1 and 100.
- The top is greater than the bottom.
- A ladder never moves a player backward.

### Responsibility boundaries

`Ladder` only describes a ladder's configuration. It does not manage turns, roll dice, or decide who wins. The board uses it when resolving a landing position.

> **Decision:** `Ladder` stores the bottom and top. `Board` manages its role in board navigation.

---

## 7. Board

### Responsibility

`Board` represents the game board and its configuration. It knows the board size (100 cells for now) and where the snakes and ladders are.

### Potential attributes

| Attribute | Purpose |
|---|---|
| `size` | Total number of cells |
| Snake configuration | Where the snakes are |
| Ladder configuration | Where the ladders are |

The exact Java data structures are settled during implementation.

### Potential behavior

- Validate its configuration.
- Maintain snake positions.
- Maintain ladder positions.
- Tell whether a cell holds a snake's head or a ladder's bottom.
- Resolve a landing position.

### Resolving a landing position

A player on cell 41 rolls a 4. The proposed landing is `41 + 4 = 45`. A snake runs from cell 45 to cell 12.

1. `Game` calculates the proposed landing position.
2. `Board` checks its configuration at cell 45.
3. `Board` finds the snake.
4. The destination resolves to cell 12.
5. `Game` updates the player to the resolved destination.

The board never needs to know *which* player is moving. It needs only a landing position and its own configuration.

### Representing snakes and ladders

One option is a single map:

```java
Map<Integer, Integer> jumps;
```

| Start | Destination | Meaning |
|---|---|---|
| 45 | 12 | Snake |
| 8 | 31 | Ladder |

This gives fast destination lookup. The catch is that one map does not record whether a jump is a snake or a ladder. If the game wants to show that difference in its output, we could use separate maps or another representation. The final choice is made in the board-design stage.

### Should the board know about players?

**No.** The board does not own the players or manage turns. It represents configuration and resolves positions, which keeps it independent of gameplay orchestration.

> **Decision:** `Board` manages snakes, ladders, configuration validation, and landing-position resolution.

---

## 8. Game

### Responsibility

`Game` coordinates the whole play-through. It executes turns, applies the rules, and decides when the game ends.

### Potential attributes

| Attribute | Purpose |
|---|---|
| `board` | Board configuration and position resolution |
| `players` | The participating players |
| `dice` | The source of dice results |
| Current-player index | Whose turn it is |
| Game status | Whether the game is active or finished |

How game status is represented is decided during implementation.

### Potential behavior

- Start the game.
- Execute a turn.
- Roll the dice through the injected `Dice` abstraction.
- Calculate the proposed movement.
- Enforce the cell 100 boundary.
- Resolve snakes and ladders through the board.
- Update the player's position.
- Check whether a player has won.
- Advance to the next player.
- Stop the game when a winner is found.

### Why does `Game` coordinate?

A turn needs several steps in the right order:

1. Select the current player.
2. Roll the dice.
3. Calculate the proposed position.
4. Check the winning-cell boundary.
5. Resolve the landing position.
6. Update the player's state.
7. Check the winning condition.
8. Advance to the next player if the game continues.

No single entity should have to coordinate that whole workflow. `Game` ties the steps together while handing each piece of work to the right object.

### What `Game` should not do

`Game` must not duplicate what other classes already own. It should not:

- Generate random numbers directly.
- Keep a second copy of the board's snake and ladder configuration.
- Hold player identity and position as separate game-level fields.
- Force players to understand the board's internal representation.

> **Decision:** `Game` orchestrates turns and enforces the rules, collaborating with `Player`, `Dice`, and `Board`.

---

## 9. Client

### Responsibility

`Client` is the entry point for demonstrating the game. It builds the required objects and starts play.

### Expected workflow

1. Create the players.
2. Configure the snakes and ladders.
3. Create the board.
4. Create the dice implementation.
5. Create the game.
6. Start the game.

Use `StandardDice` for a random game or `FixedDice` for a predictable demo.

### Responsibility boundaries

The client must not implement game rules. It does not contain logic for:

- Moving players
- Resolving snakes and ladders
- Checking whether the game has ended
- Selecting the winner

Those belong to the game and its collaborators.

> **Decision:** `Client` initializes and runs the game without implementing gameplay rules.

---

## 10. Why There Is No `Cell` Class

One design models every cell as an object: Cell 1, Cell 2, Cell 3, … Cell 100. Each would store its number and any snake or ladder attached to it.

But our requirements give cells no meaningful independent behavior. The board can identify a position with a plain integer and check whether a snake or ladder starts there. A hundred `Cell` objects would add structure with no clear payoff.

> **Decision:** No separate `Cell` class in the initial implementation.

We can revisit this if future requirements add cell-level behavior, such as special cells, cell-specific rules, or richer board interactions.

> **Principle:** A class should represent a meaningful concept or responsibility, not merely a noun found in the requirements.

---

## 11. Why There Is No `TurnManager` Class

Another option is a `TurnManager` that would:

- Track the current player.
- Determine the next player.
- Manage player order.

Our turn rules are simple. `Game` can hold the current-player index and advance it after each turn. A separate class would add complexity without solving any current requirement.

> **Decision:** Turn management stays inside `Game` for the initial implementation.

If turn mechanics ever become complex, we can reconsider the split.

---

## 12. Final Class Responsibilities

| Class / Interface | Responsibility |
|---|---|
| `Player` | Maintain player identity and current position |
| `Dice` | Define the contract for rolling a dice |
| `StandardDice` | Generate random dice results between 1 and 6 |
| `FixedDice` | Supply predetermined dice results |
| `Snake` | Represent a snake's head and tail |
| `Ladder` | Represent a ladder's bottom and top |
| `Board` | Maintain board configuration and resolve landing positions |
| `Game` | Coordinate turns, enforce rules, and determine the winner |
| `Client` | Initialize and demonstrate the game |

This is the initial class inventory. Exact attributes, method signatures, relationships, and dependencies are settled in the UML class diagram.

---

## 13. Object Interaction: An Example Turn

**Scenario**

- Board size: 100
- Player A's position: 41
- Dice result: 4
- Snake: head at 45, tail at 12

| Step | What happens |
|---|---|
| 1. Select the current player | `Game` identifies Player A. |
| 2. Roll the dice | `Game` requests a result; `Dice` returns **4**. |
| 3. Calculate the proposed position | `41 + 4 = 45` |
| 4. Validate the boundary | 45 is within the board, so the move proceeds. |
| 5. Resolve the landing position | `Game` asks `Board` to resolve cell 45. `Board` finds the snake from 45 to 12 and returns **12**. |
| 6. Update the player | `Game` sets Player A's position to 12. |
| 7. Check the winning condition | Player A has not reached cell 100. |
| 8. Advance the turn | `Game` moves on to the next player. |

### Summary

```
Client ──► Game ──► Dice
             │
             ├────► Board
             │
             └────► Player
```

`Game` coordinates the workflow, `Dice` provides the roll, `Board` resolves the position, and `Player` keeps its own state.

---

## 14. Design Principles Applied

| Principle | How it shows up here |
|---|---|
| **Single Responsibility** | `Dice` generates a result, `Board` resolves landing positions, `Player` maintains player state, `Game` coordinates gameplay. |
| **Encapsulation** | Player state and board configuration are changed through appropriate methods, not by unrelated objects. Access modifiers are decided during implementation. |
| **Separation of Concerns** | Game orchestration, dice generation, board configuration, and player state are kept apart. |
| **Low Coupling** | `Game` depends on the `Dice` abstraction, so dice behavior can change without rewriting the rules. |
| **High Cohesion** | Each class groups the data and behavior that naturally belong together. |
| **Testability** | `FixedDice` supplies predictable sequences for snakes, ladders, overshooting, and winning. |
| **Avoiding Overengineering** | No `Cell` class, no `TurnManager`, no needless design patterns, no database, no GUI. |

The goal is a clear, maintainable implementation that satisfies the agreed requirements.

---

## 15. Decisions Finalized

- [x] Use a `Player` class to maintain player state.
- [x] Use a `Dice` interface.
- [x] Implement `StandardDice` for random results.
- [x] Implement `FixedDice` for predictable demonstrations.
- [x] Use separate `Snake` and `Ladder` classes.
- [x] Use a `Board` class to maintain board configuration.
- [x] Let `Board` resolve snake and ladder effects.
- [x] Let `Game` coordinate turns and enforce the rules.
- [x] Let `Client` initialize and demonstrate the game.
- [x] Do not introduce a `Cell` class initially.
- [x] Do not introduce a separate `TurnManager` initially.
- [x] Keep the implementation simple and avoid unnecessary abstractions.

---

## 16. Next Step: UML Class Diagram

With responsibilities settled, we translate the design into a UML class diagram. It will define:

- Class attributes
- Method signatures
- Public and private members
- Interfaces and implementations
- Associations and dependencies
- Multiplicities between objects
- The relationships among `Game`, `Board`, `Player`, `Dice`, `Snake`, and `Ladder`

We review those relationships before writing any Java. The diagram then serves as the design reference for the remaining implementation stages.

---

## 17. Key Takeaways

1. Identifying entities is not the same as creating a class for every noun.
2. Every class should have a clearly defined responsibility.
3. `Game` coordinates gameplay but delegates specialized work to collaborating objects.
4. `Board` owns the snake and ladder configuration and resolves landing positions.
5. `Player` maintains its identity and current position.
6. The `Dice` abstraction allows both random and predetermined outcomes.
7. `Client` initializes the game but does not implement its rules.
8. Avoiding unnecessary classes keeps a design simple without hurting maintainability.
9. Design decisions should be driven by requirements, not by a wish to use particular design patterns.
10. A clear responsibility model makes the UML diagram and the Java implementation easier to develop.