import java.util.*;

public class WhereAmI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = input.next();
        }
        input.close();

        String currLine = "";
        int length = 0; // current length of line
		for (String word : words) {
            if ((length + word.length()) > k) {
                System.out.println(currLine);
                currLine = word;
                length = word.length();
            } else {
                if (length > 0) {
                    currLine = currLine + " ";
                }
                currLine = currLine + word;
                length += word.length();
            }
        }
        System.out.println(currLine);
    }
}