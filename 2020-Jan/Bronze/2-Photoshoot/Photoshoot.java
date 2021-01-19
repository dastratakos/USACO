import java.util.*;

public class Photoshoot {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] b = new int[n];
        for (int i = 0; i < n - 1; i++) {
            b[i] = input.nextInt();
        }
        input.close();

        boolean[] used = new boolean[n + 1];
        int[] a = new int[n];

        for (int a_0 = 1; a_0 <= n; a_0++) {
            a[0] = a_0;
            for (int i = 1; i < n; i++)
                a[i] = b[i - 1] - a[i - 1];
            for (int i = 1; i <= n; i++)
                used[i] = false;
            boolean bad = false;
            for (int i = 0; i < n; i++) {
                if (a[i] < 1 || a[i] > n || used[a[i]]) {
                    bad = true;
                    break;
                }
                used[a[i]] = true;
            }
            if (!bad) {
                for(int i = 0; i < n; i++)
                    System.out.print(a[i] + ((i < n - 1) ? " " : ""));
                System.out.println();
                break;
            }
        }
    }
}