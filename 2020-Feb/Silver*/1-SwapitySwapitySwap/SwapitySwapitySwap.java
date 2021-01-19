import java.util.*;

public class SwapitySwapitySwap {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        int n = input.nextInt();
        int[][] ranks = new int[k][n];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < n; j++)
                ranks[i][j] = input.nextInt();
        input.close();
    }
}