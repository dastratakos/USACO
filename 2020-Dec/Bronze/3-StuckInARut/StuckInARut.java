import java.io.*;
import java.util.*;

public class StuckInARut {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] xs = new int[n];
        int[] ys = new int[n];
        char[] dirs = new char[n];
        for (int i = 0; i < n; i++) {
            dirs[i] = input.next().charAt(0);
            xs[i] = input.nextInt();
            ys[i] = input.nextInt();
        }
        input.close();

        int[] answer = new int[n];
        Arrays.fill(answer, Integer.MAX_VALUE);

        List<Integer> differences = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                differences.add(Math.abs(xs[k] - xs[j]));
                differences.add(Math.abs(ys[k] - ys[j]));
            }
        }

        Collections.sort(differences);
        for (int d : differences) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (dirs[j] == 'E' && dirs[k] == 'N' && xs[j] < xs[k] && ys[k] < ys[j]) {
                        if (xs[j] + d == xs[k] && ys[k] + Math.min(answer[k], d) > ys[j]) {
                            answer[j] = Math.min(answer[j], d);
                        } else if (ys[k] + d == ys[j] && xs[j] + Math.min(answer[j], d) > xs[k]) {
                            answer[k] = Math.min(answer[k], d);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(answer[i] == Integer.MAX_VALUE ? "Infinity" : answer[i]);
        }
    }
}