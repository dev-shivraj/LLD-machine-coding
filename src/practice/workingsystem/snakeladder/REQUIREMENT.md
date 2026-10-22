# Snake and Ladder

*Requirements Specification for a Java Low-Level Design Project*

---

## 1. Overview

This project designs and implements a playable **Snake and Ladder** game in Java, guided by Low-Level Design (LLD) principles.

The emphasis is on:

- Clear class responsibilities
- Maintainable, readable code
- Object-oriented design
- Separation of concerns

The game is played through a **command-line interface (CLI)**. A client class demonstrates a complete game from setup to the announcement of the winner.

---

## 2. Game Rules

1. The board has **100 cells**, numbered 1 to 100.
2. The game supports a configurable number of players, with a **minimum of two**.
3. Every player starts at **position 0**, just before the first cell.
4. Players take turns rolling a **six-sided dice**.
5. A player moves forward by the number rolled.
6. Landing on a **snake's head** moves the player down to the snake's tail.
7. Landing on the **bottom of a ladder** moves the player up to its top.
8. A player wins by landing **exactly on cell 100**.
9. If a roll would take a player past cell 100, the player **stays where they are**.
10. The game ends **immediately** when the first player wins.
11. The game displays every relevant event: dice rolls, movements, snake encounters, ladder climbs, and the winner.

---

## 3. Functional Requirements

### FR-01 · Player Management

- Multiple players must be supported, with at least two participating.
- Each player has a name or identifier.
- Every player starts at position 0.
- Each player's current position is trackable.

### FR-02 · Board Management

- The board contains 100 cells, numbered 1 to 100.
- The board supports snakes and ladders.
- Snake and ladder positions are configurable at board initialization.
- The configuration is validated before the game begins.

### FR-03 · Dice Rolling

- The game uses one six-sided dice.
- Each roll produces an integer from 1 to 6, inclusive.
- The dice implementation is replaceable, so predictable outcomes can be supplied for demonstrations.

### FR-04 · Player Turns

- Players take turns in a defined order.
- Each active player's turn includes a dice roll.
- The player moves by the result, subject to the winning-cell boundary.
- Snake and ladder effects are applied after a valid move.
- The next turn begins only after the current turn has been fully processed.
- No turns occur after the game ends.

### FR-05 · Snake Behavior

- Each snake has a **head** (start) and a **tail** (end).
- The head sits on a higher-numbered cell than the tail.
- Landing on a head moves the player to the tail.
- A snake never moves a player forward.

### FR-06 · Ladder Behavior

- Each ladder has a **bottom** (start) and a **top** (end).
- The top sits on a higher-numbered cell than the bottom.
- Landing on a bottom moves the player to the top.
- A ladder never moves a player backward.

### FR-07 · Player Movement

- A player moves forward by the dice result when the new position does not exceed 100.
- If the new position would exceed 100, the position is unchanged.
- Snake and ladder effects apply after a valid landing.
- The player's final position after each turn is recorded correctly.

### FR-08 · Winning Condition

- A player wins only by reaching cell 100 exactly.
- Passing cell 100 is not a win.
- The first player to reach cell 100 is declared the winner.
- The game terminates immediately once a winner is determined.

### FR-09 · Game Execution and Output

- A client class initializes the players, board, snakes, ladders, dice, and game.
- The game runs as a CLI-style demonstration.
- Output shows each turn and dice result.
- Output shows each movement and any snake or ladder effect.
- Output announces the winner.
- No graphical user interface is required.

---

## 4. Initial Design Scope

The concepts below are design candidates, not a finalized class diagram. Their exact responsibilities and relationships are settled during the design phase, and additional classes or interfaces may be added where they improve the design.

| Concept | Responsibility |
|---|---|
| **Game** | Coordinates turns, runs the game, and decides when it ends |
| **Board** | Represents the board and manages its configuration |
| **Player** | Represents a player and tracks their position |
| **Dice** | Generates dice results |
| **Snake** | Represents a snake's head and tail |
| **Ladder** | Represents a ladder's bottom and top |
| **Client** | Initializes and demonstrates the game |

---

## 5. Validation Rules

All of the following are checked **before the game starts**.

### 5.1 Player Validation

- At least two players participate.
- Player names or identifiers are valid.
- Duplicate names may be permitted, unless the chosen implementation requires uniqueness.

### 5.2 Board Validation

- The board size is 100 cells for the initial version.
- Snake and ladder positions lie within cells 1 to 100.
- Every snake's head is above its tail.
- Every ladder's top is above its bottom.
- No cell is both a snake's head and a ladder's bottom.
- Invalid configurations are rejected before the game starts.

### 5.3 Dice Validation

- Every dice result is between 1 and 6, inclusive.
- The game does not depend on any particular random sequence to function correctly.

---

## 6. Technical Requirements

| Area | Requirement |
|---|---|
| Language | Java |
| Application type | Command-line application |
| Design approach | Object-oriented design |
| Board size | 100 cells |
| Dice | One six-sided dice |
| Persistence | None: no database or file storage |
| User interface | None: no GUI |
| Execution | A client class demonstrates a complete game |
| Randomness | The dice implementation allows deterministic behavior for demonstrations |

---

## 7. Out of Scope

These are excluded from the initial version and may be considered as future extensions.

- Graphical user interface
- Online multiplayer and network communication
- Database integration
- Saving and resuming games
- Player accounts and authentication
- Timers and turn timeouts
- Multiple winners in a single game
- Multiple dice
- Configurable board sizes
- Tournament management
- Undoing or replaying moves

---

## 8. Acceptance Criteria

The implementation is complete when every item below is satisfied.

**Setup**

- [ ] The game can be initialized with at least two players.
- [ ] The board contains 100 numbered cells.
- [ ] Snakes and ladders can be configured.
- [ ] Invalid snake and ladder configurations are rejected.

**Gameplay**

- [ ] The dice produces values from 1 to 6.
- [ ] Players take turns in the correct order.
- [ ] Players move according to their dice results.
- [ ] Players cannot move beyond cell 100.
- [ ] Landing on a snake's head moves a player to its tail.
- [ ] Landing on a ladder's bottom moves a player to its top.
- [ ] Players win only by landing exactly on cell 100.
- [ ] The game stops immediately after the first player wins.

**Presentation and Quality**

- [ ] The CLI displays the relevant events during gameplay.
- [ ] The client class demonstrates the complete game.
- [ ] The implementation has clear class responsibilities.
- [ ] The code is readable and maintainable.

---

## 9. Design Principles

| Principle | Meaning in this project |
|---|---|
| **Single Responsibility** | Each class has one clearly defined job. |
| **Encapsulation** | A class controls access to its own internal state. |
| **Separation of Concerns** | Game orchestration, board configuration, dice rolling, and player state live apart. |
| **Low Coupling** | A change in one component has minimal impact on unrelated ones. |
| **High Cohesion** | Related data and behavior are grouped together. |
| **Testability** | Core behavior can be demonstrated with predictable dice outcomes. |
| **Simplicity** | Unnecessary abstractions and design patterns are avoided. |

---

## 10. Implementation Plan

Each stage builds on the decisions made in the one before it.

1. Finalize requirements.
2. Identify entities, classes, and responsibilities.
3. Create the UML class diagram.
4. Design the board and its configuration.
5. Implement the player model.
6. Implement the dice abstraction and rolling behavior.
7. Implement snakes and ladders.
8. Implement movement and turn management.
9. Implement the winning condition and game termination.
10. Create the client class and demonstrate a complete game.
11. Review the design and document possible extensions.

---

## 11. Definition of Done

The project is complete when:

- The agreed requirements are implemented.
- The acceptance criteria are satisfied.
- A client class demonstrates a complete game, from initialization through to the announcement of the winner.

The final documentation must tell the story of the design journey: the important decisions, UML diagrams, implementation, and trade-offs.