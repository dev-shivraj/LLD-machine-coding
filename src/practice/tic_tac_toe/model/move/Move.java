package practice.tic_tac_toe.model.move;

import practice.tic_tac_toe.model.player.Player;

public class Move {
    private final Player player;
    private final Position position;

    public Move(Player player, Position position) {
        if(player == null) {
            throw new IllegalArgumentException("layer can not be null");
        }

        if(position == null) {
            throw new IllegalArgumentException("Position can not be null");
        }

        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }
}
