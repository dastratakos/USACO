import java.io.*;
import java.util.*;

public class Triangles {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = input.nextInt();
            y[i] = input.nextInt();
        }
        input.close();

        int max_area = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) { // same x-coordinate
                if (i == j || x[i] != x[j]) continue;
                for (int k = 0; k < n; k++) { // same y-coordinate
                    if (i == k || y[i] != y[k]) continue;
                    max_area = Math.max(max_area, Math.abs(x[k] - x[i]) * Math.abs(y[j] - y[i]));
                }
            }
        }
        System.out.println(max_area);
    }
}