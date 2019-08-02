
public class Main {

/*
 * 	Solution1: when the longest substr is at the starting index of 0
 */

//    public int lengthOfLongestSubstring(String s) {
//       String str = ""; 
//       boolean done = false;
//       for(int i = 0; i < s.length(); i++) {
//    	   if(!done) {
//         	   char a = s.charAt(i);
//
//        	   for(int j = 0; j < i; j++) {
//             	   if(a == s.charAt(j)) {
//            		   done = true;
//            	   }
//        	   }
//        	   if(!done) {
//        		   str += a;
//        	   }
//    	   }
//       }
//       System.out.println(str);
//       return str.length();
//    }
    
	
	//solution2
    public int lengthOfLongestSubstring(String s) {
    	String longestStr = "";
        for(int k = 0; k < s.length(); k++) {
            boolean done = false;
            String str = "";
            for(int i = k; i < s.length(); i++) {
         	   if(!done) {
              	   char a = s.charAt(i);
             	   for(int j = k; j < i; j++) {
                  	   if(a == s.charAt(j)) {
                 		   done = true;
                 	   }
             	   }
             	   if(!done) {
             		   str += a;
             	   }
         	   }
            }
            if(longestStr.length() < str.length()) {
            	longestStr = str;
            }
//            if(longestStr.length() < s.length()/2) {
//            	
//            }
        }
        System.out.println(longestStr);
        return longestStr.length();
     }
    
	public void run() {
		// TODO Auto-generated method stub
		int len = this.lengthOfLongestSubstring("abcabcbb");
		System.out.println(len);
	}

}
