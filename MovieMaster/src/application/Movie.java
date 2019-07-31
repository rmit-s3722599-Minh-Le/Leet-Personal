package application;

import dateandtime.DateTime;
import exceptions.IdException;
import exceptions.ReturnException;

/*
 * Class: Movie
 * Description: The class represents a single item, specifically the item
 * 				movie containing an array of Hiring Records (max 10 HR).
 * 				This Class is a subclass of Item
 * Author: Minh_Le-s3722599
 */

public class Movie extends Item {

	private Boolean isNewRelease;
	private final double NEW_RELEASE_SURCHARGE = 2.00;
	private double lateFee;
	private String movieType;

	//constructor for movie relative to the attributes
	public Movie(String id, String title, String genre, String description,
				Boolean isNewRelease) throws IdException {
		super("M_" + id, title, genre, description);

		/*fees and rental period is updated
		 * depending if the movie is a new release or not
		 */
		fee = 3.00;
		this.isNewRelease = isNewRelease;
		if (isNewRelease) {
			fee = fee + NEW_RELEASE_SURCHARGE;
			rentalPeriod = 2;
		}

		else {
			rentalPeriod = 7;
		}

	}
	
	
	public double returnItem(DateTime returnDate) throws ReturnException {
		//calculating days of loan
		int difference = DateTime.diffDays(returnDate,
					currentlyBorrowed.getBorrowDate());
		//if days of loan is <0 , exception is thrown to the MovieMaster
		if (difference <= 0) {
			throw new ReturnException("ERROR: RETURN DAY input is invalid");
		}
		//if the input is done right for returnDate
		else {
			boolCurrentBorrowed = false;
			/*
			 * lateFees is calculated depending if item is a new release
			 * or not as well as the days of loan
			 */
			
			/*
			 * BEGIN
			 * 
			 * IF Item is new Release
			 	*IF not past due date
			 		*$0.00 late fee
			 	*ELSE
			 		*LATE FEE: days of loan minus 2, times fee, divide by 2
			 *ELSE
			 	*IF not past due date
			 		*$0.00 late fee
			 	*ELSE
			 	    *LATE FEE: days of loan minus 7, times fee, divide by 2
			 *RETURN TOTAL FEE
			 *
			 *END
			 *
			 *TEST
			 	*isNewRelease = true,  difference = 1
			 		*return $5.00
			 	*isNewRelease = false, difference - 3
			 		*return $3.00
			 */
			if (isNewRelease) {
				if (difference <= 2) {
					lateFee = 0.00;
				} else {
					lateFee = (difference - 2) * fee * 0.50;
				}

			} else {
				if (difference <= 7) {
					lateFee = 0.00;
				} else {
					lateFee = (difference - 7) * fee * 0.50;
				}
			}
			return currentlyBorrowed.returnItem(returnDate, lateFee);
		}

	}

	/*
	 * updates attributes relative to attributes and determines 
	 * if the items are on loan or not
	 */
	
	public void setOtherVariables(double fee, String itemElement,
				String loanStatus) {
		this.fee = fee;

		if (loanStatus.equals(":Y")) {
			boolCurrentBorrowed = true;
		}

		else {
			boolCurrentBorrowed = false;
		}

	}

	@Override
	/*
	 * updates unique characteristics from getDetails of parent
	 * and returns the update String (override)
	 */
	public String getDetails() {
		if (boolCurrentBorrowed) {
			loanStatus = "YES";
		} else {
			loanStatus = "NO";
		}
		if (isNewRelease) {
			movieType = "NEW RELEASE";
			addType = String.format("%-25s %s\n", "Movie type: ", movieType);
		}

		else {
			movieType = "WEEKLY";
			addType = String.format("%-25s %s\n", "Movie type: ", movieType);
		}
		return super.getDetails();
	}

	@Override
	/*
	 * updates unique characteristics from toString of parent
	 * and returns the update String (override)
	 */
	public String toString() {
		String abb_LoanType;
		if (boolCurrentBorrowed) {
			abbLoanStatus = "Y";

		} else {
			abbLoanStatus = "N";

		}

		if (isNewRelease) {
			abb_LoanType = "NR";
		} else {
			abb_LoanType = "WK";
		}
		itemElement = abb_LoanType;

		return super.toString();
	}

}
