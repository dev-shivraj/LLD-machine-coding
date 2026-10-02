# Tic Tac Toe — Requirements Specification

*Machine-coding / LLD · Frozen scope for the initial implementation*

---

## 1. Overview

Design and implement a **configurable Tic Tac Toe game** that supports:

| Capability | Detail |
| --- | --- |
| Board | Variable size (N × N) |
| Players | Multiple players, Human and Bot |
| Bot strategies | Configurable |
| Turn order | Configurable strategies |
| Winning rules | Configurable, with multiple combinations |
| Undo | Full undo support |
| Detection | Winning-line detection |
| Interface | CLI-based interaction |
| Storage | In-memory game state |

> The initial implementation should provide a clean foundation that can be extended with additional strategies and features in the future.

---

## 2. Board Requirements

- The board size must be configurable and the board must be **N × N**.
- **N must be at least 3.**
- The board must initially contain only empty cells.
- The board must support all configured players.
- A cell can contain **at most one** player's symbol.
- A move cannot overwrite an occupied cell.

| N | Board |
| --- | --- |
| 3 | 3 × 3 |
| 4 | 4 × 4 |
| 10 | 10 × 10 |

---

## 3. Player Requirements

### 3.1 Number of Players

- A game must contain at least 2 players.
- The maximum number of players is N − 1.

```
2 <= numberOfPlayers <= N - 1
```

| Board | Max players |
| --- | --- |
| 3 × 3 | 2 |
| 4 × 4 | 3 |
| 5 × 5 | 4 |

### 3.2 Player Information

Every player must have a unique ID, name, symbol, image metadata and player type.

```
ID       : 101
Name     : Shivraj
Symbol   : X
Image    : shivraj.png
Type     : HUMAN
```

### 3.3 Player Type

The game must support **HUMAN** and **BOT** players.

Human and Bot players may have different properties. Common player information should be shared, while player-type-specific information should remain specific to that player type.

---

## 4. Symbol Requirements

- The user can provide a symbol for each player.
- Every player's symbol must be **unique**.
- A symbol cannot be empty.
- A symbol cannot consist only of whitespace.
- The initial implementation uses the symbol for board representation and game logic.
- Image information is stored but is not used for gameplay initially.

| Player | Symbol |
| --- | --- |
| Player 1 | `X` |
| Player 2 | `O` |
| Player 3 | `#` |
| Player 4 | `@` |

---

## 5. Image Requirements

- Each player may have an image associated with them.
- The image is stored as **metadata** and is not used by the current CLI implementation.
- The design should allow a future UI to display the image instead of, or alongside, the player's symbol.

```
Player
├── Symbol → X
└── Image  → shivraj.png
```

---

## 6. Human Player Requirements

- A human player provides their move through the CLI.
- A human move consists of a **row** and a **column**.
- The move must be validated before being applied.
- Human-specific properties may be added in the future.

---

## 7. Bot Player Requirements

- A Bot is treated as a player.
- A Bot must automatically determine its move.
- A Bot must use a **configurable strategy**.
- Bot strategy must be replaceable without changing the core game flow.

Initial Bot strategies: `RANDOM`, `EASY`, `MEDIUM`, `UNBEATABLE`.

---

## 8. Bot Strategy Requirements

| Strategy | Behaviour |
| --- | --- |
| **Random** | Selects a valid empty cell randomly. |
| **Easy** | Uses a basic decision-making strategy. Exact implementation defined in the design/implementation phase. |
| **Medium** | Uses a more advanced strategy than Easy. Exact implementation defined in the design/implementation phase. |
| **Unbeatable** | Uses an algorithm intended to avoid losing under the supported game configuration. Exact implementation defined in the design/implementation phase. |

### 8.5 Future Bot Strategies

The design should allow additional strategies later, such as `HARD`, `CUSTOM` and `MINIMAX`, without requiring changes to the core game flow.

---

## 9. Turn Order Requirements

The order in which players receive turns must be configurable.

### 9.1 Registration Order *(default)*

Players take turns in registration order throughout the game.

```
Player 1 → Player 2 → Player 3 → ... → Player N → Player 1
```

### 9.2 Random Order

The game must support random turn ordering as an alternative. Players are randomized before gameplay begins.

### 9.3 Future Turn Strategies

The design should allow additional turn-order strategies, for example:

`REGISTRATION_ORDER` · `RANDOM_ORDER` · `CUSTOM_ORDER`

---

## 10. Move Requirements

A move consists of: **Player, Row, Column, Player symbol.**

A move is valid only when all of the following hold:

1. The game is in progress.
2. It is the player's turn.
3. Row is within the board.
4. Column is within the board.
5. The selected cell is empty.

> Invalid moves must **not** modify the game state.

---

## 11. Coordinate Requirements

The CLI uses **1-based** coordinates. The internal implementation may use 0-based indexing.

```
1 1    1 2    1 3
2 1    2 2    2 3
3 1    3 2    3 3
```

---

## 12. Winning Strategy Requirements

The winning condition must be configurable. Initial strategies: `ROW`, `COL`, `DIAG`.

The user must be able to select one or more strategies, for example:

- `ROW`
- `ROW + COL`
- `ROW + DIAG`
- `COL + DIAG`
- `ROW + COL + DIAG`

### 13. Row Winning Condition

If `ROW` is enabled, a player wins by occupying N consecutive cells in a row.

```
X X X X
. . . .
. . . .
. . . .
```

### 14. Column Winning Condition

If `COL` is enabled, a player wins by occupying N consecutive cells in a column.

```
X . . .
X . . .
X . . .
X . . .
```

### 15. Diagonal Winning Condition

If `DIAG` is enabled, a player wins by occupying N consecutive cells along a supported diagonal. Both directions are supported.

**Main diagonal**

```
X . . .
. X . .
. . X .
. . . X
```

**Anti-diagonal**

```
. . . X
. . X .
. X . .
X . . .
```

### 16. Winning Strategy Combination

If multiple strategies are selected, satisfying **any one** of them results in a win. The game does not require all configured conditions to be satisfied.

> Example: with `ROW + COL`, a player can win through a row **or** a column.

### 17. Custom Winning Strategy *(future)*

The system should be designed to support custom winning conditions in the future, for example a 10 × 10 board needing 5 consecutive symbols, or completely custom patterns. Custom strategies are **not** required in the initial implementation.

---

## 18. Winning Line Requirements

When a player wins, the game should identify the **winning line**: the cells that satisfied the winning condition.

```
Winner: Shivraj
Winning line:
(1,1)
(1,2)
(1,3)
(1,4)
```

### 18.1 Multiple Winning Lines

A single move may create multiple winning lines, so the game should be capable of representing `List<WinningLine>`.

```
Winning Line 1 → ROW
Winning Line 2 → COL
```

The CLI may display one or multiple winning lines.

---

## 19. Draw Requirements

The game is a draw when every cell is occupied **and** no player has satisfied any configured winning condition.

```
Board Full + No Winner  →  DRAW
```

---

## 20. Game State Requirements

The game maintains these states: `NOT_STARTED`, `IN_PROGRESS`, `WON`, `DRAW`.

```
NOT_STARTED
     ↓
IN_PROGRESS
  ┌──┴──┐
  ↓     ↓
 WON   DRAW
```

## 21. Game Completion Requirements

When the game reaches `WON` or `DRAW`, normal moves are no longer allowed. The game may continue only through supported operations such as `UNDO`.

---

## 22. Undo Requirements

The game must support undo.

- Every successful move must be recorded.
- Multiple undo operations must be supported.
- Undo must restore the previous valid game state, including:
    - the board
    - the current player
    - the game state
    - winner information
    - winning-line information
- Undo is allowed after a win and after a draw.
- After undoing a winning or draw-producing move, the game may return to `IN_PROGRESS`.

## 23. Human vs Bot Undo

For a Human vs Bot game, undo reverts the **complete human turn cycle** by default.

```
Human Move 1
Bot Move 1
Human Move 2
Bot Move 2
UNDO
   ↓
Human Move 1
Bot Move 1      ← turn returns to the human
```

This prevents undo from reverting only the Bot's response and leaving the game in an unexpected state.

## 24. Multiple Undo

The user may perform multiple undo operations.

```
Move 1, Move 2, Move 3, Move 4
UNDO, UNDO
   ↓
State after Move 2
```

Undo cannot continue once there are no applicable moves remaining.

---

## 25. CLI Requirements

The initial application must be playable through a CLI.

**Game setup** — the user can configure:

- Board size
- Number of players
- Player information and symbols
- Player types
- Bot strategies
- Turn order strategy
- Winning strategies

**Gameplay** — the CLI supports `MOVE`, `UNDO`, `STATUS` and `QUIT`.

## 26. Status Requirements

The user should be able to view the current game status.

```
Board:
X O .
. X .
. . O
Game State: IN_PROGRESS
Current Player: Shivraj
Symbol: X
```

---

## 27. Game Storage Requirements

- Game state is maintained in memory.
- No database or external persistence is required.
- Game state is lost when the application exits.

## 28. Multiple Game Requirements

The initial implementation supports **one game per application execution**.

Future scope: multiple simultaneous games, game sessions, game IDs, game persistence.

## 29. Out of Scope

The initial implementation does not require:

- Database, REST API, Web UI, Mobile UI
- Network multiplayer
- Authentication and authorization
- Matchmaking, leaderboards, spectators, chat
- Persistent game history and game replay
- Multiple simultaneous games
- Image rendering
- Custom winning-strategy implementation
- Advanced Bot strategies beyond the agreed initial ones

---

## 30. Extensibility Requirements

The design should allow future extensions without major changes to the core game flow.

| Area | Today | Future |
| --- | --- | --- |
| Bot strategies | Random, Easy, Medium, Unbeatable | More strategies |
| Turn order strategies | Registration Order, Random Order | More strategies |
| Winning strategies | Row, Column, Diagonal, Combination | Custom strategies |
| Player types | Human, Bot | More player types |
| Player presentation | Symbol | Image, other visual forms |

## 31. Important Design Boundaries

These responsibilities should remain conceptually separate. The implementation should avoid placing all of them into a single `Game` class.

| Component | Responsibility |
| --- | --- |
| Bot Strategy | How does a Bot choose a move? |
| Turn Order Strategy | Who gets the next turn? |
| Winning Strategy | What constitutes a win? |
| Board | What is currently occupied? |
| Move History | What moves have happened and what can be undone? |
| Player | Who is participating? |

---

## 32. Final Requirement Summary

| Category | Requirement |
| --- | --- |
| Board | Configurable N × N |
| Minimum board | N >= 3 |
| Players | 2 to N − 1 |
| Player types | Human / Bot |
| Player ID | Required |
| Player name | Required |
| Player symbol | User-provided and unique |
| Player image | Stored for future use |
| Bot strategies | Random / Easy / Medium / Unbeatable |
| Turn order | Configurable |
| Default turn order | Registration order |
| Random turn order | Supported |
| Winning strategy | Configurable |
| Winning conditions | Row / Column / Diagonal |
| Winning combinations | Supported |
| Custom winning strategy | Future extension |
| Winning lines | Supported |
| Multiple winning lines | Supported |
| Draw | Full board without winner |
| Undo | Supported |
| Multiple undo | Supported |
| Undo after win/draw | Supported |
| Human + Bot undo | Complete turn cycle |
| Game states | Not Started / In Progress / Won / Draw |
| CLI | Required |
| Persistence | Not required |
| Database | Not required |
| Multiple games | Future scope |

---

## 33. Frozen Scope

The requirements above are **frozen** for the initial Tic Tac Toe machine-coding implementation.

Any feature introduced later should be explicitly classified as one of:

- **Required implementation**
- **Design extension**
- **Future scope**

This prevents scope creep while still allowing extensibility to be demonstrated during the LLD discussion.
