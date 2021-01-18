import java.io.*;
import java.util.*;

public class DaisyChains {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] petals = new int[n];
        for (int i = 0; i < n; i++) {
            petals[i] = input.nextInt();
        }
        input.close();

        int num_photos = 0;
        for (int i = 0; i < n; i++) {
            boolean[] present = new boolean[1001];
            int petalsSeen = 0;
            for (int j = i; j < n; j++) {
                petalsSeen += petals[j];
                present[petals[j]] = true;
                if (petalsSeen % (j - i + 1) == 0 && present[petalsSeen / (j - i + 1)]) {
                    num_photos++;
                }
            }
        }
        System.out.println(num_photos);
    }
}