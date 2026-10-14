package tic_tac_toe.model.strategy.turn;

import tic_tac_toe.model.player.Player;

import java.util.List;

public interface TurnOrderStrategy {

    List<Player> determineOrder(List<Player> players);
}