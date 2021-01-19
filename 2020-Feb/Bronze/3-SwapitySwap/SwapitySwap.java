import java.util.*;

public class SwapitySwap {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        int a1 = input.nextInt();
        int a2 = input.nextInt();
        int b1 = input.nextInt();
        int b2 = input.nextInt();
        input.close();
       
        int[] cows = new int[n];
        for (int i = 0; i < n; i++) cows[i] = i + 1;
        
        int cycleSize = 0;
        boolean sorted = true;
        do {
            cycleSize++;
            reverse(cows, a1, a2);
            reverse(cows, b1, b2);
            sorted = true;
            for (int i = 0; sorted && i < n; i++) sorted = cows[i] == i + 1;
        } while (!sorted);

        k %= cycleSize;

        for (int i = 0; i < n; i++) cows[i] = i + 1;

        for (int i = 0; i < k; i++) {
            reverse(cows, a1, a2);
            reverse(cows, b1, b2);
        }

        for (int c : cows)
            System.out.println(c);
    }

    private static void reverse(int[] cows, int a, int b) {
        while (a < b) {
            int t = cows[a - 1];
            cows[a - 1] = cows[b - 1];
            cows[b - 1] = t;
            a++;
            b--;
        }
    }
}