import java.io.*;
import java.util.*;

public class WordProcessor {
    public static void main(String[] args) throws IOException {
        String test_dir = "./2020-Jan/Bronze/1-WordProcessor/tests/";
        List<File> files = listDir(new File(test_dir));

        int num_failed = 0;
        for (int i = 0; i < files.size(); i += 2) {
            Input in = readInput(files.get(i));
            String[] expected_ans = readOutput(files.get(i + 1));
            String[] formatted = formatEssay(in.n, in.k, in.words);

            if (Arrays.equals(expected_ans, formatted)) {
                System.out.println("PASSED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2 + ":\n" + Arrays.toString(formatted) + "\n");
            } else {
                num_failed++;
                System.out.println("FAILED TEST " + getTestCase(files.get(i)) + 
                    "/" + files.size() / 2);
                System.out.println("  Expected:\n" + Arrays.toString(expected_ans));
                System.out.println("  Got:     \n" + Arrays.toString(formatted) + "\n");
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

    public static String[] formatEssay(int n, int k, String[] words) {
        List<String> essay = new ArrayList<String>();
        String currLine = "";
        int length = 0; // current length of line
		for (String word : words) {
            if ((length + word.length()) > k) {
                essay.add(currLine);
                currLine = word;
                length = word.length();
            } else {
                if (length > 0) {
                    currLine = currLine + " ";
                }
                currLine = currLine + word;
                length += word.length();
            }
        }
        essay.add(currLine);

        // convert from ArrayList to Array
        String[] retEssay = new String[essay.size()]; 
        retEssay = essay.toArray(retEssay); 
        return retEssay;
    }

    /* ********** UTILITIES ********** */
    public static Input readInput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_in = new BufferedReader(new FileReader(file));

        StringTokenizer st = new StringTokenizer(br_in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        String[] words = new String[n];
        st = new StringTokenizer(br_in.readLine());
        for (int i = 0; i < n; i++) {
            words[i] = st.nextToken();
        }
        br_in.close();
        return new Input(n, k, words);
    }

    public static String[] readOutput(File file) throws FileNotFoundException, IOException {
        BufferedReader br_out = new BufferedReader(new FileReader(file));
        List<String> essay = new ArrayList<String>();
        String currLine;
        while ((currLine = br_out.readLine()) != null) {
            essay.add(currLine);
        }
        String[] expected_ans = new String[essay.size()]; 
        expected_ans = essay.toArray(expected_ans); 
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
    int k;
    String[] words;
    Input(int n, int k, String[] words) {
        this.n = n;
        this.k = k;
        this.words = words;
    }
}