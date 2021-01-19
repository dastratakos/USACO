import java.util.*;

public class MadScientist {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        char[] a = input.next().toCharArray();
        char[] b = input.next().toCharArray();
        input.close();
        
        int num_swaps = 0;
        while (!new String(a).equals(new String(b))) {
            num_swaps++;
            int lhs = 0;
            while (a[lhs] == b[lhs]) lhs++;
            int rhs = n-1;
            while (a[rhs] == b[rhs]) rhs--;
            for (int i = lhs; i <= rhs; i++) {
                if (a[i] == 'G') a[i] = 'H';
                else a[i] = 'G';
            }
        }
        System.out.println(num_swaps);
    }
}