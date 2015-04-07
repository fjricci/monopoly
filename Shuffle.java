package monopoly;

public class Shuffle
{
	// swaps array elements i and j
    public static void exch(Card[] a, int i, int j) {
        Card swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // take as input an array of strings and rearrange them in random order
    public static void shuffle(Card[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N-i));   // between i and N-1
            exch(a, i, r);
        }
    }

    // take as input an array of strings and print them out to standard output
    public static void show(Card[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}