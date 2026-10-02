package tic_tac_toe.model.player;

public abstract class Player {
    private final String id;
    private final String name;
    private final char symbol;
    private final String image;

    protected Player(String id, String name, char symbol, String image) {
        if(id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id can not be blank");
        }

        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name can not be blank");
        }

        if(Character.isWhitespace(symbol)) {
            throw new IllegalArgumentException("Player symbol can not be whitespace");
        }

        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getImage() {
        return image;
    }
}
