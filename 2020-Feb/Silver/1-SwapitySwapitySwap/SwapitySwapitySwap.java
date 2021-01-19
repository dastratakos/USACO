import java.util.*;

public class SwapitySwapitySwap {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        int k = input.nextInt();
        int[] ls = new int[m];
        int[] rs = new int[m];
        for (int i = 0; i < m; i++) {
            ls[i] = input.nextInt();
            rs[i] = input.nextInt();
        }
        input.close();

        int[] to = new int[n];
        {
            int[] l = new int[n];
            for (int i = 0; i < n; i++)
                l[i] = i;
            for (int i = 0; i < m; i++) {
                int a = ls[i] - 1;
                int b = rs[i] - 1;
                while (a < b) {
                    int t = l[a];
                    l[a] = l[b];
                    l[b] = t;
                    a++;
                    b--;
                }
            }
            for (int i = 0; i < n; i++)
                to[i] = l[i];
        }
        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = i + 1;
        while (k > 0) {
            if (k % 2 == 1) ret = apply(ret, to);
            k /= 2;
            if (k > 0) to = apply(to, to);
        }
        for (int val : ret)
            System.out.println(val);
    }

    public static int[] apply(int[] l, int[] op) {
        int[] ret = new int[l.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l[op[i]];
        }
        return ret;
    }
}