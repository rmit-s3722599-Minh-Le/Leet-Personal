package exceptions;
/*
 * Class: BorrowingException
 * Description: The class represents a custom error
 * 				specifically when Returning an
 * 				item goes wrong
 * 				i.e difference days between Borrow Date
 * 				and Return Date cannot be negative or less
 * 				than 0.
 * Author: Minh_Le-s3722599
 */

//exception for returning item
public class ReturnException extends Exception {

	public ReturnException(String message) {
		super(message);

	}

}
