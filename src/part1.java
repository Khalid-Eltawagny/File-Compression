import java.util.HashMap;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Arrays;

public class part1 {

    private static HashMap<Integer, Integer> memo = new HashMap<>();

    private static int solve(int cur, int n, int[][] jobs) {
        if (cur == n || jobs == null)
            return 0;
        if (memo.containsKey(cur))
            return memo.get(cur);
        int next = findNext(jobs, cur);
        int pick = jobs[cur][2] + (next == -1 ? 0 : solve(next, n, jobs));
        int leave = solve(cur + 1, n, jobs);
        memo.put(cur, Math.max(pick, leave));
        return Math.max(pick, leave);
    }
    
    private static int findNext(int[][] jobs, int idx) {
        int l = idx + 1;
        int r = jobs.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (jobs[mid][0] >= jobs[idx][1]) {
                if (jobs[mid - 1][0] >= jobs[idx][1])
                    r = mid - 1;
                else
                    return mid;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    private static String generateOutputPath(String inputPath) {
        String p[] = inputPath.split("/");

        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < p.length - 1; i++) {
            output.append(p[i]);
            output.append("/");
        }
        
        String filename = p[p.length - 1].split("[.]")[0] + "_18010594.out";
        output.append(filename);
        return output.toString();
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Invalid number of arguments !!");
            return;
        }
        String inputPath = args[0];
        int n = 0; // total number of jobs
        int i = 0; // counter
        int[][] jobs = null;
        try {
            File file = new File(inputPath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data[] = myReader.nextLine().split(" ");
                if (data.length == 1) {
                    n = Integer.valueOf(data[0]);
                    jobs = new int[n][3];
                } else {
                    jobs[i] = new int[] { Integer.valueOf(data[0]), Integer.valueOf(data[1]),
                            Integer.valueOf(data[2]) };
                    i++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);
        String outputPath = generateOutputPath(inputPath);
        int res = solve(0, n, jobs);
        try {
            FileWriter myWriter = new FileWriter(outputPath);
            myWriter.write(String.valueOf(res));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
