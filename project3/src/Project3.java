//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Project3)
// Files:            (Project3.java , Player,java , StratTable.java)
// Semester:         (course) Summer 2017
//
// Author:           (Sean Gallogly)
// Email:            (sgallogly@wisc.edu)
// CS Login:         (sgallogly)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (Summer 2017)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     (Wei Lin Tsai)
// Partner Email:    (wtsai9@wisc.edu)
// Partner CS Login: (wtsai9)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (summer 2017)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _x_ Write-up states that Pair Programming is allowed for this assignment.
//    _x_ We have both read the CS302 Pair Programming policy.
//    _x_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          (identify each person and describe their help in detail)
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;

public class Project3 {
	public static Scanner kybScnr = new Scanner(System.in); //The input scanner scanning from input from the keyboard
	public static Scanner entryScnr; //scans a given entry that was entered from the keyboard 
	public static Scanner inFS; //Scanner of a file input stream
	public static int SEED = 777; //the seed to ensure accuracy of the autograder
	public static Random rangen = new Random(SEED); //the one declared random object, initialized with the given seed
	
	public final static char[] ENG_LETTERS = {'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' , //an array of the letters of the english alphabet, capitalized
			'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'X' , 'Y' , 'Z'};

	public static boolean isNumeric(String str) {  
		  try  
		  {  
		    int i = Integer.parseInt(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}
		
	public static void main(String[] args) throws IOException {
		char guess; //the string representation of the guess of either a human or a computer player
		double sum1 , sum2 , probOfGuess; //sum1 is used to calculate the sum of the available frequencies for a computer guesser. sum2 calculates the weighted probability of finding the available letters to the computer. probOfGuess is the randomly generated double used to determine computer's next move. 
		boolean isGuesser , won , valid , consistent , allGood , done; //might want to initialize somewhere else ; isGuesser determines if this player is guesser or not, and won indicates whether the gueser has guessed the word or not
		int turnNum , gameNum , numLett = 0 , inputAsInt; //numLett is the number of letters the chosen word has, regardless of the chooser, inputAsInt is just a typecast of user input e.g. when the user enters the positions of the computer guess if they exist.
		String humanInput = "" , aiWord = "" , temp; //humanInput is input from the keyboard for a myriad of circumstances , aiWord is the ai's chosen word (regardless if it is the chooser or not!) and temp is a reference variable to make swaps of userinput as a string to int (NOTE: may not need) 
		double[] weightedFreq = new double[26]; //an arraylist that stores the weighted frequencies of english letters given those letters available to the computer to pick from
		ArrayList <String> wordFileContents = new ArrayList<String>(); //an arrayList that stores the contents of word.txt, where each entry is a unique word from word.txt
		ArrayList<Character> secretWordLett = new ArrayList<Character>(); //initially shows asterisk in each letter position of chosen word, and reveals as correct letters are guessed
		ArrayList<Character> charGuessed = new ArrayList<Character>();  //stores the letters guessed in a given game
		ArrayList<Character> charOfAIWord = new ArrayList<Character>();  //parses chosen word and stores each letter as an entry
		ArrayList<Integer> indexOfGuess = new ArrayList<Integer>();  //used to store the index of a guess every turn
		ArrayList<Integer> properPositions = new ArrayList<Integer>(); //Used to test whether input positions are in numerical order
		Player guesser = new Player();
		Player chooser = new Player();
		gameNum = 1;
		turnNum = 1;
		done = false;
		while (!done) {
			String letSoFar = ""; // for letter guessed so far
			FileInputStream fileByteStream = new FileInputStream("words.txt"); //the input stream accessing the file word.txt, allowing its contents to be read in to this program
			Scanner inFS = new Scanner(fileByteStream); //The scanner of the input stream from words.txt
			while(inFS.hasNext()) {
				wordFileContents.add(inFS.nextLine());
			}
			if (gameNum == 1) {
				//NOTE: initializing the aiWord before the game(s) start as per instruction
				aiWord = wordFileContents.get(rangen.nextInt(wordFileContents.size()));
			}
			//Begin tournament cycle
			valid = false;
			while (!valid) {
				System.out.print("Do you wish to be the word [C]hooser or the word [G]uesser:");
				humanInput = kybScnr.nextLine();
				if (!humanInput.isEmpty()) {
					entryScnr = new Scanner(humanInput);
					temp = entryScnr.next();
					if ((Character.toUpperCase(temp.charAt(0)) == 'G') || (Character.toUpperCase(temp.charAt(0)) == 'C')) {
						valid = true;
					}
				}
			}
			if (Character.toUpperCase(humanInput.charAt(0)) == 'G') {
				isGuesser = true;
				guesser.setIsHuman(true);
				chooser.setIsHuman(false);
				
			}
			else if (Character.toUpperCase(humanInput.charAt(0)) == 'C') {
				isGuesser = false;
				chooser.setIsHuman(true);
				guesser.setIsHuman(false);
			}
			if (chooser.getIsHuman()) {
				System.out.println();
				valid = false;
				while (!valid) {
					System.out.print("How many letters are in your chosen word: ");
					humanInput = kybScnr.nextLine();
					//NOTE: If user enters nothing, place an empty line and then reprompt
					if (humanInput.isEmpty()) {
						System.out.println();
					}
					else if (!isNumeric(humanInput)) {
						System.out.println("You must enter an integer, try again");
					}
					else {
						entryScnr = new Scanner(humanInput);
						numLett = entryScnr.nextInt();
						if (numLett < 1 || numLett > 30) {
							System.out.println("your word must have between 1 and 30 letters, try again.");
						}
						else {
							valid = true;
						}
					}
				}
			}
			else {
				numLett = aiWord.length();
				for (int j = 0 ; j < numLett ; j++) {
					charOfAIWord.add(aiWord.charAt(j)); //At this point charOFAIWord is filled with LOWERCASE letters of aiWord
				}
			}
			//culling all word that doesn't match the number of letter
			for (int i = 0 ; i < wordFileContents.size(); i++) {
				if (wordFileContents.get(i).length() != numLett) {
					wordFileContents.remove(i);
					i--;
				}
			}
			//Then check if wordFileContents.size() is 0
			if (wordFileContents.size() == 0) {
					System.out.println("No Letters guessed yet: ");
					System.out.println("No strikes yet -- all ten still remaining.");
				System.out.print("Word revealed thus far: ");
				for(int j = 0 ; j < numLett ; j++) {
					secretWordLett.add('*');
					System.out.print(secretWordLett.get(j) + " ");
				}
				System.out.println();
				consistent = false;
			}
			else {
				consistent = true;
			}
			guesser.setNumStrikes(10);
			won = false;
			while (!won && (guesser.getNumStrikes() != 0) && consistent) {
				if (turnNum == 1) {
					System.out.print("No letters guessed yet.\nNo strikes yet -- all ten still remaining.\nWord revealed thus far: ");
					for(int j = 0 ; j < numLett ; j++) {
						secretWordLett.add('*');
						System.out.print(secretWordLett.get(j) + " ");
					}
					System.out.println();
				}
				else {
					if(letSoFar.isEmpty()){
						System.out.print("No Letters guessed yet: ");
					}
					else{
						System.out.print("Letters guessed so far: ");
						System.out.print(letSoFar);
					}
					System.out.println();
					if (guesser.getNumStrikes() == 10) {
						System.out.println("No strikes yet -- all ten still remaining.");
					}
					else {
						System.out.println("Guesser currently has " + (10 - guesser.getNumStrikes()) + " strikes (" + guesser.getNumStrikes() + " to go until automatic loss)");
					}
					System.out.print("Word revealed thus far: ");
					for(int j = 0 ; j < numLett ; j++) {
						System.out.print(secretWordLett.get(j) + " ");
					}
					System.out.println();
				}
				if (guesser.getIsHuman()) {
					valid = false;
					while (!valid) {
						System.out.print("\nEnter a letter: "); 
						humanInput = kybScnr.nextLine();
						if (humanInput.isEmpty()) {
							consistent = false;
							if(letSoFar.isEmpty()){
								System.out.print("No Letters guessed yet: ");
							}else{
								System.out.print("Letters guessed so far: ");
								System.out.print(letSoFar);
							}
							System.out.println();
							if (guesser.getNumStrikes() == 10) {
								System.out.println("No strikes yet -- all ten still remaining.");
							}
							else {
								System.out.println("Guesser currently has " + (10 - guesser.getNumStrikes()) + " strikes (" + guesser.getNumStrikes() + " to go until automatic loss)");
							}
							System.out.print("Word revealed thus far: ");
							for(int j = 0 ; j < numLett ; j++) {
								System.out.print(secretWordLett.get(j) + " ");
							}
							System.out.println();
						}
						else {
							entryScnr = new Scanner(humanInput);
							humanInput = entryScnr.next();
							if (charGuessed.isEmpty()) {
								for (char test = 'a' ; test <= 'z' ; test++) {
									if (Character.toLowerCase(humanInput.charAt(0)) == test) {
										valid = true;
									}
								}
							}
							else {
								int i = 0;
								while (i < charGuessed.size() && Character.toLowerCase(humanInput.charAt(0)) != Character.toLowerCase(charGuessed.get(i))) {
									i++;
								}
								if (i == charGuessed.size()) {
									for (char test = 'a' ; test <= 'z' ; test++) {
										if (Character.toLowerCase(humanInput.charAt(0)) == test) {
											valid = true;
										}
									}
								}
								else {
									System.out.println("Letter already guessed, try again.");
								}
							}
						}
					}
					guess = Character.toLowerCase(humanInput.charAt(0));
					if (charOfAIWord.contains(guesser)) {
						guess = Character.toUpperCase(guess);
						charGuessed.add(Character.toUpperCase(guess));
						letSoFar = letSoFar + Character.toUpperCase(guess) + " ";
					}
					else {
						charGuessed.add(Character.toLowerCase(guess));
						letSoFar = letSoFar + Character.toLowerCase(guess) + " ";
					}
					aiWord = StratTable.secretSolutionChooser(wordFileContents , charGuessed.get(charGuessed.size() - 1));
					charOfAIWord.clear();
					for (int j = 0 ; j < numLett ; j++) {
						charOfAIWord.add(aiWord.charAt(j)); //At this point charOFAIWord is filled with LOWERCASE letters of aiWord
					}
					if (charOfAIWord.contains(guess)) {
						for (int j = 0 ; j < numLett ; j++) {
							if (guess == charOfAIWord.get(j)) {
								secretWordLett.set(j, Character.toUpperCase(guess));
								indexOfGuess.add(j + 1);
							}
						}
						System.out.print("The letter '" + guess + "' is at positions: ");
						System.out.print(indexOfGuess +"\n");
						indexOfGuess.clear();
					}
					else {
						System.out.println("The letter '" + guess + "' is not in the word -- Strike!");
						guesser.decrementStrikes();
						//charGuessed.add(Character.toLowerCase(guess));
					}
				}
				//NOTE:Block below handles when guesser is the computer
				else {
					guess = StratTable.computerGuess(wordFileContents , charGuessed);
					System.out.println("\nThe computer chooses the letter '" + Character.toLowerCase(guess) + "' for its turn.");
					//NOTE: Input validation for guess positions in word
					valid = false;
					while (!valid) {
						properPositions.clear();
						System.out.print("Enter locations that computer's guess appears in your word (0 if none): ");
						humanInput = kybScnr.nextLine();
						if (humanInput.isEmpty()) {
							System.out.println("You must enter at least one number, try again.");
						}
						else {
							entryScnr = new Scanner(humanInput);
							do {
								temp = entryScnr.next();
								int i = 0;
								while (i != (temp.length() + 1) && i < temp.length()) {
									if (!Character.isDigit(temp.charAt(i))) {
										i = temp.length() + 1;
									}
									else {
										i++;
									}
								}
								if (i == (temp.length() + 1) && !isNumeric(temp)) {
									System.out.println("All entries must be integers, try again.");
									break;
								}
								else {
									inputAsInt = Integer.parseInt(temp);
								}
								if (inputAsInt < 0 || inputAsInt > numLett) {
									System.out.println("Numbers should be in range of 0 up to " + numLett + ", the word length");
									break;
								}
								if ((inputAsInt != 0) && (secretWordLett.get(inputAsInt - 1) != '*')) {
									System.out.println("You entered a position already known, try again.");
									break;
								}
								else {
									if (inputAsInt == 0 && entryScnr.hasNext()) {
										System.out.println("There should be no entries after a 0");
										break;
									}
									else if (inputAsInt == 0 && !entryScnr.hasNext()) {
										charGuessed.add(Character.toLowerCase(guess));
										letSoFar = letSoFar + Character.toLowerCase(guess) + " ";
										guesser.decrementStrikes();
										valid = true;
									}
									//NOTE: Input validation for increasing order
									else if (inputAsInt != 0 && entryScnr.hasNext()) {
										properPositions.add(inputAsInt);
										if (properPositions.size() > 1) {
											int k = properPositions.indexOf(inputAsInt);
											if (properPositions.get(k) < properPositions.get(k-1)) {
												System.out.println("Positions must be entered in increasing order, try again.");
												properPositions.clear();
												break;
											}
										}
									}
									else if (inputAsInt != 0 && !entryScnr.hasNext()) {
										properPositions.add(inputAsInt);
										if (properPositions.size() > 1) {
											int k = properPositions.indexOf(inputAsInt);
											if (properPositions.get(k) < properPositions.get(k-1)) {
												System.out.println("Positions must be entered in increasing order, try again.");
												break;
											}
											else {
												for (int l = 0 ; l < properPositions.size() ; l++) {
													secretWordLett.set(properPositions.get(l) - 1, guess);
												}
												charGuessed.add(Character.toUpperCase(guess));
												letSoFar = letSoFar + Character.toUpperCase(guess)+ " ";
												valid = true;
											}
										}
										else {
											for (int l = 0 ; l < properPositions.size() ; l++) {
												secretWordLett.set(properPositions.get(l) - 1, guess);
											}
											charGuessed.add(Character.toUpperCase(guess));
											letSoFar = letSoFar + Character.toUpperCase(guess)+ " ";
											valid = true;
										}
									}
								}
							} while (entryScnr.hasNext() && !valid);
						}
					}
				//NOTE: userInput position consistency check
					if (!properPositions.isEmpty()) {
						for (int j = 0; j< properPositions.size();j++) {
							for(int i = 0;i<wordFileContents.size();i++) {
								if ((wordFileContents.get(i).charAt(properPositions.get(j)-1))!=((Character.toLowerCase(guess)))){
									wordFileContents.remove(i);
									i--;
								}
							}
						}
						//NOTE: Gives game status before exiting game due to inconsistent user input
						if (wordFileContents.isEmpty()) {
							consistent = false;
							if(letSoFar.isEmpty()){
								System.out.print("No Letters guessed yet: ");
							}
							else{
								System.out.print("Letters guessed so far: ");
								System.out.print(letSoFar);
							}
							
							System.out.println();
							if (guesser.getNumStrikes() == 10) {
								System.out.println("No strikes yet -- all ten still remaining.");
							}
							else {
								System.out.println("Guesser currently has " + (10 - guesser.getNumStrikes()) + " strikes (" + guesser.getNumStrikes() + " to go until automatic loss)");
							}
							System.out.print("Word revealed thus far: ");
							for(int j = 0 ; j < numLett ; j++) {
								System.out.print(Character.toUpperCase(secretWordLett.get(j)) + " ");
							}
							System.out.println();
						}
					}
				}
				if (!secretWordLett.contains('*')) {
					won = true;
				}
				turnNum++;
				entryScnr.close();
			}
			//One game cycle complete
			if (won && consistent) {
				System.out.print("\nThe guesser has won by finding the word '");
				for (int j = 0 ; j < numLett ; j++) {
					System.out.print(secretWordLett.get(j));
				}
				System.out.print("' before the tenth strike.");
			}
			else {
				if (!consistent) {
					System.out.println("The computer does not know your word.");
				}
				System.out.println("\nThe guesser has lost by not finding the word before reaching ten strikes.");
				if (chooser.getIsHuman()) {
					valid = false;
					while (!valid) {
						System.out.print("\nThe Chooser should reveal their word now: ");
						humanInput = kybScnr.nextLine();
						if (!humanInput.isEmpty()) {
							valid = true;
						}
					}
					humanInput = humanInput.toLowerCase();
					entryScnr = new Scanner(humanInput);
					humanInput = entryScnr.next();
					if (humanInput.length() != numLett){
						System.out.print("That does not match the length you stated at the start of the game.\n");
					}
					int j = 0;
					for (int i = 0 ; i < charGuessed.size() ; i++) {
						if ((humanInput.indexOf(Character.toLowerCase(charGuessed.get(i)) , 0) == -1 && charGuessed.get(i) == Character.toUpperCase(charGuessed.get(i))) || (humanInput.indexOf(Character.toLowerCase(charGuessed.get(i)) , 0) != -1 && charGuessed.get(i) == Character.toLowerCase(charGuessed.get(i)))) {
							System.out.println("Your statement on my guess '" + Character.toLowerCase(charGuessed.get(i)) + "' is not consistent with your reported solution.");
							j++;
						}
					}
				}
				else {
					System.out.println("The computer had chosen the word '" + aiWord.toUpperCase() + "'.");
				}
			}
			System.out.print("\nDo you wish to play again (Y/N)? ");
			humanInput = kybScnr.nextLine();
			entryScnr = new Scanner(humanInput);
			humanInput = entryScnr.next().toLowerCase();
			if (humanInput.charAt(0) == 'n') {
				done = true;
				fileByteStream.close();
				inFS.close();
			}
			else {
				gameNum++;
				turnNum = 1;
				wordFileContents.clear();
				charGuessed.clear();
				secretWordLett.clear();
				//Close 8-bit input stream as we have everything we need from it for this game: will re-open if another game exists
				fileByteStream.close();
				inFS.close();
			}
		}
		kybScnr.close();
		entryScnr.close();
	}
}
