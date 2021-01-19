import java.util.*;

public class LivestockLineup {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String[] beside_a = new String[n];
        String[] beside_b = new String[n];
        for (int i = 0; i < n; i++) {
            beside_a[i] = input.next();
            input.next(); // must
            input.next(); // be
            input.next(); // milked
            input.next(); // beside
            beside_b[i] = input.next();
        }
        input.close();

        String[] cows = new String[] { "Beatrice", "Belinda", "Bella", "Bessie", "Betsy", "Blue", "Buttercup", "Sue" };
        ArrayList<String> answer = new ArrayList<String>();

        for (int i = 0; i < 8; i++) {
            int next_cow = 0;
            while (!can_go_first(answer, beside_a, beside_b, n, cows[next_cow])) {
                next_cow++;
            }
            answer.add(cows[next_cow]);
            System.out.println(cows[next_cow]);
        }
    }

    public static boolean can_go_first(ArrayList<String> answer, String[] beside_a, String[] beside_b, int N, String c) {
        int n = answer.size(), nbrs = 0;
        if (where(answer, c) != 999)
            return false;
        for (int i = 0; i < N; i++) {
            if (beside_a[i].equals(c) && where(answer, beside_b[i]) == 999)
                nbrs++;
            if (beside_b[i].equals(c) && where(answer, beside_a[i]) == 999)
                nbrs++;
        }
        if (nbrs == 2)
            return false;
        if (n > 0) {
            String last_cow = answer.get(n - 1);
            for (int i = 0; i < N; i++) {
                if (beside_a[i].equals(last_cow) && where(answer, beside_b[i]) == 999 && !beside_b[i].equals(c))
                    return false;
                if (beside_b[i].equals(last_cow) && where(answer, beside_a[i]) == 999 && !beside_a[i].equals(c))
                    return false;
            }
        }
        return true;
    }

    public static int where(ArrayList<String> answer, String c) {
        for (int i = 0; i < answer.size(); i++)
            if (answer.get(i).equals(c))
                return i;
        return 999;
    }
}