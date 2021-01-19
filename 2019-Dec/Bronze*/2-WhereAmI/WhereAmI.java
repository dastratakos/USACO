import java.util.*;

public class WhereAmI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String s = input.next();
        input.close();

        int k = 1;
        while (dups(n, s, k)) k++;

        System.out.println(k);
    }

    /*
    Returns true if there is a duplicate using length k
    */
    public static boolean dups(int n, String s, int k) {
        Set<String> X = new HashSet<>();
        for (int i = 0; i <= n - k; i++) {
            if (X.contains(s.substring(i, i + k))) return true;
            X.add(s.substring(i, i + k));
        }
        return false;
    }
}