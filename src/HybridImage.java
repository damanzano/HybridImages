import processing.core.PApplet;
import processing.core.PImage;


public class HybridImage extends PApplet{
	PImage image;
	PImage image2;
	PImage resImage;

	public void setup() {
		image = loadImage("../data/retrato1.jpg");
		image2 = loadImage("../data/retrato2.jpg");

		
	}
	
	int convolution(int x, int y, float[][] matrix, int matrixsize, PImage img) {
		  float rtotal = 0.0f;
		  float gtotal = 0.0f;
		  float btotal = 0.0f;
		  int offset = matrixsize / 2;
		  // Loop through convolution matrix
		  for (int i = 0; i < matrixsize; i++){
		    for (int j= 0; j < matrixsize; j++){
		      // What pixel are we testing
		      int xloc = x+i-offset;
		      int yloc = y+j-offset;
		      int loc = xloc + img.width*yloc;
		      // Make sure we have not walked off the edge of the pixel array
		      loc = constrain(loc,0,img.pixels.length-1);
		      // Calculate the convolution
		      // We sum all the neighboring pixels multiplied by the values in the convolution matrix.
		      rtotal += (red(img.pixels[loc]) * matrix[i][j]);
		      gtotal += (green(img.pixels[loc]) * matrix[i][j]);
		      btotal += (blue(img.pixels[loc]) * matrix[i][j]);
		    }
		  }
		  // Make sure RGB is within range
		  rtotal = constrain(rtotal,0,255);
		  gtotal = constrain(gtotal,0,255);
		  btotal = constrain(btotal,0,255);
		  // Return the resulting color
		  return color(rtotal,gtotal,btotal);
		}
}
