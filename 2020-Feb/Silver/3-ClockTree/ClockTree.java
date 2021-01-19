import java.util.*;

public class ClockTree {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        int[] A = new int[n];
        for (int i = 0; i < n; i++)
            A[i] = input.nextInt();

        ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<Integer>());
        }
        for (int i = 1; i < n; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            edges.get(a).add(b);
            edges.get(b).add(a);
        }
        input.close();

        int[] d = new int[n];
        d = dfs(d, edges, 0, 0, -1);

        int s0 = 0, s1 = 0, n0 = 0, n1 = 0;
        for (int i = 0; i < n; i++) {
            if (d[i] % 2 == 0) {
                s0 += A[i];
                n0++;
            } else {
                s1 += A[i];
                n1++;
            }
        }
        if ((s0 % 12) == (s1 % 12))
            System.out.println(n);
        else if ((s0 + 1) % 12 == (s1 % 12))
            System.out.println(n1);
        else if ((s0 % 12) == ((s1 + 1) % 12))
            System.out.println(n0);
        else
            System.out.println(0);
    }

    public static int[] dfs(int[] d, ArrayList<ArrayList<Integer>> edges, int i, int depth, int par) {
        d[i] = depth;
        for (int j = 0; j < edges.get(i).size(); j++)
            if (edges.get(i).get(j) != par)
                d = dfs(d, edges, edges.get(i).get(j), depth + 1, i);
        return d;
    }
}