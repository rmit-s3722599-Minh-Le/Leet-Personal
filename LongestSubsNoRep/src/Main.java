
public class Main {

	
	
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
    
    public int lengthOfLongestSubstring(String s) {
        String str = ""; 
        boolean done = false;
        for(int i = 0; i < s.length(); i++) {
     	   if(!done) {
          	   char a = s.charAt(i);

         	   for(int j = 0; j < i; j++) {
              	   if(a == s.charAt(j)) {
             		   done = true;
             	   }
         	   }
         	   if(!done) {
         		   str += a;
         	   }
     	   }
        }
        System.out.println(str);
        return str.length();
     }
    
	public void run() {
		// TODO Auto-generated method stub
		int len = this.lengthOfLongestSubstring("abcda");
		System.out.println(len);
	}

}
