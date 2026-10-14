package practice.tic_tac_toe.model.strategy.winning;

import practice.tic_tac_toe.model.enums.WinningConditionType;
import practice.tic_tac_toe.model.move.Position;

import java.util.List;
import java.util.Objects;

public class WinningLine {
    private final WinningConditionType type;
    private final List<Position> positions;

    public WinningLine(WinningConditionType type, List<Position> positions) {
        if(type == null) {
            throw new IllegalArgumentException("Winning condition type cannot be null");
        }

        if(positions == null || positions.isEmpty()) {
            throw new IllegalArgumentException("Winning line must contain positions");
        }

        this.type = type;
        this.positions = List.copyOf(positions);
    }

    public WinningConditionType getType() {
        return type;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WinningLine that)) return false;
        return type == that.type && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, positions);
    }
}
