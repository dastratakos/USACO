import java.io.*;
import java.util.*;

public class LivestockLineup {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2019-Dec/Bronze/3-LivestockLineup/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int[] expected_ans = readOutput(in.n, files.get(i + 1));
            int[] final_order = doSwaps(in.n, in.k, in.a_1, in.a_2, in.b_1, in.b_2);

            if (!Arrays.equals(expected_ans, final_order)) {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + final_order + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TESTS (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TESTS");
        }
    }

    public static int[] doSwaps(int n, int k, int a_1, int a_2, int b_1, int b_2) {
        int[] cows = new int[n];
        for (int i = 0; i < n; i++) cows[i] = i + 1;
        
        int cycleSize = 0;
        boolean sorted = true;
        do {
            cycleSize++;
            reverse(cows, a_1, a_2);
            reverse(cows, b_1, b_2);
            sorted = true;
            for (int i = 0; sorted && i < n; i++) sorted = cows[i] == i + 1;
        } while (!sorted);

        k %= cycleSize;

        for (int i = 0; i < n; i++) cows[i] = i + 1;

        for (int i = 0; i < k; i++) {
            reverse(cows, a_1, a_2);
            reverse(cows, b_1, b_2);
        }

        return cows;
    }

    private static void reverse(int[] cows, int a, int b) {
        while (a < b) {
            int t = cows[a - 1];
            cows[a - 1] = cows[b - 1];
            cows[b - 1] = t;
            a++;
            b--;
        }
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        
        StringTokenizer st = new StringTokenizer(br_in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br_in.readLine());
        int a_1 = Integer.parseInt(st.nextToken());
        int a_2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br_in.readLine());
        int b_1 = Integer.parseInt(st.nextToken());
        int b_2 = Integer.parseInt(st.nextToken());
       
        br_in.close();
        return new Input(n, k, a_1, a_2, b_1, b_2);
    }

    public static int[] readOutput(int n, File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        int[] expected_ans = new int[n];
        for (int i = 0; i < n; i++) {
            expected_ans[i] = Integer.parseInt(br_out.readLine());
        }
        br_out.close();
        return expected_ans;
    }

    public static List<File> listDir(File dirName) {
        List<File> files = new ArrayList<>();
        File[] fileList = dirName.listFiles();
        for (File file : fileList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                files.add(file);
                listDir(file);
            }
        }
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                int n1 = getTestCase(f1);
                int n2 = getTestCase(f2);
                if (n1 == n2) {
                    return f1.getPath().endsWith(".in") ? -1 : 1;
                } else {
                    return n1 - n2;
                }
            }
        });
        return files;
    }

    public static int getTestCase(File file) {
        String[] tokens = file.getPath().split("/");
        String filename = tokens[tokens.length - 1];
        return Integer.parseInt(filename.split("\\.")[0]);
    }
}

class Input {
    int n, k, a_1, a_2, b_1, b_2;
    Input(int n, int k, int a_1, int a_2, int b_1, int b_2) {
        this.n = n;
        this.k = k;
        this.a_1 = a_1;
        this.a_2 = a_2;
        this.b_1 = b_1;
        this.b_2 = b_2;
    }
}