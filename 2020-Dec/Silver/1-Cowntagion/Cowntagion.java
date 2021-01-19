import java.io.*;
import java.util.*;

public class Cowntagion {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] num_children = new int[n];
        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt();
            int b = input.nextInt();
            num_children[a - 1]++; num_children[b - 1]++;
        }
        input.close();

        int num_days = n - 1;                            // number of move events
        for (int i = 0; i < n; i++) {
            // if i is not a leaf node
            if (num_children[i] > 1 || i == 0) {
                int children = num_children[i];
                if (i != 0) children--;

                // compute ceil(log(children + 1))
                int log_children = 0;
                int pow = 1;
                while(pow < children + 1) { log_children++; pow *= 2; }

                num_days += log_children;
            }
        }
        System.out.println(num_days);
    }
}