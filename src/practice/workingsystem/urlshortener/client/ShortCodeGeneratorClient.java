package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.strategy.RandomBase62CodeGenerator;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;

public class ShortCodeGeneratorClient {

    public static void main(String[] args) {
        ShortCodeGenerator generator = new RandomBase62CodeGenerator();

        for (int i = 0; i < 5; i++) {
            System.out.println(generator.generate());
        }
    }
}