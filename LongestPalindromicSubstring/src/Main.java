import java.util.Scanner;

public class Main {

public String longestPalindrome(String s) {
        
		//this is for the initial phase. 
        String pal = "";
        //if the string is more than 0 length, than the first char is the minimum Palindrome 
        if(s.length() > 0) {
            pal = Character.toString(s.charAt(0));
        }
        
        //loops for pivot i from index 0 to the length - 1 of the string
        for(int i = 0; i < s.length(); i++) {
            
            int pivot = i;
            
            //gets the index next to the pivot i with the same char
            int nextIndex = s.indexOf(s.charAt(i), pivot + 1);
            while(nextIndex != -1) {
                /*
                 * find the substring that starts and ends with the same char of the pivot index i. 
                 * This is to not have to compare every single substring for a Palindrome.
                 * 
                 */
                String subS = s.substring(i, nextIndex + 1);
                
                boolean pand = true;
                /*
                 * the left pivot (j) checks if the right pivot (opposite) is the same, 
                 * otherwise the substring is not a Palindrome
                 */
                for(int j = 1; j < subS.length()/2; j++) {
                    if(subS.charAt(j) != subS.charAt(subS.length() - 1 - j) ) {
                        pand = false;
                    }
                }
                //checks if the the length of the Palindrome substring is less than the current longest Palindrome
                //if true, then the new substring replaces the old substring
                if (pand == true) {
                    if(pal.length() < subS.length()) {
                        pal = subS;
                    }
                }
                //gets the next index with the same char of the pivot i
                nextIndex = s.indexOf(s.charAt(i), nextIndex + 1);               
            }
        }
        
        
        
        return pal;
    }
	
	public void run() {
		//user input
		System.out.println("Type in a string to find the Longest Palindrome:");
		Scanner scan = new Scanner(System.in);
		String string = scan.nextLine();
		
		System.out.println(this.longestPalindrome(string));
		scan.close();
	}

}
