import java.util.*;

public class CowGymnastics {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int n = input.nextInt();
        int[][] ranks = new int[k][n];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < n; j++)
                ranks[i][j] = input.nextInt();
        input.close();

        int count = 0;
        for (int a = 1; a <= n; a++)
            for (int b = 1; b <= n; b++)
                if (countBetter(ranks, a, b) == k) count++;
        System.out.println(count);
    }

    public static int countBetter(int[][] ranks, int a, int b) {
        int count = 0;
        for (int session = 0; session < ranks.length; session++)
            if (better(ranks, a, b, session)) count++;
        return count;
    }

    public static boolean better(int[][] ranks, int a, int b, int session) {
        int a_rank = 0, b_rank = 0;
        for (int i = 0; i < ranks[0].length; i++) {
            if (ranks[session][i] == a) a_rank = i;
            if (ranks[session][i] == b) b_rank = i;
        }
        return a_rank < b_rank;
    }
}