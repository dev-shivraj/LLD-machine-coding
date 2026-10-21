package practice.workingsystem.tic_tac_toe.model.strategy.turn;

import practice.workingsystem.tic_tac_toe.model.player.Player;

import java.util.List;

public interface TurnOrderStrategy {

    List<Player> determineOrder(List<Player> players);
}