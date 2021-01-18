import java.io.*;
import java.util.*;

public class Photoshoot {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Jan/Bronze/2-Photoshoot/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int[] expected_ans = readOutput(in.n, files.get(i + 1));
            int[] order = findOrder(in.n, in.nums);

            if (Arrays.equals(expected_ans, order)) {
                System.out.println("PASSED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2 + ":\n" + Arrays.toString(order) + "\n");
            } else {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2);
                System.out.println("  Expected:\n" + Arrays.toString(expected_ans));
                System.out.println("  Got:     \n" + Arrays.toString(order) + "\n");
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

    public static int[] findOrder(int n, int[] b) {
        int[] d = new int[n];
        int[] order = new int[n];
        boolean[] used = new boolean[n];
        for(int a = 1; a <= n;a++) {
            order[0] = a; order[1] = b[0] - a;
            for (int i = 2; i < n; i++)
                order[i] = order[i - 2] + d[i];
            for (int i = 1; i <= n; i++)
                used[i] = false;
            boolean bad = false;
            for (int i = 0; i < n; i++) {
                if(used[order[i]] || order[i] <= 0 || order[i] > n) {
                    bad = true;
                    break;
                }
                used[order[i]] = true;
            }
            if (!bad) break;
        }
        return order;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] nums = new int[n - 1];
        StringTokenizer st = new StringTokenizer(br_in.readLine());
        for (int i = 0; i < n - 1; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        br_in.close();
        return new Input(n, nums);
    }

    public static int[] readOutput(int n, File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        int[] expected_ans = new int[n];
        StringTokenizer st = new StringTokenizer(br_out.readLine());
        for (int i = 0; i < n; i++) {
            expected_ans[i] = Integer.parseInt(st.nextToken());
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
    int[] nums;
    Input(int n, int[] nums) {
        this.n = n;
        this.nums = nums;
    }
}