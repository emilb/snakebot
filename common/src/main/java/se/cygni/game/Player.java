package se.cygni.game;

public class Player {
    private final String name;
    private final char headChar;
    private final char tailChar;
    private final String color;
    private String playerId;

    public Player(String name, String color) {
        this(name, 'o', 'x', color);
    }

    public Player(String name, char headChar, char tailChar, String color) {
        this.name = name;
        this.color = color;
        this.headChar = headChar;
        this.tailChar = tailChar;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public char getHeadChar() {
        return headChar;
    }

    public char getTailChar() {
        return tailChar;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name != null ? name.equals(player.name) : player.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
