import java.util.*;

public class Race {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int n = input.nextInt();
        int[] xs = new int[n];
        for (int i = 0; i < n; i++) {
            xs[i] = input.nextInt();
        }
        input.close();

        for (int i = 0; i < n; i++) {
            System.out.println(solve(k, xs[i]));
        }
    }

    public static int solve(int k, int x) {
        int lhstravel = 0;
        int rhstravel = 0;
        int timeused = 0;
        for (int currspeed = 1;; currspeed++) {
            lhstravel += currspeed;
            timeused++;
            if (lhstravel + rhstravel >= k) return timeused;
            if (currspeed >= x) {
                rhstravel += currspeed;
                timeused++;
                if (lhstravel + rhstravel >= k) return timeused;
            }
        }
    }
}