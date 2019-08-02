package application;

import dateandtime.DateTime;
import exceptions.IdException;
import exceptions.ReturnException;

/*
 * Class: Game
 * Description: The class represents a single item, specifically the item
 * 				game containing an array of Hiring Records (max 10 HR).
 * 				This Class is a subclass of Item
 * Author: Minh_Le-s3722599
 */
public class Game extends Item {
	private String[] platforms;
	private boolean extended = false;
	private String platformList = "";
	private double lateFees;

	//constructor that updates the relative attributes
	public Game(String id, String title, String genre, String description,
				String[] platforms) throws IdException {
		super("G_" + id, title, genre, description);
		this.platforms = platforms;
		/*
		 * transforms the array of platforms to a format
		 */
		for (int i = 0; i < platforms.length; i++) {
			if (platforms[i] != null) {
				if (i == platforms.length - 1) {
					break;
				} else {
					platformList += platforms[i] + ", ";
				}
			}

		}
		if (platforms[platforms.length - 1] != null) {
			platformList += platforms[platforms.length - 1];
		}
		//fees of any Game is set to $2.00
		fee = 2.00;

	}
	//setter for extended (to set the item to extended)
	public void setExtended(boolean value) {
		extended = value;
	}
	
	/*
	 * updates attributes relative to attributes and determines 
	 * if the items are on loan or not
	 */
	public void setOtherVariables(double fee, String loanStatus) {
		this.fee = fee;
		if (loanStatus.equals(":Y")) {
			boolCurrentBorrowed = true;
			extended = false;
		} else if (loanStatus.equals(":N")) {
			boolCurrentBorrowed = false;
			extended = false;
		} else {
			boolCurrentBorrowed = true;
			extended = true;
		}

	}

	@Override
	public double returnItem(DateTime returnDate) throws ReturnException {
		//calculating days of loan
		int difference = DateTime.diffDays(returnDate,
					currentlyBorrowed.getBorrowDate());
		/*if days of loan is less than 0, 
		 *exception is thrown to the MovieMaster
		 */
		if (difference <= 0) {
			//throws to MovieMaster
			throw new ReturnException("ERROR: RETURN DAY input is invalid");
		}
		//Otherwise lateFee is calculated
		
		//Condition for loan is more than 14 days
		/*
		 * ALGORITM
		 * BEGIN
		 * 
		 * IF loan days is more than 14
		 	*ITEM is returned
		 	*LATEFEES =
		 	*(1.00 times (loan days minus 14) + $5 Extra per 7 days)	
		 	*IF EXTENDED
		 		*LATEFEE is cut by 50%
		 	*RETURN TOTAL FEE
		 *ELSE (loan is between 0 and 14 days)
		 	*ITEM is returned
		 	*LATEFEES = 0
		 	*RETURN TOTAL FEE
		 	*
		 	*END
		 	*
		 	*TEST
		 	*IF LOAN DAYS IS 12
		 	*RETURNED TOTAL FEE =  $2
		 	*
		 	*IF LOAN DAYS IS 15
		 	*RETURN TOTAL FEE = $3
		 	*
		 	*IF LOAN DAYS IS 22
		 	*RETURN TOTAL FEE = $15
		 */
		else if (difference > 14) {
			boolCurrentBorrowed = false;
			lateFees = (1.00 * (difference - 14) + (difference - 14) / 7 * 5);
			// extended items are a 50% discount on lateFees
			if (extended) {
				lateFees = lateFees / 2;
				extended = false;

			}

			return currentlyBorrowed.returnItem(returnDate, lateFees);

		}
		
		//condition for loan is less than 14 days
		else {
			boolCurrentBorrowed = false;
			extended = false;
			lateFees = 0;
			return currentlyBorrowed.returnItem(returnDate, lateFees);
		}

	}

	@Override
	/*
	 * updates unique characteristics from getDetails of parent
	 * and returns the update String (override)
	 */
	public String getDetails() {
		rentalPeriod = 14;
		if (boolCurrentBorrowed) {
			loanStatus = "YES";
		} else {
			loanStatus = "NO";
		}
		if (extended) {
			loanStatus = "EXTENDED";
		}

		addType = String.format("%-25s %s\n", "Platform: ", platformList);

		return super.getDetails();

	}

	@Override
	/*
	 * updates unique characteristics from toString of parent
	 * and returns the update String (override)
	 */
	public String toString() {
		if (boolCurrentBorrowed) {
			abbLoanStatus = "Y";

		} else {
			abbLoanStatus = "N";

		}
		if (extended) {
			abbLoanStatus = "E";
		}

		itemElement = platformList;

		return super.toString();

	}

}
