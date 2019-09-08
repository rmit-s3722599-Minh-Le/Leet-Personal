
public class MaxArea {
	
	private final int[] height = {1,3,6,7,3,7,4,1,9};
	//complexity of O(n^2)
	    public int maxArea(int[] height) {
	        int biggest = 0;
	        for(int i = 0; i < height.length - 1; i++) {
	            for(int j = i + 1; j < height.length; j++) {
	                int minHeight = height[i];
	                if(minHeight > height[j]) {
	                    minHeight = height[j];
	                }
	                int area = minHeight*(j - i);
	                if(biggest < area) {
	                    biggest = area;
	                }
	            }
	        }
	        return biggest;
	    }
	    
	    public void run() {
	    	System.out.println(maxArea(height));
	    }
	    
}
