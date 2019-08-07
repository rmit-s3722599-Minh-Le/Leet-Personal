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
                 * otherwise the substring is not a palindrome
                 */
                for(int j = 1; j < subS.length()/2; j++) {
                    if(subS.charAt(j) != subS.charAt(subS.length() - 1 - j) ) {
                        pand = false;
                    }
                }
                if (pand == true) {
                    if(pal.length() < subS.length()) {
                        pal = subS;
                    }
                }
                nextIndex = s.indexOf(s.charAt(i), nextIndex + 1);               
            }
        }
        
        
        
        return pal;
    }
	
	public void run() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String string = scan.nextLine();
		System.out.println(this.longestPalindrome(string));
		scan.close();
	}

}
