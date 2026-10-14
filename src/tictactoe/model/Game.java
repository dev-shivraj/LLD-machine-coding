package tictactoe.model;

import tictactoe.exceptions.InvalidPlayerCountException;
import tictactoe.model.enums.CellState;
import tictactoe.model.enums.GameState;
import tictactoe.strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextTurn;
    private List<WinningStrategy> winningStrategies;

    public Game(int boardSize, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(boardSize); // strong HAS-A : Composition
        this.players = players; // weak HAS-A
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.IN_PROGRESS;
        this.nextTurn = 0;
        this.moves = new ArrayList<>();
    }

    public void makeMove() {
        Player player = players.get(nextTurn);
        System.out.println("It's " + player.getName() + "'s turn");

        Move move = player.makeMove(this);
        nextTurn = (nextTurn + 1) % players.size();

        // fill the cell
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Cell currCell = board.getCells().get(row).get(col);
        currCell.setCellState(CellState.FILLED);
        currCell.setPlayer(player);

        this.moves.add(move);

        if(checkWinner(move)) {
            this.setWinner(player);
            this.setGameState(GameState.COMPLETED);
        } else if(moves.size() == this.board.getSize() * this.board.getSize()) {
            this.setGameState(GameState.DRAW);
        }
    }

    private boolean checkWinner(Move move) {
        for(WinningStrategy winningStrategy : winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }

        return false;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(int nextTurn) {
        this.nextTurn = nextTurn;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        int size;
        List<Player> players;
        List<WinningStrategy> winningStrategies;

        public Game build() {
            validateNumberOfPlayers();
            // validate for bot count
            // validateBotCount();

            // every player should have unique symbols
            // validateUniqueSymbol();
            return new Game(size, players, winningStrategies);
        }

        public void validateNumberOfPlayers() {
            if(this.players.size() >= this.size) {
                throw new InvalidPlayerCountException("Number of players should be less than or equal to " + (this.size - 1));
            }
        }

        public int getSize() {
            return size;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public List<WinningStrategy> getWinningStrategies() {
            return winningStrategies;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }
    }
}
