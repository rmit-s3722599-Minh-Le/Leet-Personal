package application;
/*import java.util.*;
import java.io.*;*/
import java.text.DecimalFormat;
import java.text.NumberFormat;

import dateandtime.DateTime;
import exceptions.BorrowingException;
import exceptions.IdException;
import exceptions.ReturnException;
/*
 * Class: Item
 * Description: The class represents a single item, the parent of
 * 				the Game and Movie class. The Game and Movie will
 *				inherit this class. 		
 * Author: Minh_Le-s3722599
 */
public abstract class Item {
	// fields/attributes

	// these attributes with information stored will be added for getDetails for
	// printing out the Item Data
	private String id;
	private String title;
	private String description;
	private String genre;
	protected double fee;

	// Stores a collection (10) of hireHistory
	private HiringRecord[] hireHistory = new HiringRecord[10];

	// User's Current Data that will be added to hireHistory array
	protected HiringRecord currentlyBorrowed;

	// String of specific data depending on the type of item
	// (Used for getDetails)
	protected String addType;
	// (Used for toString)
	protected String itemElement = "";

	// no. of days for renting the specific item
	protected int rentalPeriod;

	//attributes that define the status of the variables
	// the Items will not be on loan from the very start they are created
	protected boolean boolCurrentBorrowed = false;
	protected String loanStatus = "NO";
	protected String abbLoanStatus = "N";

	// new object created to format the doubles (the costs) to 2 decimal places
	NumberFormat formatter = new DecimalFormat("#0.00");
	// private String movieOrGameSymbol;

	// constructor that updates the relative attributes
	public Item(String id, String title, String genre, String description)
				throws IdException {

		if (id.length() == 5) {
			this.id = id;
			this.title = title;
			this.description = description;
			this.genre = genre;
		}

		else {
			throw new IdException("ERROR: 3 DIGIT ID IS NEEDED");
		}

	}

	// used for MovieMaster..gives the id to MovieMaster for comparison with the
	// input of the user
	public String getId() {
		return id;
	}

	// used for MovieMaster.. gives the title to MovieMaster to print out the
	// title
	public String getTitle() {
		return title;
	}

	// MovieMaster can check if the movie is on loan or not if this is true(on
	// loan) or false(not on loan)
	public boolean getBoolCurrentBorrowed() {
		return boolCurrentBorrowed;
	}

	/*AlGORITHM
	 * BEGIN
	 * 
	 * CHECKS if item is borrowed or not 
	    * IF SO.. NEW Hiring Record object is created SHIFT hireHistory by 1 
	 * ADD object to array 0 
	 * BORROW becomes true
	    * ELSE exception is activated
	    * 
	 *END 
	 */
	public double borrow(String memberId, DateTime borrowDate)
				throws BorrowingException {
		//shifting the data up by 1
		if (boolCurrentBorrowed == false) {

			currentlyBorrowed = new HiringRecord(id, memberId, borrowDate, fee);
			for (int i1 = 8; i1 >= 0; i1--) {
				hireHistory[i1 + 1] = hireHistory[i1];
			}
			hireHistory[0] = currentlyBorrowed;

			boolCurrentBorrowed = true;

		}
		//exception
		else {
			throw new BorrowingException("ERROR: ITEM is currently on Loan..");
		}

		return fee;
	}

	// Movie and Game class will need this method (to return). Each class has
	// different calculations for the lateFee
	public abstract double returnItem(DateTime returnDate)
				throws ReturnException;
	
	//adds all the hireHistory data of the array to the hireData, and returns it
	public String getHiringRecordDetails() {
		String hireData = "";
		for (int i = 0; i < hireHistory.length; i++) {
			if (hireHistory[i] != null) {
				hireData += "\n" + hireHistory[i];

			}
		}
		hireData = hireData.replaceAll("(?m)^[ \t]*\r?\n", "");
		return hireData;
	}

	//method where string data from file is converted to the right attributes
	//and the attributes containing data are then added to a new Hiring Record
	//(with all of the past data). Continues until the item has all of the 
	//hiring records
	public void setHiringRecordData(String id, String borrowDate,
				String returnDate, String fee, String lateFee) {
		
		//conversion of the string to the attributes
		String newId = id.substring(0, 5);
		String memberId = id.substring(6, id.length() - 9);
		String stringBorrowDay = borrowDate.substring(0, 2);
		String stringBorrowMonth = borrowDate.substring(2, 4);
		String stringBorrowYear = borrowDate.substring(4, borrowDate.length());
		int borrowDay = Integer.parseInt(stringBorrowDay);
		int borrowMonth = Integer.parseInt(stringBorrowMonth);
		int borrowYear = Integer.parseInt(stringBorrowYear);
		DateTime borrowDate1 = new DateTime(borrowDay, borrowMonth, borrowYear);
		
		//condition if the item has been returned via the Hiring Record data
		if (!returnDate.equals("none")) {
			//conversion of the return date attributes to recreate
			//the return date
			String stringReturnDay = returnDate.substring(0, 2);
			String stringReturnMonth = returnDate.substring(2, 4);
			String stringReturnYear = returnDate.substring(4,
						borrowDate.length());

			int returnDay = Integer.parseInt(stringReturnDay);
			int returnMonth = Integer.parseInt(stringReturnMonth);
			int returnYear = Integer.parseInt(stringReturnYear);
			
			//conversion of the fees
			double doubleFee = Double.parseDouble(fee);
			double doubleLateFee = Double.parseDouble(lateFee);
			
			//recreation of the return date
			DateTime returnDate1 = new DateTime(returnDay, returnMonth,
						returnYear);
			/*
			 *Searches for an empty slot from the hireHistory array. Creates the 
			 *new HiringRecord and adds it to that slot*/
			for (int i = 0; i < hireHistory.length; i++) {
				if (hireHistory[i] == null) {
					currentlyBorrowed = new HiringRecord(newId, memberId,
								borrowDate1, doubleFee);
					hireHistory[i] = currentlyBorrowed;
					hireHistory[i].setOtherVariables(returnDate1,
								doubleLateFee);
					currentlyBorrowed = hireHistory[0];
					break;
				}

			}

		} 
		//condition if the item has not been returned via the Hiring Record data
		/*
		 *Searches for an empty slot from the hireHistory array. Creates the 
		 *new HiringRecord and adds it to that slot*/
		else {
			
			for (int i = 0; i < hireHistory.length; i++) {
				if (hireHistory[i] == null) {
					currentlyBorrowed = new HiringRecord(newId, memberId,
								borrowDate1, this.fee);
					hireHistory[i] = currentlyBorrowed;
					currentlyBorrowed = hireHistory[0];

					break;
				}
			}
		}
	}
	
	//for displaying results data towards 'Display Details' option in 
	//MovieMaster
	public String getDetails() {

		String outputData = String.format("%-25s %s\n", "ID:", id)
					+ String.format("%-25s %s\n", "Title", title)
					+ String.format("%-25s %s\n", "Genre:", genre)
					+ String.format("%-25s %s\n", "Description:", description)
					+ String.format("%-25s %s\n", "Standard Fee:",
								"$" + formatter.format(fee))
					+ String.format("%-25s %s\n", "On Loan:", loanStatus)
					+ addType
					+ String.format("%-25s %s\n", "Rental Period:",
								rentalPeriod + " days")
					+ String.format("%-15s %s\n", "",
								"-----------------BORROWING RECORD--------------------");
		/*Adds a specific string to the item data only to items with no hiring record
		 *to show that the item has no hiring record */
		if (hireHistory[0] == null) {
			return outputData + String.format("%-25s %s\n", "", "NONE");

		}

		else {
			/*Adds all of the Hiring Record of the items (that has at least 1
			 * hiring record under the item data*/
			for (int i = 0; i < hireHistory.length; i++) {

				if (hireHistory[i] != null) {
					outputData += hireHistory[i].getDetails() + "\n"
								+ String.format("%-15s %s\n", "",
											"---------------------------------------");

				}

			}
			return outputData;
		}

	}

	
	//attributes will be written in a format to a string to be written off to the text file
	//in order to be read and recover the item data when reboot
	public String toString() {

		return id + ":" + title + ":" + description + ":" + genre + ":" + fee
					+ ":" + itemElement + ":" + abbLoanStatus;
	}

}
