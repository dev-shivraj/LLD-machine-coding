package practice.tic_tac_toe.model.strategy.turn;

import practice.tic_tac_toe.model.player.Player;

import java.util.List;

public class RegistrationOrderStrategy implements TurnOrderStrategy {

    @Override
    public List<Player> determineOrder(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Players cannot be null or empty");
        }

        return List.copyOf(players);
    }
}