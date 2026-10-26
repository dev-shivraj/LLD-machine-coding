package practice.workingsystem.urlshortener.strategy;

import java.security.SecureRandom;
import java.util.Objects;

public class RandomBase62CodeGenerator implements ShortCodeGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_CODE_LENGTH = 7;

    private final SecureRandom random;
    private final int codeLength;

    public RandomBase62CodeGenerator() {
        this(new SecureRandom(), DEFAULT_CODE_LENGTH);
    }

    public RandomBase62CodeGenerator(SecureRandom random, int codeLength) {
        this.random = Objects.requireNonNull(random, "random cannot be null");

        if (codeLength <= 0) {
            throw new IllegalArgumentException("codeLength must be greater than zero");
        }
        this.codeLength = codeLength;
    }

    @Override
    public String generate() {
        StringBuilder code = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; ++i) {
            int index = random.nextInt(ALPHABET.length());
            code.append(ALPHABET.charAt(index));
        }

        return code.toString();
    }
}
