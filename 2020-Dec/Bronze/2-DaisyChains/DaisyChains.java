import java.io.*;
import java.util.*;

public class DaisyChains {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Dec/Bronze/2-DaisyChains/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            int expected_ans = readOutput(files.get(i + 1));
            int num_photos = countPhotos(in.n, in.petals);
            
            if (expected_ans != num_photos) {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)));
                System.out.println("Expected: " + expected_ans);
                System.out.println("Got:      " + num_photos + "\n");
            }
        }

        if (num_failed == 0) {
            System.out.println("PASSED ALL TESTS (" + files.size() / 2 + "/" + files.size() / 2 + ")");
        } else {
            System.out.println("FAILED " + num_failed + "/" + files.size() / 2 + " TESTS");
        }
    }

    public static int countPhotos(int n, int[] petals) {
        int num_photos = 0;
        for (int i = 0; i < n; i++) {
            boolean[] present = new boolean[1001];
            int petalsSeen = 0;
            for (int j = i; j < n; j++) {
                petalsSeen += petals[j];
                present[petals[j]] = true;
                if (petalsSeen % (j - i + 1) == 0 && present[petalsSeen / (j - i + 1)]) {
                    num_photos++;
                }
            }
        }
        return num_photos;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br_in.readLine());
        int[] petals = new int[n];
        StringTokenizer st = new StringTokenizer(br_in.readLine());
        for (int i = 0; i < n; i++) {
            petals[i] = Integer.parseInt(st.nextToken());
        }
        br_in.close();
        return new Input(n, petals);
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
    int[] petals;
    Input(int n, int[] petals) {
        this.n = n;
        this.petals = petals;
    }
}