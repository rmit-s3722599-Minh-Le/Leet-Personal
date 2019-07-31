package application;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import dateandtime.DateTime;
/*
 * Class: Hiring Record
 * Description: The class represents a single hiring record
 * 				for any type of item can be hired
 * Author: Minh_Le-s3722599
 */
			

public class HiringRecord {

	//attributes that will contain data for the toString and getDetails
	private String id;
	private double rentalFee;
	private double lateFee;
	private DateTime borrowDate;
	//return date starts as null as the created item cannot be returned
	private DateTime returnDate = null;
	//used for formatting doubles into 2 decimal places
	NumberFormat formatter = new DecimalFormat("#0.00");
	
	//constructor to add data to relative attributes
	public HiringRecord(String id, String memberId, DateTime borrowDate,
			double rentalFee) {
		this.rentalFee = rentalFee;
		this.borrowDate = borrowDate;
		this.id = id + "_" + memberId + "_" + borrowDate.getEightDigitDate();
	}
	
	//movie and game class require the borrow date of the item
	//to calculate the difference between the borrow date and 
	//the return date
	public DateTime getBorrowDate() {
		return borrowDate;
	}
	
	//updates the fees and late fees in this class and returns
	//the total fee for the Game/Movie class
	public double returnItem(DateTime returnDate, double lateFee) {
		this.returnDate = returnDate;
		/*
		 *Calculates the total fee of the item*/
		this.lateFee = lateFee;
		return lateFee + rentalFee;
	}
	
	//required to update the relative variables to complete the hireHistory
	//restoration
	public void setOtherVariables(DateTime returnDate, double lateFee) {
		this.returnDate = returnDate;
		this.lateFee = lateFee;
	}
	
	
	//Returns string for the user to know the Hire History
	/*
	 * ALGORITHM
	 * BEGIN
	 * 
	 	*IF item has not been borrowed
	 		*USE borrow format
	 	*ELSE
	 		*USE return format
	 *RETURN STRING
	 *
	 *END
	 */
	public String getDetails() {
		//if return date doesn't exist yet, return String borrow format
		if (returnDate == null) {
			return String.format("%-25s %s\n", "",
					String.format("%-25s %s", "Hire Id:", id))
					+ String.format("%-25s %s\n", "", String.format("%-25s %s",
							"BorrowDate:", borrowDate.getFormattedDate()));
		} else {
			//if return date doesn't exist yet, return String return format
			return String.format("%-25s %s", "",
					String.format("%-25s %s\n", "Hire Id:", id))
					+ String.format("%-25s %s", "",
							String.format("%-25s %s\n", "BorrowDate:",
									borrowDate.getFormattedDate()))
					+ String.format("%-25s %s", "",
							String.format("%-25s %s\n", "ReturnDate:",
									returnDate.getFormattedDate()))
					+ String.format("%-25s %s", "",
							String.format("%-25s %s\n", "Fee:",
									"$" + formatter.format(rentalFee)))
					+ String.format("%-25s %s", "",
							String.format("%-25s %s\n", "LateFee:",
									"$" + formatter.format(lateFee)))
					+ String.format("%-25s %s", "", String.format("%-25s %s\n",
							"TotalFee:",
							"$" + formatter.format(lateFee + rentalFee)));
		}

	}
	
	//returns string in a format for file to be written and read
		/*
		 * ALGORITHM
		 * BEGIN
		 * 
		 	*IF item has not been borrowed
		 		*USE borrow format
		 	*ELSE
		 		*USE return format
		 *RETURN STRING
		 *
		 *END
		 */
	public String toString() {
		
		//if return date doesn't exist yet, return borrow format
		if (returnDate == null) {
			return id + ":" + borrowDate.getEightDigitDate() + ":" + "none"
					+ ":" + "none" + ":" + "none";
		//if return date does exist, return return format
		} else {
			return id + ":" + borrowDate.getEightDigitDate() + ":"
					+ returnDate.getEightDigitDate() + ":"
					+ formatter.format(rentalFee) + ":"
					+ formatter.format(lateFee);
		}
	}

}
