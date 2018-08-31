//Import the following libraries to be used in this class:
import java.awt.Color;
import java.io.FileReader;
import java.util.Scanner;

//YouTube Demo: https://youtu.be/msCt_rNQeWY

//Main class
public class Main { 
	
	//Wall and Coin arrays
	static EZImage[] wallArray = new EZImage[200]; //Declare an array called wallArray of type EZImage with a fixed size of 200
	static EZImage[] coinArray = new EZImage[100]; //Declare an array called coinArray of type EZImage with a fixed size of 100
	
	static String name; //String that stores the value from the input of inputName()
	
	//Main method
	public static void main(String[] args) throws java.io.IOException { //Handle an IOException by throwing it

		//Create a scanner and a file reader that scans and reads the file 'level.txt'
		Scanner fileScanner = new Scanner(new FileReader("level.txt")); //use "levelCopy.txt" for quick game win
		
		//These will determine how many rows and columns will make up our level map
		final int WIDTH = fileScanner.nextInt(); //Reads the first integer of level.txt to determine the fixed value for int WIDTH (columns)
		final int HEIGHT = fileScanner.nextInt(); //Reads the second integer of level.txt to determine the fixed value for int HEIGHT (rows)

		//Because the images in our program are 32x32, we want to setup an EZ window that scales the window's width and height by 32
		final int SCREEN_WIDTH = WIDTH * 32; //Initializes SCREEN_WIDTH and assigns the value to it by WIDTH * 32
		final int SCREEN_HEIGHT = HEIGHT * 32; //Initializes SCREEN_HEIGHT and assigns the value to it by Height * 32
		
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT); //Initialize an EZ window based on SCREEN_WIDTH and SCREEN_HEIGHT for its width and height, respectively.
		EZ.setBackgroundColor(new Color(240, 234, 218)); //For aesthethic purpose, so that the background color matches the color of 'floor.png' when its loading in or when a coin has been removed
		EZSound loading = EZ.addSound("loading.wav"); //Load the sound file "loading.wav" and assign it to loading of type EZSound; sound when "level.txt" is loading in
			loading.loop(); //Loop 'loading'
			
		int wallArrayCount = 0; //Declare an int variable named wallArrayCount and initialize to 0; this will keep track of how many actual wall pieces are read from level.txt and stored in wallArray
		int coinArrayCount = 0; //Declare an int variable named coinArrayCount and initialize to 0; this will keep track of how many actual coin pieces are read from level.txt and stored in coinArray

		String charInput = fileScanner.nextLine(); //Reads the next line of string and stores it into the variable charInput of String type
		
		for(int row = 0; row < HEIGHT; row++) { //Read each row of level.txt

			charInput = fileScanner.nextLine(); //Examines each character from each row of level.txt and stores it to charInput

			for (int column = 0; column < charInput.length(); column++) { //Read each column of level.txt

				//Converts charInput from String to char by returning the char value of charInput at the corresponding index value of int column and store it into the variable ch of char type
				char ch = charInput.charAt(column); 

				switch(ch) { //Switch statement - If 'ch' equals a specific char case, then execute its respective case
				case 'W': //If ch is the char 'W', then...
					wallArray[wallArrayCount] = EZ.addImage("redwood.png", 16 + (column * 32), 16 + (row * 32)); //Load the image "redwood.png" to the specified x and y coordinates and store it into the array wallArray, with each image being stored in an array index corresponding to the value of wallArrayCount
					wallArrayCount++; //Increment wallArrayCount to indicate that the total number of wall pieces read and stored in our wallArray has increased by one
					break; //Terminates the switch statement and the flow of control jumps back up to the for loop
				case 'C': //If ch is the char 'C", then...
					coinArray[coinArrayCount] = EZ.addImage("coin.png", 16 + (column * 32), 16 + (row * 32)); //Load the image "coin.png" to the specified x and y coordinates and store it into the array coinArray, with each image being stored in an array index corresponding to the value of coinArrayCount
					coinArrayCount++; //Increment coinArrayCount to indicate that the total number of coin pieces read and stored in our coinArray has increased by one
					break; //Terminates the switch statement and the flow of control jumps back up to the for loop
				case '.': //If ch is the char '.", then...
					EZ.addImage("floor.png", 16 + (column * 32), 16 + (row * 32)); //Load the image "floor.png" to the specified x and y coordinates
					break; //Terminates the switch statement and the flow of control jumps back up to the for loop
				default: //Default case, executes when none of the cases in the switch statement is met
					break; //Terminates the switch statement and the flow of control jumps back up to the for loop
				} //close switch(ch)

			} //close for(column) 
			
		} //close for(row)	
		
		loading.stop(); //Stop looping 'loading'
		fileScanner.close(); //Close fileScanner
		
		System.out.println("Total Number of Walls: " + wallArrayCount); //Print the total number of wall pieces read from level.txt and stored in wallArray
		System.out.println("Total Number of Coins: " + coinArrayCount); //Print the total number of coin pieces read from level.txt and stored in coinArray
		
		//Initializes 'intro' of type EZTExt that tells the player to enter their name in the console.
		EZText intro = EZ.addText(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, "ENTER YOUR NAME IN THE CONSOLE!", Color.red, 45); 
			intro.setFont("SuperMario256.ttf"); //Sets the font of intro to "SuperMario256.ttf"
		
		//The player types in their name into the console which gets stored into String 'name'
		inputName(); //Call inputName();
		
		intro.hide(); //Hide 'intro' from the screen
		
		//Load in EZSound sound files
		EZSound coinSound = EZ.addSound("coinSound.wav"); //Load in "coinSound.wav" and assign it to 'coinSound' of type EZSound; sound when the Roombrah collects a coin
		EZSound bounceSound = EZ.addSound("bounce.wav"); //Load in "bounce.wav" and assign it to 'bounceSound' of type EZSound; sound when the Roombrah bounces off a wall
		EZSound gameWin = EZ.addSound("gameWin.wav"); //Load in "gameWin.wav" and assign it to 'gameWin' of type EZSound; sound when the Roombrah has collected all coins
		EZSound backgroundMusic = EZ.addSound("backgroundMusic.wav"); //Load in "backgroundMusic.wav" and assign it to 'backgroundMusic' of type EZSound; background music for the game
			backgroundMusic.loop(); //Loop 'backgroundMusic'
		
		//Picture of Peach from Mario that will appear when the Roombrah has 'won' the game
		EZImage peach = EZ.addImage("peach.png", WIDTH + 64, HEIGHT + 720); //Load in "peac.png" and assign it to 'peach' of type EZImage
			peach.hide(); //Initially hide 'peach' at the start of the game
			peach.scaleTo(0.1); //Scale 'peach' to 0.1
			
		EZText coinCountText = EZ.addText(100, 16, "Coins: 0", Color.black, 32); //Initialize 'coinCountText' of type EZText to set a message to state that we have 0 coins at the start of the game.
			coinCountText.setFont("SuperMario256.ttf"); //Set font of 'coinCountText' to "SuperMario256.ttf"
		EZText gameWinText = EZ.addText(peach.getXCenter() + 450, peach.getYCenter() - 160, "", new Color(255, 90, 235), 35); //Initialize gameWinText' of type EZText with "" as the msg that will serve as the holder for our game win message
			gameWinText.hide(); //Initially hide 'gameWinText' at the start of the game
			gameWinText.setFont("SuperMario256.ttf"); //Set font of 'gameWinText' to "SuperMario256.ttf"
			gameWinText.setMsg("Thanks for cleaning the room " + name + "!"); //Set the message of gameWinText to "Thanks for cleaning the room " + name, with 'name' being the name that the user has entered at the start of the game
			
		//Create a Roombrah object called 'roombrah' and assign the specified parameters to it	
		Roombrah roombrah = new Roombrah("Roombrah.png", 64, 64); //"Roombrah.png" will be the img for the Roombrah, starting point at 64, 64
		
		//The roombrah's x and y directions
		int dirX = 4;  //roombrah's x direction
		int dirY = 4; //roombrah's y direction
		
		int coinCount = 0; //Counter for how many coins the Roombrah has collected; initialize to 0
		
		boolean running = true; //Set 'running' to true to indicate that the game is still running
		
		//Game Updater Loop
		while(running) { //While 'running' == true, ...
			roombrah.move(dirX, dirY); //Call the move method on the roombrah object, which moves the roombrah(translateTo) to (dirX, dirY)
			roombrah.trace(); //Calls the trace method for 'roombrah' which traces the movement of 'roombrah' by creating EZCircles
			for(int i = 0; i < wallArrayCount; i++) { //Read each wall piece that was stored in the wallArray
				if(wallArray[i].isPointInElement(roombrah.getX() + dirX, roombrah.getY())) { //If the roombrah's (x + dirX, y) coordinates are within the wallArray at a particular index, then...
					bounceSound.stop(); //Stop bounceSound to reset; If bounceSound was getting triggered too much within a period of time, it would not play or play only once.
					bounceSound.play(); //Play bounceSound to indicate that the roombrah has bounced off of a wall
					dirX = -dirX; //Reverse dirX (roombrah's x direction)
				}
				if(wallArray[i].isPointInElement(roombrah.getX(), roombrah.getY() + dirY)) { //If the roombrah's (x, y + dirY) coordinates are within the wallArray at a particular index, then...
					bounceSound.stop(); //Stop 'bounceSound' to reset; If bounceSound was getting triggered too much within a period of time, it would not play or play only once.
					bounceSound.play(); //Play 'bounceSound' to indicate that the roombrah has bounced off of a wall
					dirY = -dirY; //Reverse dirY (roombrah's y direction)
				}
			} //close for(wallArray) loop
			
		
			//Coin collision detection
			for(int i = 0; i < coinArrayCount; i++) { //Read each coin piece that was stored in the coinArray
				if(roombrah.isInside(coinArray[i].getXCenter(), coinArray[i].getYCenter())) { //If a particular coin piece is within the roombrah object, then...
					coinArray[i].translateTo(0, 0); //Move that particular coin to 0,0
					coinSound.stop(); //Stop 'coinSound' to reset; If coinSound was getting triggered too much within a period of time, it would not play or play only once.
					coinSound.play(); //Play 'coinSound' to indicate that the roombrah has collected a coin
					coinCount++; //Increment 'coinCount' by one to indicate that the roombrah has collected a coin
					coinCountText.setMsg("Coins: " + coinCount); //Set the msg of coinCountText to "Coins: " + coinCount, with coinCount being the total amount of coins that the roombrah has collected so far
				}
			} //close for(coinArray) loop
			
			//Once the roombrah has collected all coins, the roombrah stops moving and we move on to...
			if(coinCount == coinArrayCount) { //If the amount of coins collected equals the total amount of coins, then...
				running = false; //Set running to false to break the game updater loop
				
				backgroundMusic.stop(); //Stops the 'backgroundMusic'
				
				peach.show(); //Reveals EZImage peach
				gameWinText.show(); //Reveals EZTExt gameWinText
				
				peach.pullToFront(); //Pull peach to the front so that the trace circles are not on top of the img
				gameWinText.pullToFront(); //Pull gameWintext to the front so that the trace circles are not on top of the text
				
				gameWin.play(); //Play gameWin sound file
			}
			
			EZ.refreshScreen(); //Constantly refreshes the screen
			
		} //close while(running) game updater loop

	} //close main method 
	
	//Setup a system where the player types in their name into the console and stores it into 'name'
	//Once the roombrah has collected all the coins, 'gameWinText' shows a message on the screen saying "Thanks for cleaning the room " + 'name', with 'name' being the name input from this method
	static void inputName() {
		EZSound error = EZ.addSound("error.wav"); //Load in "error.wav" and assign it to 'error' of type EZSound; this sound is used to indicate to the player that the name they have entered is too long
		
		Scanner s = new Scanner(System.in); //Create a scanner
		
		System.out.println("Enter your first name: "); //Tells the player to enter their first name in the console
		
		name = s.next(); //Reads the next String that the user has entered and assigns it to 'name'
		
		//We want to make sure that the name entered by the user is not too long that it goes off the screen, so we set a limit so that the length of 'name' is 9 or less characters
		while(name.length() > 9) { //As long as the length of 'name' is longer than 9, keep making the user re-enter a different name
			error.play(); //Play 'error'
			System.out.println("The name is too long! Must be 9 or less characters. Re-enter a new name:"); //Warn the user that the name they have entered is too long
			name = s.next(); //Reads the next line of String that the user has entered and reassigns it to 'name'
		}
		
		s.close(); //Close 's' Scanner
	}
	
} //close Main{} class
