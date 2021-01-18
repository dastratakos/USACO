import java.util.*;

public class ABCs {
    public static void main(String[] args) {
        int[] nums = new int[7];
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 7; i++) {
            nums[i] = input.nextInt();
        }
        input.close();

        Arrays.sort(nums);
        System.out.println(nums[0] + " " + nums[1] + " " + (nums[6] - nums[0] - nums[1]));
    }
}