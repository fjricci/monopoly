package monopoly;

public class Inactive implements SquareIf {
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