import java.io.*;
import java.util.*;

public class SwapitySwap {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Feb/Bronze/2-MadScientist/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int expected_ans = readOutput(files.get(i + 1));
            int num_swaps = doSwaps(in.n, in.a, in.b);

            if (expected_ans != num_swaps) {
                num_failed++;
                System.out.println("FAILED TEST CASE " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + num_swaps + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TEST CASES (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TEST CASES");
        }
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

    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        char[] a = br_in.readLine().toCharArray();
        char[] b = br_in.readLine().toCharArray();
        br_in.close();
        return new Input(n, a, b);
    }

    public static int readOutput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        int expected_ans = Integer.parseInt(br_out.readLine());
        br_out.close();
        return expected_ans;
    }

    public static int getTestCase(File file) {
        String[] tokens = file.getPath().split("/");
        String filename = tokens[tokens.length - 1];
        return Integer.parseInt(filename.split("\\.")[0]);
    }

    public static int doSwaps(int n, char[] a, char[] b) {
        int num_swaps = 0;
        while(!new String(a).equals(new String(b))) {
            num_swaps++;
            int lhs = 0;
            while(a[lhs] == b[lhs]) lhs++;
            int rhs = n-1;
            while(a[rhs] == b[rhs]) rhs--;
            for(int i = lhs; i <= rhs; i++) {
                if(a[i] == 'G') a[i] = 'H';
                else a[i] = 'G';
            }
        }
        return num_swaps;
    }
}

class Input {
    int n;
    char[] a;
    char[] b;
    Input(int n, char[] a, char[] b) {
        this.n = n;
        this.a = a;
        this.b = b;
    }
}