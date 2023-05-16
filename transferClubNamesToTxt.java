import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Haven't checked this code in a year and it still works fine and dandy on torolink lol. the only issue that happens is that more clubs get added to the database so a quick fix to that is to 
//increase the amount of space in the array

/*
 * REQUIREMENTS BEFORE YOU RUN THE CODE: Make sure you get the html file from torolink. Before grabbing the html file load all the
 clubs that there are by hitting the load more button on the bottom of the screen. After that grab inspect one of the clubs by right clicking.
When inspecting the html code please find this div id="org-search-results". Once you have found that right click it and hit edit as html.
copy paste it into a txt file.
Now make 1 txt file for the club response, 1 txt file for the club about, 1 txt file for the links.
Code should be up and running just make sure to create the text file.


OH I FORGOT THIS STEP:

Name the txt file where you are going to paste the html code. The name should be helperToCreateList.
The other txt file should be empty and name the 3 txt file this defaultResponse, about, clubLinks
If this does not work make sure to check your File object it should have the correct path to find the txt file. It differs in all computers.
 * 
 * 
 * 
 * 
 */

//@Author: Bryan Cortes

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
//IDEA: So after you have found alt= the characters after that will be the name of the club. 
//I posted the images of the txt to help us vizualize when to start copying characters to turn it into a string.

//DONE so far do not think I will work further in this. Unless the website seems to change

public class transferClubNamesToTxt {

	// s has the string that has the club name

	public static String[] clubNames(String[] names, String s) {

		int count = 0; // Helps us count the number of clubs we have
		String clubName = "";

		for (int i = 0; i < s.length(); i++) {

			// System.out.print(s.charAt(i));
			// The reason we have this if statement is so that we can catch the name of the
			// club.

			if (s.charAt(i) == 'a' && s.charAt(i + 1) == 'l' && s.charAt(i + 2) == 't' && s.charAt(i + 3) == '=') {
				i += 5; // We have this here to help us jump to the start of the string we jump 5
						// because we want to reach the start of the name
				// keep looping until we reached the end. After we reached the end we will know
				// that we have cut out the club name
				while (s.charAt(i) != 34) { // I put 34 because that is the ascii number for "" this will let us know we
											// have reached the end of the club
					clubName += s.charAt(i);
					i++;
				}
				// Because you reached the end of the string, it is time to put it in the names
				// array.
				names[count++] = clubName;
				clubName = "";
			}

		}

		return names;
	}

	// this helps create a delimeter for the excel sheet.
	public static void insertInFile(String[] names, String defaultSentence, String revertSentence, File file)
			throws IOException {

		if (!defaultSentence.contains("blank")) {
			System.out.println("Error no blank statment");
		}
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);

		for (int i = 0; names[i] != null; i++) {
			defaultSentence = defaultSentence.replace("blank", names[i]);
			defaultSentence += "7";
			pw.println(defaultSentence);
			defaultSentence = revertSentence;
		}
		pw.close();

	}

	public static void grabLinks() throws IOException {
		File file = new File("C:/Users/Blue0/Desktop/helperToCreateList.txt");
		Scanner scan = new Scanner(file);
		String[] linksOfClubs = new String[500];
		String s;

		while (scan.hasNextLine()) {
			s = scan.nextLine();
			linksOfClubs = makeListOfLinks(linksOfClubs, s);
		}

		file = new File("C:/Users/Blue0/Desktop/clubLinks.txt");

		insertLinkInFile(linksOfClubs, file);
	}

	// Concating the links together
	public static void insertLinkInFile(String[] LOC, File file) throws IOException {
		String link = "https://torolink.csudh.edu/organization/";
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		for (int i = 0; LOC[i] != null; i++) {
			link += LOC[i];
			link += "7";
			pw.println(link);
			link = "https://torolink.csudh.edu/organization/"; // reverting
		}
		pw.close();

	}

	// LOC stands for link of clubs
	public static String[] makeListOfLinks(String[] LOC, String s) {

		int count = 0;
		String link = "";

		for (int i = 0; i < s.length(); i++) {

			// if we find this this means we have found the link
			if (s.charAt(i) == '/' && s.charAt(i + 1) == 'o' && s.charAt(i + 2) == 'r' && s.charAt(i + 3) == 'g'
					&& s.charAt(i + 4) == 'a'
					&& s.charAt(i + 5) == 'n' && s.charAt(i + 6) == 'i' && s.charAt(i + 7) == 'z') {
				i += 14;

				while (s.charAt(i) != 34) {
					link += s.charAt(i); // building the link of the club
					i++;
				}
				LOC[count++] = link;
				link = ""; // resetting link

			}
		}

		return LOC;

	}

	public static void main(String[] args) throws IOException {

		File file = new File("C:/Users/Blue0/Desktop/helperToCreateList.txt");
		Scanner scan = new Scanner(file);
		String s;
		String[] namesOfClubs = new String[500];
		String a = null;

		String response = "For more information about blank please click on the globe which contains the link that has their information.";
		String revertResponse = "For more information about blank please click on the globe which contains the link that has their information.";
		File responseFile = new File("C:/Users/Blue0/Desktop/defaultResponse.txt");

		String about = "About blank";
		String revertAbout = "About blank";
		File aboutFile = new File("C:/Users/Blue0/Desktop/about.txt");

		while (scan.hasNextLine()) {
			s = scan.nextLine();
			System.out.println(s);
			namesOfClubs = clubNames(namesOfClubs, s);
		}
		insertInFile(namesOfClubs, response, revertResponse, responseFile);
		insertInFile(namesOfClubs, about, revertAbout, aboutFile);

		grabLinks(); // grabs the links

	}

}
