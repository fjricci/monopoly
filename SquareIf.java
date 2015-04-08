package monopoly;

/**
 * Created by fjricci on 4/7/2015.
 */
public interface SquareIf {
    int cost();

    void purchase(Player player);

    int rent(int val);

    Player owner();
}
