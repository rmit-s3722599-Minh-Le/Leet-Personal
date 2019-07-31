package moviemaster_menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

import application.Game;
import application.Item;
import application.Movie;
import dateandtime.DateTime;
import exceptions.BorrowingException;
import exceptions.IdException;
import exceptions.ReturnException;
/*
 * Class: MovieMaster
 * Description: The class handles the functionality of the program Movie Master.
 * 				This class is also menu type class, which also takes care
 * 				of the User Interface.
 * Author: Minh_Le-s3722599
 */
public class MovieMaster {
	private String response;
	//boolean is forever true
	private final boolean IS_RUNNING = true;
	
	private boolean error = true;
	private String currentId;
	// Integer to Increment the item array in Adding in item
	private int counting = 0;

	// User input will be stored in these attributes and then implemented to the constructors
	private String title;
	private String genre;
	private String description;
	private String newRelease;
	private String memberId;
	private int advanceDateCounter;

	private boolean skip = true;

	// 50 items can be stored
	private Item[] item = new Item[50];

	//
	private Movie currentMovie;
	// no. of rental days of the item
	private int dueDayCounter; 
	/*
	 * the user input of id with the abbreviation of the item
	 * used to compare with the id of the items
	 */
	private String string;
	NumberFormat formatter = new DecimalFormat("#0.00");
	@SuppressWarnings("resource")
	Scanner console = new Scanner(System.in);

	public void run() {
		//loads file (if exists) when program starts
		loadItemFile();

		// Ensures that the program will forever run unless the user chooses to exit
		while (IS_RUNNING) {
			error = true;
			screen();
			response = console.next();
			
			//makes sure that user response is non case sensitive
			switch (response.toUpperCase()) {
			// adding in item option
			case "A":
				addItem();
				break;
			// borrow the item option
			case "B":
				borrowItem();
				break;
			// returning the borrowed item option
			case "C":
				returnItem();
				break;
			// display item option
			case "D":
				display();
				break;

			// hard-code option
			case "E":
				seed();
				break;

			// Exit Program option
			case "X":
				exitProgram();
				break;

			default:

			}
		}

	}
	
	//loads file (memory) from previous work
	private void loadItemFile() {
		/*
		 * ALGORITHM
		 * BEGIN
		 *IF "output.txt" is found
		 	*LOAD output.txt 
		 	*IF DATA IS ITEM
		 		*INITIALISE ITEM DATA TO VARIABLES
		 		*CREATE ITEM OBJECT TO ARRAY
		 		*ADD EXTRA DATA VIA METHOD
		 	*ELSE
		 		*INITIALISE Hire-Record DATA TO VARIABLES
		 		*CREATE ITEM OBJECT TO ARRAY
		 		*ADD EXTRA DATA VIA METHOD
		 *ELSE
		 	*LOADS backup
		 		*IF backup NOT FOUND
		 		*GOES TO MENU
		 *
		 *END
		 */
		//Loads file
		//EXCEPTIONS will skip the rest of this code and return to the menu
		Scanner inputStream = null;
		try {
			System.out.println("LOADING FILE...");
			inputStream = new Scanner(new File("output.txt"));
		} catch (FileNotFoundException e) {

			System.out.println("ITEM FILE CANNOT BE LOADED");
			try {
				System.out.println("LOADING BACKUP FILE..");
				inputStream = new Scanner(
							new File("backup_output.txt"));
			} catch (FileNotFoundException e1) {

				System.out.println("BACKUP FILE CANNOT BE LOADED\n");
				skip = false;

			}

		}
		//reads data and adds to variables
		while (skip) {
			inputStream.useDelimiter(":");
			int count = 0;
			try {
				while (inputStream.hasNextLine()) {
					//mysteryId is used to identify if the data is an item or not
					String mysteryId = inputStream.next();
					//If the data is an item data
					if ((mysteryId.length()) == 5) {
						String id = mysteryId;
						String title = inputStream.next();
						String description = inputStream.next();
						String genre = inputStream.next();
						String stringFee = inputStream.next();
						double fee = Double.parseDouble(stringFee);
						String itemElement = inputStream.next();
						String loanStatus = inputStream.nextLine();

						if (id.substring(0, 1).equals("M")) {
							if (itemElement == "NR") {
								try {
									item[count] = new Movie(id.substring(2, 5),
												title, description, genre,
												true);
									((Movie) item[count]).setOtherVariables(fee,
												itemElement, loanStatus);
									System.out.println("LOADING MOVIE: " + title
												+ " IS SUCCESSFUL");
									count += 1;
									skip = false;
								} catch (IdException e) {

									System.out.println(e.getMessage());
									System.out.println("ERROR Movie no."
												+ (count + 1)
												+ " cannot be added");
								}
							} else {
								try {
									item[count] = new Movie(id.substring(2, 5),
												title, description, genre,
												false);
									((Movie) item[count]).setOtherVariables(fee,
												itemElement, loanStatus);
									System.out.println("LOADING MOVIE: " + title
												+ " IS SUCCESSFUL");
									count += 1;
									skip = false;
								} catch (IdException e) {

									System.out.println(e.getMessage());
									System.out.println("ERROR Movie no."
												+ (count + 1)
												+ " cannot be added");
								}
							}

						}

						else {
							String platformArray[] = itemElement.split(", ");
							try {
								item[count] = new Game(id.substring(2, 5),
											title, genre, description,
											platformArray);
								((Game) item[count]).setOtherVariables(fee,
											loanStatus);
								System.out.println("LOADING GAME: " + title
											+ " IS SUCCESSFUL");
								count += 1;
								skip = false;
							} catch (IdException e) {

								System.out.println(e.getMessage());
								System.out.println("ERROR Game no."
											+ (count + 1) + " cannot be added");
							}

						}

					//if the data is the record history itself
					} else {
						count -= 1;
						String id = mysteryId;
						String borrowDate = inputStream.next();
						String returnDate = inputStream.next();
						String fee = inputStream.next();
						String lateFee = inputStream.nextLine();

						item[count].setHiringRecordData(id, borrowDate,
									returnDate, fee, lateFee.substring(1));
						count += 1;

					}
				}
			} catch (Exception e) {

				System.out.println("ERROR: ITEM CANNOT BE CREATED");

				skip = false;

			}

			System.out.println("DONE");
			skip = false;
		}
	}

	//saves progress and closes program
	private void exitProgram() {
		//Writes the data of each item in the file and the backup file
		//Writes the hireHistory respectfully of the item underneath the item data
		//Once finished, closes the program
		PrintWriter output = null;
		PrintWriter output1 = null;
		try {
			output = new PrintWriter(new FileOutputStream("output.txt"));
			output1 = new PrintWriter(
						new FileOutputStream("backup_output.txt"));

			for (int i = 0; i < item.length; i++) {
				if (item[i] != null) {
					output.println(item[i].toString());
					output1.println(item[i].toString());
					output.println(item[i].getHiringRecordDetails());
					output1.println(item[i].getHiringRecordDetails());
				}
			}
			System.out.println("FILE SAVED");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR..FILE NOT FOUND");
			System.out.println(e);

		}

		System.out.println("\n**END**");
		output.close();
		output1.close();
		System.exit(0);
	}

	//hard-coded items
	private void seed() {
		//proceeds if no items exists in the item array
		if (item[0] == null) {
			// weekly movies hard-coded
			try {
				item[0] = new Movie("MOS", "Man of Steel", "Action",
							"Clark Kent must save....", false);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}

			try {
				item[1] = new Movie("PMM", "Pokemon: Mew vs Mewtwo",
							"Action/Adventure",
							"Ash is gathered with strong trainers...but..",
							false);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[1].borrow("TOM", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}

			try {
				item[2] = new Movie("PLM",
							"Pokemon: Lucario and the Mystery of Mew",
							"Action/Adventure",
							"Ash stumbles upon a confused Lucario...", false);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[2].borrow("RNG", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[2].returnItem(new DateTime(5));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}

			try {
				item[3] = new Movie("NGZ", "No Game No Life: Zero",
							"Fantasy/Action",
							"Before the Legendary Sora and Shiro was born...",
							false);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[3].borrow("GNR", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[3].returnItem(new DateTime(10));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}

			try {
				item[4] = new Movie("SMH", "Shaking my Head", "Horror",
							"Movie Starts with a disappointment..", false);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[4].borrow("GNR", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[4].returnItem(new DateTime(10));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}
			try {
				item[4].borrow("GEO", new DateTime(10));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}

			// new release movie
			try {
				item[5] = new Movie("SOS", "Sword Art Online: Ordinal Scale",
							"Fantasy/Action",
							"The new Augma has just been released, but Kirito is resiliant...",
							true);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}

			try {
				item[6] = new Movie("BTB", "Bob, the Builder", "Comedy",
							"Can we fix it?", true);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[6].borrow("TOM", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}

			try {
				item[7] = new Movie("BOB", "Bullets of Bullets", "Action",
							"A new SAO: Battle of the guns..", true);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[7].borrow("GEO", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[7].returnItem(new DateTime(1));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}

			try {
				item[8] = new Movie("NBP", "Naruto: Blood Prison", "Action",
							"Naruto faces a new obstacle..", true);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[8].borrow("WEB", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[8].returnItem(new DateTime(3));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}

			try {
				item[9] = new Movie("KKM", "Karate Kid movie", "Action",
							"Bullied kid desires a change..", true);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[9].borrow("RNG", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[9].returnItem(new DateTime(3));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}
			try {
				item[9].borrow("GNR", new DateTime(3));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}

			// Games
			String[] platform = { "Nintendo Wii" };
			try {
				item[10] = new Game("PBR", "Pokemon: Battle revolution",
							"Fighting", "Conquer all of the colosseum..",
							platform);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}

			String[] platform1 = { "Nintendo Wii" };
			try {
				item[11] = new Game("ACR", "Animal Crossing", "Strategy",
							"In a village of Animals...", platform1);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[11].borrow("WEB", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}

			String[] platform2 = { "Xbox 360, PS4" };
			try {
				item[12] = new Game("COD", "Call of Duty", "Fighting/Zombies",
							"In an Apocalypse...", platform2);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[12].borrow("RNG", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[12].returnItem(new DateTime(19));
			} catch (ReturnException e2) {

				System.out.println(e2.getMessage());

			}

			String[] platform3 = { "PSP" };
			try {
				item[13] = new Game("SVA", "Sword Art Online vs Accel World",
							"Adventure/Fantasy", "When one meets another...",
							platform3);
			} catch (IdException e1) {

				System.out.println(e1.getMessage());

			}
			try {
				item[13].borrow("WEB", new DateTime(0));
			} catch (BorrowingException e) {

				System.out.println(e.getMessage());

			}
			try {
				item[13].returnItem(new DateTime(32));
			} catch (ReturnException e) {

				System.out.println(e.getMessage());

			}
			System.out.println("SEED IS DONE");
		}

		else {
			System.out.println("ERROR, SYSTEM CANNOT OVERWRITE DATA");
		}
	}

	//displaying the items and their hiring records
	private void display() {
		//code will reach back to the menu if no items exist
		if (item[0] == null) {
			System.out.println("ERROR: NO ITEMS HAS YET BEEN ADDED");
		}
		//printing out all item data String via loop 
		for (int i = 0; i < item.length; i++) {
			if (item[i] != null) {
				System.out.println(item[i].getDetails());
			}
		}
	}

	//returning item function
	private void returnItem() {
		
		//code will reach back to the menu if no items exist
		if (item[0] == null) {
			System.out.println("ERROR: NO ITEMS HAS YET BEEN ADDED");
			error = false;
		}
		//activates when there is at least 1 item created
		while (error) {
			/*
			 * ALGORITHM
			 * BEGIN
			 * 
			 * USER ENTERS ID
			 	*PROGRAM SCANS for the same ID as the user input
			 	* IF ID IS FOUND
			 		* USER ENTERS DAYS ON LOAN
			 		* IF INVALID INPUT
			 			*ERROR MESSAGE IS DISPLAYED 
			 			*USER IS TAKEN BACK TO MENU
			 		*ELSE
			 		*NOTIFY user the success
			 		*returnItem method is implemented 
			 		*BACK TO MENU
			 	* ELSE
			 		*DISPLAY ERROR
			 		*GOES BACK TO MENU 
			 *
			 *END
			 */
			boolean idError = true;
			//User enters id
			//User will go back to menu if id is invalid
			System.out.print(
						String.format("%-25s %s", "Enter ID: ", ""));
			currentId = console.next();
			string = "M_" + currentId;
			String string1 = "G_" + currentId;
			for (int j = 0; j < item.length; j++) {
				if (item[j] != null) {
					if (item[j].getId() != null) {
						if ((string.equals(item[j].getId())) || (string1
									.equals(item[j].getId()))) {
							idError = false;
							if (item[j].getBoolCurrentBorrowed() == false) {
								System.out.println(
											"ERROR: ITEM IS NOT BORROWED");
								error = false;
								break;
							}
							error = false;
							//User inputs no. of days on loan
							//User will go back to menu if invalid input
							System.out.print(String.format("%-25s %s",
										"Enter number of days on loan: ",
										""));
							try {
								advanceDateCounter = Integer
											.valueOf(console.next());
							} catch (NumberFormatException e) {

								System.out.println(
											"ERROR: INPUT MUST BE A INTEGER");
								break;
							}

							try {
								//returnItem method implemented
								System.out.println(
								"The total fee payable is $"
									+ 
								formatter.format(item[j].returnItem(new DateTime(advanceDateCounter))));
																	
																				
																							
							} catch (ReturnException e) {

								System.out.println(e.getMessage());
								error = false;
								break;
							}
							break;

						}

					}

				}
			}
			if (idError) {
				System.out.println("ERROR... INVALID ID");
				break;
			}
		}
	}
	
	//borrowing item function
	private void borrowItem() {
		
		//Code will reach the end if no items exist
		if (item[0] == null) {
			error = false;
			System.out.println("ERROR.. NO ITEMS EXIST");
		}
		//This part of code will function if at least 1 item exist
		/*
		 * ERRORS (like integer inputs) will be warned to the user 
		 * and redirected back to the menu
		 * THIS INCLUDES EXCEPTIONS
		 */
		while (error) {
			System.out.print(
						String.format("%-25s %s", "Enter ID: ", ""));
			boolean idError = true;
			currentId = console.next();
			for (int j = 0; j < item.length; j++) {
				if (item[j] != null) {
					if (item[j].getId() != null) {
						//If the item is a movie type
						if (item[j] instanceof Movie) {
							String string = "M_" + currentId;
							if (string.equals(item[j].getId())) {
								idError = false;
								System.out.print(String.format(
											"%-25s %s",
											"Enter Member ID: ", ""));
								memberId = console.next();

								System.out.print(String.format(
											"%-25s %s",
											"Advance borrow(days): ",
											""));
								try {
									advanceDateCounter = Integer
												.valueOf(console
															.next());
									if (advanceDateCounter < 0) {
										System.out.println(
													"Days can't be less than 0!");
										error = false;
										break;
									}
								} catch (NumberFormatException e1) {

									System.out.println(
												"ERROR: INPUT MUST BE A INTEGER");
									error = false;
									break;
								}
								//updates movie data and prints out the completion
								try {
									System.out.println("The item " + "'"
											+ item[j].getTitle()
											+ "'" + " costs $"
											+ formatter.format(
												 item[j].borrow(memberId,			
												new DateTime(advanceDateCounter)))																					
											+ " and is due on: "
											+ (new DateTime(
										 advanceDateCounter + dueDayCounter)).getFormattedDate());
																		
																					
									error = false;
									break;
								} catch (BorrowingException e) {
									System.out.println(e.getMessage());
									error = false;
									break;

								}
							}
							//if the item is a game
						} else {
							String string = "G_" + currentId;
							if (string.equals(item[j].getId())) {
								idError = false;
								if (item[j].getBoolCurrentBorrowed() == false) {
									System.out.print(String.format(
												"%-25s %s",
												"Enter Member ID: ",
												""));
									memberId = console.next();
									System.out.print(String.format(
												"%-25s %s",
												"Advance borrow(days): ",
												""));
									try {
										advanceDateCounter = Integer
													.valueOf(console
																.next());
										if (advanceDateCounter < 0) {
											System.out.println(
														"Days can't be less than 0!");
											error = false;
											break;
										}
									} catch (NumberFormatException e1) {

										System.out.println(
													"ERROR: INPUT MUST BE A INTEGER");
										error = false;
										break;
									}
									//updates game data and prints out to screen the completion
									try {
										System.out.println("The item "
													+ "'"
													+ item[j].getTitle()
													+ "'" + " costs $"
													+ formatter.format(
																item[j].borrow(
																			memberId,
																			new DateTime(
																						advanceDateCounter)))
													+ " and is due on: "
													+ (new DateTime(
																advanceDateCounter
																			+ dueDayCounter))
																						.getFormattedDate());
										error = false;
										break;
									} catch (BorrowingException e) {
										System.out.println(
													e.getMessage());
										error = false;
										break;
									}

								} else {
									System.out.print(String.format(
												"%-25s %s",
												"EXTENDED (Y/N): ",
												""));
									String userInput = console.next();
									userInput = userInput.toUpperCase();

									// Yes to extended
									if (userInput.equals("Y")) {
										error = false;
										((Game) item[j])
													.setExtended(true);
										System.out.println("The item "
													+ "'"
													+ item[j].getTitle()
													+ "'"
													+ " has been set to EXTENDED");
									}
									//No to extended
									else if (userInput.equals("N")) {
										System.out.println(
													"BACK TO MENU....");
										error = false;
										break;
									}
								}

							}

						}
					}
				}
			}
			//Error message for unknown id
			if (idError) {
				System.out.println("ERROR...UNIDENTIFIED ID!!");
				break;
			}
		}
	}
	
	//adding item function
	private void addItem() {
		error = true;

		/* condition of user inputed id must be satisfied
		 * loops back to start if same id was found
		 * goes back to menu if inputed id does not have 3 char 
		 * Any other exception errors will take the user to the menu
		 */
		while (error) {
			System.out.print(
						String.format("%-25s %s", "Enter ID: ", ""));
			currentId = console.next();
			string = "M_" + currentId;
			String string1 = "G_" + currentId;
			for (int i = 0; i < item.length; i++) {
				if (item[i] != null) {
					if ((item[i].getId()).equals(string)
								|| (item[i].getId()).equals(string1)) {
						System.out.println("ERROR: ID for " + currentId
									+ " ALREADY EXISTS IN SYSTEM");
						break;

					}

				}

				else {
					counting = i;
					error = false;
					break;
				}

			}
		}

		//user input of item details
		// title
		System.out.print(
					String.format("%-25s %s", "Enter Title: ", ""));
		title = console.next();
		// genre
		System.out.print(
					String.format("%-25s %s", "Enter Genre: ", ""));
		genre = console.next();
		// description
		System.out.print(
					String.format("%-25s %s", "Description: ", ""));
		description = console.next();
		// type of item
		System.out.print(String.format("%-25s %s",
					"Movie or Game (M/G)? ('R' to return): ", ""));
		String chooseItem = console.next();
		chooseItem = chooseItem.toUpperCase();
		error = true;
		/*
		 * ALGORITHM
		 * BEGIN
		 * 
		 * IF input is 'M'
		 	* ENTER new release or not
		 		*IF input is 'Y'
		 			*NEW RELEASE IS TRUE
		 			*2 days movie
		 			*CREATE MOVIE OBJECT
		 		*IF input is 'N'
		 			* NEW RELEASE IS FALSE
		 			* 7 days movie
		 			* CREATE MOVIE OBJECT
		 		*ELSE
		 			*MESSAGE ERROR 
		 			*LOOPS BACK FOR ENTER NEW RELEASE
		 *IF input is 'G'
		 	*ENTER platform
		 	*INPUTS platform in an array
		 	*CREATE GAME OBJECT
		 *ELSE
		 	*DISPLAY error
		 	*LOOPS BACK TO MOVIE OR GAME..
		 *
		 * END
		 */
		while (error) {

			// if the item is a movie
			
			if (chooseItem.equals("M")) {
				System.out.print(String.format("%-25s %s",
							"EnterNewRelease(Y/N): ", ""));
				newRelease = console.next();
				error = true;
				while (error) {

					if ((newRelease.toUpperCase()).equals("N")) {
						try {
							currentMovie = new Movie(currentId, title,
										genre, description, false);
						} catch (IdException e) {

							System.out.println(e.getMessage());
							error = false;
							break;
						}
						dueDayCounter = 2;

						item[counting] = currentMovie;
						counting += 1;
						error = false;
						System.out.println("The movie " + title
									+ " has been successfully added!");

					} else if ((newRelease.toUpperCase()).equals("Y")) {
						try {
							currentMovie = new Movie(currentId, title,
										genre, description, true);
						} catch (IdException e) {

							System.out.println(e.getMessage());
							error = false;
							break;
						}
						dueDayCounter = 7;

						item[counting] = currentMovie;
						counting += 1;
						error = false;
						System.out.println("The movie " + title
									+ " has been successfully added!");
					} else if (newRelease.equals("r")) {

						error = false;

					}

					else {
						System.out.print(
									"INVALID OPTION.. EnterNewRelease(Y/N): ");
						newRelease = console.next();
						newRelease = newRelease.toUpperCase();
					}
				}
			}
			//if the item is a game
			else if (chooseItem.equals("G")) {
			// game item is due in 14 days
				dueDayCounter = 14;
				System.out.println(
							"Type in the amount of platform in this game:");
				try {

					int num = Integer.valueOf(console.next());
					if (num <= 0) {
						System.out.println(
									"ERROR: GAME CAN'T HAVE NEGATIVE/ZERO PLATFORMS");
						error = false;
						break;
					}
					String[] arrayOfPlatform = new String[num];
					System.out.println(
								"Type in platforms (Enter for each platform):");

					for (int i = 0; i < arrayOfPlatform.length; i++) {
						arrayOfPlatform[i] = console.next();
					}

					try {
						item[counting] = new Game(currentId, title, genre,
									description, arrayOfPlatform);
					} catch (IdException e) {

						System.out.println(e.getMessage());
						error = false;
						break;
					}
					System.out.println("The Game " + title
								+ " has been successfully added!");
					error = false;
					counting += 1;
				} catch (NumberFormatException e) {

					System.out.println(
								"ERROR: INPUT MUST BE A INTEGER");
					error = false;
				}

			}
			//if the user decides to return to menu
			else if (chooseItem.equals("R")) {
				System.out.println("Returning to Menu....");
				error = false;
				break;
			}
			//when user writes in wrong input of item type
			//warning is displayed
			else {
				System.out.println(
							"ERROR.. WRONG INPUT.. TYPE IN ITEM TYPE AGAIN");
				System.out.println(
							"MOIVE OR GAME (M/G) OR 'R' FOR RETURN");
				chooseItem = console.next();
				chooseItem = chooseItem.toUpperCase();
			}

		}

	}

	//displays the menu screen
	private void screen() {
		System.out.print(

					String.format("%-25s %s\n", "",
								"**MOVIE MASTER SYSTEM MENU**")
								+ "\n"
								+ String.format("%-25s %s\n", "Add item",
											"A")
								+ String.format("%-25s %s\n", "Borrow item",
											"B")
								+ String.format("%-25s %s\n", "Return item",
											"C")
								+ String.format("%-25s %s\n",
											"Display details", "D")
								+ String.format("%-25s %s\n", "Seed Data",
											"E")
								+ String.format("%-25s %s\n\n",
											"Exit program", "X")
								+ String.format("%-25s %s",
											"Enter Selection:", "")

		);
	}
}
