import java.io.*;
import java.util.*;

public class ABCs {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Bronze/1-ABCs/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            int[] nums = readInput(files.get(i));
            int[] expected_ans = readOutput(files.get(i + 1));
            int[] abc = findABC(nums);

            if (!Arrays.equals(expected_ans, abc)) {
                num_failed++;
                System.out.println("FAILED TEST CASE " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + abc + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TEST CASES (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TEST CASES");
        }
    }

    public static int[] findABC(int[] nums) {
        Arrays.sort(nums);
        return new int[]{nums[0], nums[1], nums[6] - nums[0] - nums[1]};
    }

    /* ********** UTILITIES ********** */
    public static int[] readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int[] nums = new int[7];
        StringTokenizer st = new StringTokenizer(br_in.readLine());
        for(int i = 0; i < 7; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        br_in.close();
        return nums;
    }

    public static int[] readOutput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        int[] expected_ans = new int[3];
        StringTokenizer st = new StringTokenizer(br_out.readLine());
        for(int i = 0; i < 3; i++) {
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