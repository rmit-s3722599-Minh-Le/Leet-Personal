import java.util.Scanner;

public class Main {
	
    public boolean isPalindrome(int x) {
        
    	//length of number
        int length = (int)(Math.log10(x)+1);
        //detects negative integer
        if (x < 0) return false;
        
        //left and right digits are compared until the centre is reached.
        for(int i = 0; i < length/2; i++) {
        	//looks at right digit
            int rightdigit = (x/(int)Math.pow(10,i))%10;
            //looks at left digit
            int leftdigit = (x/(int)Math.pow(10,length -1- i))%10;
            System.out.println(leftdigit);
            //false if not match
            if(leftdigit != rightdigit) {
                return false;
            }
        }
        //true if it all passed
        return true;
    }

	public void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Type in a number:");
		boolean pass = false;
		while(!pass) {
			try {
				int a = Integer.valueOf(sc.nextLine());
				System.out.println(this.isPalindrome(a));
				pass = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Incorrect Input, try again..");
			}
		}
		sc.close();
		
	}

}
