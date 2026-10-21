package practice.workingsystem.tic_tac_toe.model.strategy.turn;

import practice.workingsystem.tic_tac_toe.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomTurnOrderStrategy implements TurnOrderStrategy {

    @Override
    public List<Player> determineOrder(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Players cannot be null or empty");
        }

        List<Player> shuffledPlayers = new ArrayList<>(players);
        Collections.shuffle(shuffledPlayers);
        return List.copyOf(shuffledPlayers);
    }
}