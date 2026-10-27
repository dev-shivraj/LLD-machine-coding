package practice.workingsystem.urlshortener.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class AliasValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;
    private static final Pattern VALID_ALIAS_PATTERN = Pattern.compile("[a-zA-Z0-9_-]+");
    private static final Set<String> RESERVED_ALIASES = Set.of("admin", "api", "login", "logout", "register", "signup", "signin", "help", "support", "about", "terms", "privacy");

    public void validate(String alias) {
        Objects.requireNonNull(alias, "alias cannot be null");

        if (alias.length() < MIN_LENGTH || alias.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Alias length must be between 3 and 30 characters");
        }

        if (!VALID_ALIAS_PATTERN.matcher(alias).matches()) {
            throw new IllegalArgumentException("Alias can contain only letters, digits, hyphens, and underscores");
        }

        if (RESERVED_ALIASES.contains(alias.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("Alias is reserved: " + alias);
        }
    }
}