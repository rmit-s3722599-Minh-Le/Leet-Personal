package exceptions;
/*
 * Class: BorrowingException
 * Description: The class represents a custom error
 * 				specifically when a user input ID
 * 				goes wrong (if the length of the id
 * 				exceeds 3 characters)
 * Author: Minh_Le-s3722599
 */

//exception specifically for wrong id input
public class IdException extends Exception {

	public IdException(String message) {
		super(message);
	}

}
