import java.io.*;
import java.util.*;

public class RectangularPasture {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Silver/2-RectangularPasture/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            long expected_ans = readOutput(files.get(i + 1));
            long num_subsets = countSubsets(in.n, in.xs, in.ys);
            
            if (expected_ans == num_subsets) {
                System.out.println("PASSED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2 + ": " + num_subsets + "\n");
            } else {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2);
                System.out.println("  Expected: " + expected_ans);
                System.out.println("  Got:      " + num_subsets + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("**** PASSED ALL TESTS (" + files.size() / 2 +
                "/" + files.size() / 2 + ") ****");
        } else {
            System.out.println("**** FAILED " + num_failed + "/" +
                files.size() / 2 + " TESTS ****");
        }
    }

    /*
    Implemented using 2D prefix sums:
    https://usaco.guide/silver/prefix-sums?lang=cpp#2d-prefix-sums.
    */
    public static long countSubsets(int n, int[] xs, int[] ys) {
        int[][] sums = new int[n + 1][n + 1];
        for (int j = 0; j < n; j++) {
            sums[xs[j]][ys[j]]++;
        }
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= n; y++) {
                if (x > 0) sums[x][y] += sums[x - 1][y];
                if (y > 0) sums[x][y] += sums[x][y - 1];
                if (x > 0 && y > 0) sums[x][y] -= sums[x - 1][y - 1];
            }
        }
        long answer = n + 1;
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                answer += getSum(sums, Math.min(xs[j], xs[k]), Math.max(xs[j], xs[k]), 1, Math.min(ys[j], ys[k]))
                        * getSum(sums, Math.min(xs[j], xs[k]), Math.max(xs[j], xs[k]), Math.max(ys[j], ys[k]), n);
            }
        }
        return answer;
    }

    public static int getSum(int[][] sums, int fromX, int toX, int fromY, int toY) {
        return sums[toX][toY] - sums[fromX - 1][toY] - sums[toX][fromY - 1] + sums[fromX - 1][fromY - 1];
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] xs = new int[n];
        int[] ys = new int[n];
        Integer[] cows = new Integer[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br_in.readLine());
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
            cows[i] = i;
        }

        // compress x and y coordinates
        Arrays.sort(cows, Comparator.comparingInt(i -> xs[i]));
        for (int x = 1; x <= n; x++) {
            xs[cows[x - 1]] = x;
        }
        Arrays.sort(cows, Comparator.comparingInt(i -> ys[i]));
        for (int y = 1; y <= n; y++) {
            ys[cows[y - 1]] = y;
        }

        br_in.close();
        return new Input(n, xs, ys);
    }

    public static long readOutput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        long expected_ans = Long.parseLong(br_out.readLine());
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
    int n;
    int[] xs;
    int[] ys;
    Input(int n, int[] xs, int[] ys) {
        this.n = n;
        this.xs = xs;
        this.ys = ys;
    }
}