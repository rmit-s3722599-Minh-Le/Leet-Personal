package number_to_string;
import java.io.*;
import java.util.Scanner;
public class numtostring
{
	
	public static String ones[]={"one","two","three","four","five","six"," seven", "eight","nine","ten","eleven","twelve","thirteen","forteen","fifteen","sixteen","seventeen","eighteen","ninteen"};      
        public static String tens[]={"twenty","thirty","fourty","fifty","sixty","seventy","eighty","ninty"};       
public static void main(String a[]) throws Exception
	{
        Scanner sc=new Scanner(System.in);
        int num,rem_tens,rem_ones; 
        String number;
        
      
         System.out.println("Enter the number:");
          num=sc.nextInt();
         System.out.println("Entered number is:");
      /*write down your logic here*/
       rem_ones = num%10;
       rem_tens = num/10;
       if (rem_tens  == 1) {
           number  = ones[rem_ones - 1 + 10];
       }
       else if (rem_tens == 0) {
           number = ones[rem_ones - 1];
       }
       
       else {
           if (rem_ones == 0) {
               number = tens[rem_tens - 2];
           }
           else {
               number = tens[rem_tens - 2] + " " + ones[rem_ones - 1];
           }
       }
	    System.out.println(number);
        }//main 


	 
}  
  