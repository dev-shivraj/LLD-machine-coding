package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.RandomBase62CodeGenerator;
import practice.workingsystem.urlshortener.ShortCodeGenerator;

public class ShortCodeGeneratorClient {

    public static void main(String[] args) {
        ShortCodeGenerator generator = new RandomBase62CodeGenerator();

        for (int i = 0; i < 5; i++) {
            System.out.println(generator.generate());
        }
    }
}