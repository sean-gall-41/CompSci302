/**
 * The Date class serves to construct a new date, which is a move in the 
 * New Year's game. It also defines getters and setters for the month name
 * and number as well as specialized methods for human player input validation
 * as well as to determine number of legal moves after a given arbitrary date. 
 * 
 * Bugs: (Cannot think of any at the moment) 
 * 
 * @author (Sean Gallogly) */
public class Date {
	private static final int TOT_MONTHS_IN_YEAR = 12; //A constant defining the number of months in a year
	private String name; //the name of the month of the initialized date
	private int num; //the number of the day in the month of the initialized date
	private static String[] monthName = {"Jan", "Feb","Mar","Apr","May",  //An array that gives the names of all the months abbreviated to the first 3 letters
			"Jun","Jul", "Aug","Sep","Oct","Nov","Dec",""};
	private static int[] monthMax = {31,28,31,30,31,30,31,31,30,31,30,31,0}; //An array with the maximum amount of days in each month (in order)
	
	public Date(String inName, int inNum){
		this.name = inName;
		this.num = inNum;
	}
	/**
	 *Returns the name of the month of the instance that called it. Note 
	 *that the name may not be valid while user input validation is occurring
	 *
	 * @param (na) 
	 * @return (the name assigned to the Date calling this method)
	 */
	public String getName(){
		return this.name;
	}
	/**
	 *Returns the number of the day in the month of the date that called it
	 *
	 * @return (the number assigned to the date calling this method)
	 */
	public int getNum(){
		return this.num;
	}
	/**
	 *Determines whether the date calling the method is equal to the Date 
	 * as an argument. Used to validate a user's move
	 *
	 * @param other) (the desired date to compare this date to)
	 * @return (returns the validity of whether this month is equal to the parameter month)
	 */
	public boolean equals(Date other){
		return ((this.name.equals(other.getName()))&&(this.num==other.getNum()));
	}
	/**
	 *Determines the number of the calling date's month in the year, minus one
	 *e.g. if Date was January 30 then Date.monthNumInYear(this.Date) returns 0
	 *
	 * @param (inMon) (the desired month that one wishes to find the number in year for (a string literal))
	 * @return (returns an integer representing the number in the year minus 1)
	 */
	public static int monthNumInYear(String inMon){ 
		int i = 0;
		while (!(inMon.equals(monthName[i])) && (i<12)) {
			i++;
		} 
		return i;
	}
	/**
	 * Determines the maximum number of days in the month that is the argument of this method
	 *
	 * @param (inMon) (the name of the month (as a string literal) that one wishes to determine how many days are contained in)
	 * @return (an integer representation of the maximum number of days in the argument's month)
	 */
	public static int monthMax(String inMon) {
		int i = 0;
		while (!(inMon.equals(monthName[i]))) {
			i++;
		}
		return monthMax[i];
		
	}
	/**
	 *Compares two dates and determines the validity of whether this month is after the argument month.
	 *
	 * @param (prior) (The date to be tested against this month)
	 * @return (returns true or false whether this month is after prior or not)
	 */
	public boolean after(Date prior){
		int month1 = monthNumInYear(prior.getName());
		int month2 = monthNumInYear(this.name);
		if (month2!=month1){
			return (month2>month1);
		} else {
			return (this.num>prior.num);
		}
	}
	/**
	 *Serves to be used in a print statement to print out the name of the month and number of day in that month of
	 *the calling month
	 *
	 * @return (returns a string representation of the name of the month and the number of the day in that month)
	 */
	public String toString(){
		return this.name+" "+this.num;
	}
	/**
	 *Determines how many days of the year exist before the argument date. 
	 *e.g. if the argument date is Jan 1 then dayNum(date) returns 0
	 *
	 * @param (inDate) (the date which one wishes to find the number of days prior to)
	 * @return (returns the algorithmic sum representing the number of days in this year that exist before the argument date)
	 */
	public static int dayNum(Date inDate) {
		int sum=0;
		for(int i=0; i<monthNumInYear(inDate.getName()); i++){
			sum+=monthMax[i];			
		}
		sum+=inDate.getNum()-1;
		return sum;
	}
	/**
	 *Determines the total number of legal moves that exist after the argument date
	 *
	 * @param (inDate) (the date of which one wishes to find how many legal moves exist after)
	 * @return (returns the integer representation of the number of legal moves after this date)
	 */
	public static int numLegal(Date inDate) {
		int i = 0;
		int sum = 0;
		for (i = monthNumInYear(inDate.getName())+1; i<TOT_MONTHS_IN_YEAR ;i++ ) {
			if (monthMax[i] >= inDate.getNum()) {
				sum++;
			}
		}
		sum += monthMax[monthNumInYear(inDate.getName())] - inDate.getNum();
		return sum;
	}
	/**
	 *Creates and populates an array of all of the possible legal moves that could be played after the calling date
	 *
	 * @return (Returns the (populated array of all possible legal moves after this date. Note that the numLegal function is used to determine
	 * the size of the array created for the calling date)
	 */
	public Date[] legalMoves() {
		Date[] subseqLegalMoves = new Date[numLegal(this)];
		int i = 0;
		int j = monthNumInYear(this.getName());
		int k = 0;
		Date validArrayEntry;
		for (i = 0 ; i < numLegal(this) ; i++) {
			k = this.num + 1;
			while (k <= monthMax(this.getName())) {
				validArrayEntry = new Date(monthName[j] , k);
				subseqLegalMoves[i] = validArrayEntry;
				k++;
				i++;
			}
			j = monthNumInYear(this.getName()) + 1;
			while (j < TOT_MONTHS_IN_YEAR) {
				if (monthMax[j] >= this.getNum()) {
					validArrayEntry = new Date(monthName[j] , this.getNum());
					subseqLegalMoves[i] = validArrayEntry;
					i++;
				}
				j++;
			}
			  
		}
		return subseqLegalMoves;
	}
}