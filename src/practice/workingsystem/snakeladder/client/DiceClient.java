package practice.workingsystem.snakeladder.client;

import practice.workingsystem.snakeladder.Dice;
import practice.workingsystem.snakeladder.FixedDice;
import practice.workingsystem.snakeladder.StandardDice;

import java.util.List;

public class DiceClient {
    public static void main(String[] args) {
        Dice standardDice  = new StandardDice();
        System.out.println(("Random dice rolls:"));
        for(int i = 0; i < 5; ++i) {
            System.out.println(standardDice.roll());
        }


        Dice fixedDice = new FixedDice(
                List.of(3, 5, 2, 6, 1)
        );

        System.out.println("\nFixed dice rolls:");
        for(int i = 0; i < 5; ++i) {
            System.out.println(fixedDice.roll());
        }
    }
}
