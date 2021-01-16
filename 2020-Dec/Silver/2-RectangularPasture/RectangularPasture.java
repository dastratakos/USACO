import java.io.*;
import java.util.*;

public class RectangularPasture {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Silver/2-RectangularPasture/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            String[] expected_ans = readOutput(in.n, files.get(i + 1));
            String[] amounts = simulateAmounts(in.n, in.xs, in.ys, in.dirs);
            
            if (!Arrays.equals(expected_ans, amounts)) {
                num_failed++;
                System.out.println("FAILED TEST CASE " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + amounts + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TEST CASES (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TEST CASES");
        }
    }

    public static String[] simulateAmounts(int n, int[] xs, int[] ys, char[] dirs) {
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

        String[] amounts = new String[n];
        for (int i = 0; i < n; i++) {
            amounts[i] = answer[i] == Integer.MAX_VALUE ? "Infinity" : Integer.toString(answer[i]);
        }
        return amounts;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] xs = new int[n];
        int[] ys = new int[n];
        char[] dirs = new char[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br_in.readLine());
            dirs[i] = st.nextToken().charAt(0);
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
        }
        br_in.close();
        return new Input(n, xs, ys, dirs);
    }

    public static String[] readOutput(int n, File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        String[] expected_ans = new String[n];
        for (int i = 0; i < n; i++) {
            expected_ans[i] = br_out.readLine();
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
    int n;
    int[] xs;
    int[] ys;
    char[] dirs;
    Input(int n, int[] xs, int[] ys, char[] dirs) {
        this.n = n;
        this.xs = xs;
        this.ys = ys;
        this.dirs = dirs;
    }
}