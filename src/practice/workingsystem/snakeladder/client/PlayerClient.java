package practice.workingsystem.snakeladder.client;

import practice.workingsystem.snakeladder.Player;

public class PlayerClient {
    public static void main(String[] args) {
        Player player = new Player("P1", "Shivraj");

        System.out.println("Player ID: " + player.getId());
        System.out.println("Player Name: " + player.getName());
        System.out.println("Initial position: " + player.getPosition());

        player.moveTo(10);
        System.out.println("Updated Position: " + player.getPosition());

        player.moveTo(25);
        System.out.println("Updated Position: " + player.getPosition());
    }
}
