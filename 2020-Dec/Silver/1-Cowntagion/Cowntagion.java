import java.io.*;
import java.util.*;

public class Cowntagion {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Silver/1-Cowntagion/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int expected_ans = readOutput(in.n, files.get(i + 1));
            int min_days = findMinDays(in.n, in.num_children);
            
            if (expected_ans == min_days) {
                System.out.println("PASSED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2 + ": " + min_days + "\n");
            } else {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2);
                System.out.println("  Expected: " + expected_ans);
                System.out.println("  Got:      " + min_days + "\n");
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

    public static int findMinDays(int n, int[] num_children) {
        int num_days = n - 1;                            // number of move events
        for (int i = 0; i < n; i++) {
            // if i is not a leaf node
            if (num_children[i] > 1 || i == 0) {
                int children = num_children[i];
                if (i != 0) children--;

                // compute ceil(log(children + 1))
                int log_children = 0;
                int pow = 1;
                while(pow < children + 1) { log_children++; pow *= 2; }

                num_days += log_children;
            }
        }
        return num_days;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] num_children = new int[n];
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br_in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            num_children[a - 1]++; num_children[b - 1]++;
        }
        br_in.close();
        return new Input(n, num_children);
    }

    public static int readOutput(int n, File file) throws FileNotFoundException, IOException {
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
    int[] num_children;
    Input(int n, int[] num_children) {
        this.n = n;
        this.num_children = num_children;
    }
}