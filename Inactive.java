package monopoly;

public class Inactive implements Square {
    private int pos;
    private String name;

    public Inactive(String name, int pos) {
        this.name = name;
        this.pos = pos;
    }

    public int position() {
        return pos;
    }

    public String name() {
        return name;
    }

    public boolean isOwnable() {
        return false;
    }

    public boolean isMortgaged() {
        return false;
    }

    public int mortgageCost() {
        return 0;
    }

    public int cost() {
        return 0;
    }

    public void purchase(Player player) {
    }

    public int rent(int val) {
        return 0;
    }

    public Player owner() {
        return null;
    }
}