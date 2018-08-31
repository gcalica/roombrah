//Import the following libraries to be used in this class:
import java.awt.Color;
import java.util.Random;

//Roombrah class
public class Roombrah {

	//Member Variables
	private EZImage roombrahPic; //Roombrah picture
	private int posX, posY; //To keep track of the Roombrah's x and y coordinates

	public Roombrah(String filename, int x, int y) { //Default constructor
		posX = x; //Assign the given parameter x to 'posX'
		posY = y; //Assign the given parameter y to 'posY'

		//Load in an EZImage with the specified filename in posX and posY as its x and y coordinates, respectively, and assign the image to 'roombrahPic'
		roombrahPic = EZ.addImage(filename, posX, posY); 
	}

	//Returns the Roombrah's x coordinate
	public int getX() {
		return posX; 
	}

	//Returns the Roombrah's y coordinate
	public int getY() {
		return posY;
	}

	//Method to move the Roombrah
	public void move(int dirx, int diry) {
		posX += dirx; //posX changes in relation to dirx
		posY += diry; //posY changes in relation to diry

		roombrahPic.translateTo(posX, posY); //Sets the center of roombrahPic to posX, posY
	}

	//Traces the Roombrah by creating EZCircles on its path
	public void trace() {
		Random rand = new Random(); //Create a random generator 'rand'
		
		Color rainbow = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 60); //Create a new color with random int r, g, b values from the random generator
		EZCircle trace = EZ.addCircle(posX, posY, 16, 16, rainbow, true); //Create a transparent EZCircle at the roombrah's posX and posY
			trace.pushBackOneLayer(); //Push back 'trace' so that it goes underneath the roombrahPic
	}

	//isPointinElement method
	public boolean isInside(int x, int y) {
		return roombrahPic.isPointInElement(x, y); //Returns true if x, y are within roombrahPic
	}

}
