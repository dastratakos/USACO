import java.io.*;
import java.util.*;

public class Triangles {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Feb/Bronze/1-Triangles/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int expected_ans = readOutput(files.get(i + 1));
            int max_area = findMax(in.n, in.x, in.y);

            if (expected_ans != max_area) {
                num_failed++;
                System.out.println("FAILED TEST CASE " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + max_area + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TEST CASES (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TEST CASES");
        }
    }

    public static int findMax(int n, int[] x, int[] y) {
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
        return max_area;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br_in.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }
        br_in.close();
        return new Input(n, x, y);
    }

    public static int readOutput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        int expected_ans = Integer.parseInt(br_out.readLine());
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
    int[] x;
    int[] y;
    Input(int n, int[] x, int[] y) {
        this.n = n;
        this.x = x;
        this.y = y;
    }
}