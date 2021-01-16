import java.io.*;
import java.util.*;

public class StuckInARut {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Silver/3-StuckInARut/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int[] expected_ans = readOutput(in.n, files.get(i + 1));
            int[] blame = computeBlame(in.n, in.xs, in.ys, in.eastCows, in.northCows);
            
            if (Arrays.equals(expected_ans, blame)) {
                System.out.println("PASSED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2 + ": " + Arrays.toString(blame) + "\n");
            } else {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2);
                System.out.println("  Expected: " + Arrays.toString(expected_ans));
                System.out.println("  Got:      " + Arrays.toString(blame) + "\n");
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

    public static int[] computeBlame(int n, int[] xs, int[] ys, List<Integer> eastCows, List<Integer> northCows) {
        boolean[] isStopped = new boolean[n];
        int[] blame = new int[n];
        for (int j : eastCows) {
            for (int k : northCows) {
                if (!isStopped[j] && !isStopped[k] && xs[k] > xs[j] && ys[j] > ys[k]) {
                    if (xs[k] - xs[j] > ys[j] - ys[k]) {
                        isStopped[j] = true;
                        blame[k] += 1 + blame[j];
                    } else if (ys[j] - ys[k] > xs[k] - xs[j]) {
                        isStopped[k] = true;
                        blame[j] += 1 + blame[k];
                    }
                }
            }
        }
        return blame;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] xs = new int[n];
        int[] ys = new int[n];
        List<Integer> eastCows = new ArrayList<>();
        List<Integer> northCows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br_in.readLine());
            if (st.nextToken().charAt(0) == 'E') {
                eastCows.add(i);
            } else {
                northCows.add(i);
            }
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
        }
        eastCows.sort(Comparator.comparingInt(i -> ys[i]));
        northCows.sort(Comparator.comparingInt(i -> xs[i]));
        br_in.close();
        return new Input(n, xs, ys, eastCows, northCows);
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
    int n;
    int[] xs;
    int[] ys;
    List<Integer> eastCows = new ArrayList<>();
    List<Integer> northCows = new ArrayList<>();
    Input(int n, int[] xs, int[] ys, List<Integer> eastCows, List<Integer> northCows) {
        this.n = n;
        this.xs = xs;
        this.ys = ys;
        this.eastCows = eastCows;
        this.northCows = northCows;
    }
}