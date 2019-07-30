import java.util.Arrays;

public class Main {
	
    public int[] twoSum(int[] nums, int target) {
        int[] answer = new int[2];
    	int INIT = 0;
        
		for (int i = INIT; i < nums.length; i++) {
			int sec = i + 1;
			for(int j = sec; j < nums.length; j++) {
				if(nums[i] + nums[j] == target) {
					answer[0] = nums[i];
					answer[1] = nums[j];
					//exits
					//
					i = nums.length;
					j = nums.length;
				}
			}
		}	
		return answer;
    }

    
    public void run() {
    	int[] ar = {1,2,3,4,5,6,7,8,9};
    	System.out.println(Arrays.toString(twoSum(ar,9)));
    }
	
}

