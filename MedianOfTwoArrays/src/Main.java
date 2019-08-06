import java.util.ArrayList;
import java.util.Collections;

public class Main {
	    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
	        //combine two arrays to one array
	        //Order the nums in array
	        //If no. is even
	            //FInd average
	        //Else find middle
	        
	        //lazy sort method
	        //add those two arrays to an ArrayList
	        ArrayList<Integer> list = new ArrayList<Integer>();
	        for(int num: nums1) {
	            list.add(num);
	        }
	        for(int num:nums2) {
	            list.add(num);
	        }
	        //using collection.sort() uses a quick sort dual pivoting type of sort
	        Collections.sort(list);
	        
	        double med;
	        //if odd
	        if(list.size()%2 == 1) {
	            med = (double) list.get(list.size()/2);
	        }
	        
	        else {
	            med = ((double)list.get(list.size()/2 - 1) + (double)list.get(list.size()/2))/2;
	        }
	        return med;
	    }
	
	public void run() {
		int[] a = {1,2,3,4,5,6};
		int[] b = {1,2,3,4,5,6};
		
		System.out.println(this.findMedianSortedArrays(a, b));
		
	}

}
