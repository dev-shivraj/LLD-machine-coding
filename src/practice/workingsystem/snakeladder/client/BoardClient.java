package practice.workingsystem.snakeladder.client;

import practice.workingsystem.snakeladder.Board;
import practice.workingsystem.snakeladder.Ladder;
import practice.workingsystem.snakeladder.Snake;

import java.util.List;

public class BoardClient {
    public static void main(String[] args) {
        List<Snake> snakes = List.of(
                new Snake(16, 6),
                new Snake(47, 26),
                new Snake(64, 60),
                new Snake(93, 73)
        );

        List<Ladder> ladders = List.of(new Ladder(3, 22),
                new Ladder(8, 30),
                new Ladder(20, 41),
                new Ladder(63, 81)
        );

        Board board = new Board(100, snakes, ladders);

        System.out.println(board.resolvePosition(5));
        System.out.println(board.resolvePosition(8));
        System.out.println(board.resolvePosition(47));
        System.out.println(board.resolvePosition(100));
    }
}
