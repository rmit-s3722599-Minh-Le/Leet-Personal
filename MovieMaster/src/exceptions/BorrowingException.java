package exceptions;
/*
 * Class: BorrowingException
 * Description: The class represents a custom error
 * 				specifically when Borrowing an
 * 				item goes wrong 
 * 				i.e. when trying to borrow an item
 * 				currently on loan
 * Author: Minh_Le-s3722599
 */

//exception specifically for borrowing item
public class BorrowingException extends Exception {

	public BorrowingException(String message) {
		super(message);

	}

}