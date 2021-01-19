import java.util.*;

public class RectangularPasture {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] xs = new int[n];
        int[] ys = new int[n];
        Integer[] cows = new Integer[n];
        for (int i = 0; i < n; i++) {
            xs[i] = input.nextInt();
            ys[i] = input.nextInt();
            cows[i] = i;
        }
        input.close();

        // compress x and y coordinates
        Arrays.sort(cows, Comparator.comparingInt(i -> xs[i]));
        for (int x = 1; x <= n; x++) {
            xs[cows[x - 1]] = x;
        }
        Arrays.sort(cows, Comparator.comparingInt(i -> ys[i]));
        for (int y = 1; y <= n; y++) {
            ys[cows[y - 1]] = y;
        }

        System.out.println(countSubsets(n, xs, ys));
    }

    /*
    Implemented using 2D prefix sums:
    https://usaco.guide/silver/prefix-sums?lang=cpp#2d-prefix-sums.
    */
    public static long countSubsets(int n, int[] xs, int[] ys) {
        int[][] sums = new int[n + 1][n + 1];
        for (int j = 0; j < n; j++) {
            sums[xs[j]][ys[j]]++;
        }
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                if (x > 0) sums[x][y] += sums[x - 1][y];
                if (y > 0) sums[x][y] += sums[x][y - 1];
                if (x > 0 && y > 0) sums[x][y] -= sums[x - 1][y - 1];
            }
        }
        long answer = n + 1;
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                answer += getSum(sums, Math.min(xs[j], xs[k]), Math.max(xs[j], xs[k]), 1, Math.min(ys[j], ys[k]))
                        * getSum(sums, Math.min(xs[j], xs[k]), Math.max(xs[j], xs[k]), Math.max(ys[j], ys[k]), n);
            }
        }
        return answer;
    }

    public static int getSum(int[][] sums, int fromX, int toX, int fromY, int toY) {
        return sums[toX][toY] - sums[fromX - 1][toY] - sums[toX][fromY - 1] + sums[fromX - 1][fromY - 1];
    }
}