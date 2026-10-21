# Tic Tac Toe — Entity Identification & Design Decisions

*Continues from REQUIREMENTS.md · Requirements define **what** the system must do; this document defines **how** we structure it.*

---

## 1. From Requirements to Design

Before writing Java code, we need to identify:

- What are the important entities?
- What responsibility belongs to each entity?
- Which responsibilities should be delegated?
- Which parts of the system are likely to change?
- Where should we use interfaces/strategies?
- Which objects represent state versus behavior?

The major design areas are:

1. Game orchestration
2. Board management
3. Player representation
4. Move representation
5. Position representation
6. Winning evaluation
7. Bot move selection
8. Turn ordering
9. Move history and undo
10. Game configuration
11. Game result/state

> **Guiding principle:** Keep `Game` as the coordinator instead of making it responsible for every piece of business logic.

---

## 2. Initial Entity Identification

| Component | Type | Main Responsibility |
| --- | --- | --- |
| `Game` | Class | Orchestrates the game |
| `Board` | Class | Maintains board occupancy |
| `Player` | Abstract class | Common player information |
| `HumanPlayer` | Class | Represents human players |
| `BotPlayer` | Class | Represents bot players |
| `Move` | Class | Represents one successful move |
| `Position` | Value Object | Represents row and column |
| `WinningLine` | Class | Represents a winning line |
| `GameState` | Enum | Represents current game state |
| `PlayerType` | Enum | Human/Bot classification |
| `BotStrategy` | Interface | Decides a bot's move |
| `TurnOrderStrategy` | Interface | Decides turn ordering |
| `WinningStrategy` | Interface | Determines whether a player has won |
| `GameConfig` | Class | Holds game configuration |
| `GameResult` | Class | Represents result information |

Some of these are concrete classes, while others are abstractions designed to support future changes.

---

## 3. Game

`Game` is the central coordinator. It should not contain every algorithm; its job is to coordinate components.

**Responsibilities** — `Game` should:

- start the game and maintain game state
- know whose turn it is
- request a move and validate it at the game level
- place a valid move on the board and record successful moves
- invoke winning strategies and determine draw
- change game state
- support undo
- coordinate bot turns
- use the configured turn-order strategy

```
Game
 |
 +-- Board
 +-- Players
 +-- TurnOrderStrategy
 +-- WinningStrategy set
 +-- MoveHistory
 +-- GameState
```

The key point: `Game` **delegates decisions** instead of implementing all of them.

```
Game
 |
 +-- 'Who plays next?'               --> TurnOrderStrategy
 +-- 'What move should the bot make?' --> BotStrategy
 +-- 'Did the player win?'            --> WinningStrategy
```

This keeps the game flow clean.

---

## 4. Board

The `Board` represents the physical game board: **N × N** where **N >= 3**.

**Responsibilities** — `Board` should:

- know its size
- maintain cell occupancy
- check whether a position is valid
- check whether a cell is empty
- place a symbol
- remove a symbol during undo
- expose the current board state when required

```
Board
 |
 +-- size
 +-- cells
 +-- isValidPosition()
 +-- isEmpty()
 +-- place()
 +-- remove()
```

**`Board` should NOT know:**

- whose turn it is
- whether somebody won
- which bot strategy is being used
- how turns are ordered
- whether the game has started
- whether the game is a draw
- how undo works at the game level

Why? Because the board is only responsible for one question: *What is currently occupied on the board?*

---

## 5. Player Design

This was one of our important design decisions. We considered two approaches.

**Option A — a single `Player` class**

```
Player
 |
 +-- id
 +-- name
 +-- symbol
 +-- image
 +-- playerType
```

Bot-specific behavior would then be attached separately.

**Option B — Selected**

```
              Player
             /      \
            /        \
   HumanPlayer      BotPlayer
```

`Player` contains common player information. `HumanPlayer` and `BotPlayer` represent the different types of players.

---

## 6. Why Player Should Be an Abstraction

Both humans and bots are players. They share `id`, `name`, `symbol` and `image`, but their behavior differs: a human gets a move through CLI input, while a bot generates a move through a strategy.

```
Player
 |
 +-- common identity/state
 |
 +-- HumanPlayer
 |      +-- human-specific behavior
 |
 +-- BotPlayer
        +-- bot-specific behavior
```

This avoids forcing bot-specific concepts onto humans. For example, we do **not** want:

```java
class Player {
    String id;
    String name;
    char symbol;
    BotStrategy botStrategy;
}
```

because every human player would then unnecessarily carry a `BotStrategy`. Instead:

```
Player
 |
 +-- HumanPlayer
 |
 +-- BotPlayer
        +-- BotStrategy
```

---

## 7. HumanPlayer

`HumanPlayer` represents a human participant who enters a move through the CLI.

```
HumanPlayer
 |
 +-- Player information
 +-- getMove()
```

The exact CLI interaction should ideally remain **outside the domain model**. The domain responsibility is simply: *a human player's move comes from external user input.*

We should avoid tightly coupling `HumanPlayer` to `Scanner` or CLI classes. This keeps the domain model reusable if we later build a Web UI, Mobile UI, REST API or GUI.

---

## 8. BotPlayer

`BotPlayer` represents an automated player. A bot needs a strategy, but it does not need to know how the strategy works. It simply asks:

```
strategy -> chooseMove(...)
```

```
BotPlayer
    |
    v
BotStrategy
    |
    +-- Random
    +-- Easy
    +-- Medium
    +-- Unbeatable
    +-- Future strategies
```

---

## 9. BotStrategy

`BotStrategy` is an **interface**. Its responsibility: *decide what move a bot should make.*

Initial strategies: `RANDOM`, `EASY`, `MEDIUM`, `UNBEATABLE`. Future strategies can be added without modifying `Game`.

```
BotStrategy
    |
    +-- RandomBotStrategy
    +-- EasyBotStrategy
    +-- MediumBotStrategy
    +-- UnbeatableBotStrategy
```

> `Game` should not contain `if bot == RANDOM`, `if bot == EASY`, and so on.

```
Game
 |
 +-- BotPlayer
       +-- BotStrategy
```

This follows the **Strategy Pattern**.

---

## 10. Position

A position represents a location on the board. Instead of passing `int row, int col` everywhere, we introduce:

```
Position
 |
 +-- row
 +-- column
```

Example: `Position(1, 2)`.

Internally we use **0-based** indexes; the CLI uses **1-based** coordinates.

|  | Row | Column |
| --- | --- | --- |
| User input | 1 | 2 |
| Internal | 0 | 1 |

`Position` is useful because it appears in multiple parts of the system:

```
Move
 |
 +-- Position

WinningLine
 |
 +-- List<Position>
```

---

## 11. Move

A `Move` represents one **successful** player action.

```
Move
 |
 +-- Player
 +-- Position
 +-- Symbol
```

Example: Player `Alice`, Position `(1, 2)`, Symbol `X`.

Moves are needed for move history, undo, debugging and possible future replay/history functionality. Every successful move is pushed into a stack.

---

## 12. Move History

Undo is required. We decided to use a simple stack of moves: `Stack<Move>`.

```
MOVE
  |
  v
Board updated
  |
  v
Move pushed into stack
```

Example after four moves:

```
TOP
 |
 v
Move 4
Move 3
Move 2
Move 1
```

When undo is requested, we pop `Move 4` and then reverse that move on the board.

---

## 13. Why Stack of Moves?

We considered two approaches.

### Option A — Move Stack

Store `Stack<Move>` and undo by reversing the latest move.

**Advantages**

- simple
- memory efficient
- natural fit for undo
- easy to understand
- sufficient for our current game

**Disadvantage:** we must correctly restore derived game information after undo — current player, game state, winner and winning lines. We can recompute these after reversing the move.

### Option B — Snapshot Stack

Store a complete snapshot of the game after every move.

```
GameSnapshot
 |
 +-- Board state
 +-- Current player
 +-- Game state
 +-- Winner
 +-- Winning lines
```

**Advantage:** undo becomes trivial — restore the previous snapshot.

**Disadvantages**

- more memory
- more objects
- more complexity
- unnecessary for our current requirements

---

## 14. Final Undo Decision

> We choose **`Stack<Move>`**, because our system is simple enough to reconstruct the game state after reversing a move, and the design remains understandable during an interview.

---

## 15. Special Undo Rule: Human vs Bot

Suppose the game went:

```
Human -> Move 1
Bot   -> Move 2
Human -> Move 3
Bot   -> Move 4
```

The user requests `UNDO`. We do **not** want to undo only the bot's move and immediately give the bot another turn. Instead, `UNDO` reverts the complete previous human turn cycle (Human Move 3 and Bot Move 4).

```
Result:
Human -> Move 1
Bot   -> Move 2

Control returns to: Human
```

This is a responsibility of the `Game` / undo logic, **not** the `Board`.

---

## 16. Winning Strategy

Winning conditions are configurable, and we explicitly decided **not** to hardcode winning logic inside `Game`. We introduce `WinningStrategy` as an abstraction that answers:

> Has this player achieved a winning condition, and if so, which cells form the winning line(s)?

## 17. Set of Winning Strategies

Instead of one hardcoded winning algorithm, the game holds a set of strategies:

```
Set<WinningStrategy>
```

For example `ROW`, `COLUMN`, `DIAGONAL` — or `ROW`, `COLUMN`, `DIAGONAL`, `ANTI_DIAGONAL`. The game configures whichever strategies it wants.

## 18. Why a Set of Strategies?

If the game is configured with `ROW` and `COLUMN`, the player wins if **either** strategy reports a win.

```
Winning strategies
       |
       +-- RowWinningStrategy
       +-- ColumnWinningStrategy

Evaluation:
for each strategy
       |
       +-- check board
       +-- if winning
               +-- collect WinningLine
```

If any strategy succeeds, `GAME = WON`. If multiple succeed, all relevant winning lines are captured (`WinningLine 1`, `WinningLine 2`, ...).

## 19. WinningStrategy Interface

```
WinningStrategy
       |
       +-- RowWinningStrategy
       +-- ColumnWinningStrategy
       +-- DiagonalWinningStrategy
       +-- AntiDiagonalWinningStrategy
       +-- FutureCustomWinningStrategy
```

Each strategy has one focused responsibility:

| Strategy | Checks |
| --- | --- |
| `RowWinningStrategy` | Rows |
| `ColumnWinningStrategy` | Columns |
| `DiagonalWinningStrategy` | Main diagonal |
| `AntiDiagonalWinningStrategy` | Anti-diagonal |

This is much cleaner than one giant method inside `Game`:

```java
checkRows();
checkColumns();
checkDiagonal();
checkAntiDiagonal();
```

---

## 20. WinningLine

A win should not simply return `true`. We also need to know which cells caused the win, so we introduce:

```
WinningLine
 |
 +-- winning strategy/type
 +-- List<Position>
```

Example:

```
WinningLine
Type: ROW
Positions:
(0,0)
(0,1)
(0,2)
```

For an N × N board, the line contains the relevant N positions.

## 21. Multiple Winning Lines

A single move can create more than one winning line. For example, on a 3 × 3 board:

```
X | X | X
---------
O | X | O
---------
X | O | X
```

The final move could satisfy multiple configured winning conditions. So we do not model the result as `WinningLine winningLine;` but as:

```java
List<WinningLine> winningLines;
```

## 22. Winning Strategy Evaluation

```
Game
 |
 | checkWin()
 v
Set<WinningStrategy>
 |
 +-- RowStrategy
 +-- ColumnStrategy
 +-- DiagonalStrategy
 +-- AntiDiagonalStrategy
 |
 v
List<WinningLine>
```

- If `winningLines.isEmpty()` → no winning condition was satisfied.
- If `winningLines` is not empty → `GameState = WON`.

## 23. Custom Winning Strategy

Custom winning strategies are part of our future extensibility. Later we could support:

```
WinningStrategy
    |
    +-- Row
    +-- Column
    +-- Diagonal
    +-- AntiDiagonal
    +-- FourCorners
    +-- CustomPattern
```

The core game should not need to change. We simply add another implementation of `WinningStrategy` to the configured set. This is the main reason we use the strategy abstraction.

---

## 24. TurnOrderStrategy

Turn order is configurable. `TurnOrderStrategy` determines: *who should play next?*

Initial implementations: `RegistrationOrderStrategy` (default) and `RandomTurnOrderStrategy`.

```
Registration order:  P1 -> P2 -> P3 -> P1 -> P2 -> P3
Random order:        randomized
```

Future strategies can be introduced without modifying the main game flow.

## 25. Important Separation: Turn vs Bot

There are two completely different questions:

| Question | Handled by |
| --- | --- |
| Who plays next? | `TurnOrderStrategy` |
| If that player is a bot, what move should it make? | `BotStrategy` |

```
TurnOrderStrategy
        |
        v
   Current Player
        |
        v
 Is player a Bot?
      /    \
    No      Yes
    |        |
  Human     Bot
  move     strategy
```

> We should never combine these concepts into one strategy.

---

## 26. GameState

The game has four states: `NOT_STARTED`, `IN_PROGRESS`, `WON`, `DRAW`.

```
NOT_STARTED
      |
      | start()
      v
IN_PROGRESS
   /       \
  /         \
WON        DRAW
```

Normal moves are allowed only in `IN_PROGRESS`. However, `UNDO` is allowed even after `WON` or `DRAW`, because undo may bring the game back to `IN_PROGRESS`.

## 27. GameConfig

Instead of passing many unrelated parameters to `Game`, we introduce `GameConfig`:

```
GameConfig
 |
 +-- boardSize
 +-- players
 +-- turnOrderStrategy
 +-- winningStrategies
```

Bot strategy belongs to the relevant `BotPlayer`. This gives a clean separation:

| Component | Answers |
| --- | --- |
| `GameConfig` | How the game is configured |
| `Player` | Who is playing |
| `BotStrategy` | How a bot plays |

## 28. GameResult

A game result may contain:

```
GameResult
 |
 +-- GameState
 +-- Winner
 +-- WinningLines
```

```
Won:   state = WON,  winner = Player X, winningLines = [...]
Draw:  state = DRAW, winner = null,     winningLines = []
```

The result contains the information the CLI needs to display the outcome.

---

## 29. Responsibility Boundaries

| Component | Responsibility |
| --- | --- |
| `Game` | Coordinates the entire game |
| `Board` | Maintains board occupancy |
| `Player` | Represents common player information |
| `HumanPlayer` | Represents human-specific behavior |
| `BotPlayer` | Represents automated player behavior and owns/uses a bot strategy |
| `BotStrategy` | Determines a bot's move |
| `TurnOrderStrategy` | Determines who plays next |
| `WinningStrategy` | Determines whether a winning condition is satisfied |
| `WinningLine` | Represents the cells that form a winning condition |
| `Move` | Represents one successful move |
| `Position` | Represents a board coordinate |
| `GameConfig` | Represents game configuration |
| `GameResult` | Represents current/final result information |

## 30. High-Level Design

```
                         +----------------+
                         |      Game      |
                         +----------------+
                           |    |    |   |
             +-------------+    |    |   +----------------+
             |                  |    |                    |
             v                  v    v                    v
        +---------+       +---------+ +----------------+ +-----------+
        |  Board  |       | Players | | TurnOrder      | | Move      |
        +---------+       +---------+ | Strategy       | | History   |
                               |      +----------------+ +-----------+
                         +-----+------+
                         |            |
                         v            v
                       Human       BotPlayer
                                      |
                                      v
                                 BotStrategy
                                      |
                           +----------+----------+
                           |          |          |
                        Random      Easy       Medium ...

                         Game
                          |
                          v
                  Winning Strategies
                          |
          +---------------+---------------+
          |               |               |
         Row            Column          Diagonal
                                          |
                                    Anti-Diagonal
```

---

## 31. Core Strategy Separation

The three most important strategy abstractions hang off `Game`: `BotStrategy`, `TurnOrderStrategy` and `WinningStrategy`.

| Strategy | Question it answers |
| --- | --- |
| `BotStrategy` | What move should the bot make? |
| `TurnOrderStrategy` | Who should play next? |
| `WinningStrategy` | Did this player satisfy this winning condition? |

This separation is one of the key design points of the implementation.

## 32. Design Principles We Are Following

### Single Responsibility Principle

Each major class has a focused responsibility:

```
Board             -> board state
BotStrategy       -> bot decision
WinningStrategy   -> win evaluation
TurnOrderStrategy -> turn ordering
```

### Open/Closed Principle

The system should be open for adding new strategies without modifying existing core logic. Adding `AggressiveBotStrategy` or `CustomWinningStrategy` should not require modifying `Game`.

### Composition Over Hardcoded Logic

| Instead of | We use |
| --- | --- |
| `Game` knows every possible bot algorithm | `Game` → `BotStrategy` |
| `Game` knows every winning rule | `Game` → `Set<WinningStrategy>` |

---

## 33. Decisions Finalized So Far

The following decisions are now **frozen**.

| Topic | Decision |
| --- | --- |
| Board | Configurable N × N |
| Minimum N | 3 |
| Players | 2 to N-1 |
| Player design | `Player` + `HumanPlayer` + `BotPlayer` |
| Bot decision | `BotStrategy` |
| Bot strategies | Random, Easy, Medium, Unbeatable |
| Turn order | `TurnOrderStrategy` |
| Default turn order | Registration order |
| Alternative turn order | Random |
| Winning evaluation | `WinningStrategy` abstraction |
| Winning configuration | `Set<WinningStrategy>` |
| Winning types | Row, Column, Diagonal, Anti-Diagonal, future Custom |
| Winning result | `List<WinningLine>` |
| Position | Separate value object |
| Move | Separate entity |
| Undo | `Stack<Move>` |
| Human-vs-Bot undo | Undo complete human + bot cycle |
| Game state | NOT\_STARTED, IN\_PROGRESS, WON, DRAW |
| Persistence | In-memory |
| Interface | CLI |

## 34. What We Have Achieved

We have moved from requirements to a set of entities, responsibilities, design decisions and strategy boundaries:

```
Requirements
     |
     v
Entities
     |
     v
Responsibilities
     |
     v
Design decisions
     |
     v
Strategy boundaries
```

We have intentionally **not started coding yet**, which matters in an LLD machine-coding interview. We first establish:

```
WHAT -> WHY -> WHO -> RESPONSIBILITY -> RELATIONSHIP -> IMPLEMENTATION
```

## 35. Next Step

The next step is the **UML / Class Diagram**. We now have enough information to design the class relationships, capturing:

```
Game
 |
 +-- Board
 +-- Player
 |    +-- HumanPlayer
 |    +-- BotPlayer
 |           +-- BotStrategy
 +-- TurnOrderStrategy
 +-- WinningStrategy
 +-- Move
 |    +-- Position
 +-- WinningLine
 +-- GameConfig
 +-- GameResult
```

We will then decide:

- inheritance vs composition
- aggregation vs composition
- interface relationships
- multiplicities
- exact method responsibilities
- which objects own which objects
- what should be immutable
- how `Game` interacts with strategies

Only after that will we move into the Java implementation.
