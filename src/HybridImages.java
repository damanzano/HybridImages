import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class HybridImages extends PApplet {

	PImage image0;
	PImage image1;
	PImage lpImage;
	PImage hpImage;
	PImage resImage;
	PImage halfResImage;

	public void setup() {
		image0 = loadImage("../data/retrato1.jpg");
		image0.resize(120, 120);
		image0.filter(GRAY);

		image1 = loadImage("../data/retrato2.jpg");
		image1.resize(120, 120);
		image1.filter(GRAY);
		
		// Lowpass filter is a 3x3 gaussian filter
		float[][] lowPassFilter = { 
				{ 1 / 16f, 1 / 8f, 1 / 16f },
				{ 1 / 8f, 1 / 4f, 1 / 8f }, 
				{ 1 / 16f, 1 / 8f, 1 / 16f } };
		float[][] lowPassFilter2 = { 
				{ 2 / 16f, 2 / 8f, 2 / 16f },
				{ 1 / 8f, 1 / 4f, 2 / 8f }, 
				{ 2 / 16f, 2 / 8f, 2 / 16f } };
		
		
		// High-pass filter is calculates as 1- Lowwpass
		float[][] highPassFilter = { 
				{ 15 / 16f, 7 / 8f, 15 / 16f },
				{ 7 / 8f, 3 / 4f, 7 / 8f }, 
				{ 15 / 16f, 7 / 8f, 15 / 16f } };

		lpImage = convolution(lowPassFilter, image0);
		hpImage = convolution(highPassFilter, image1);

		resImage = sumImages(lpImage, hpImage);
		//halfResImage = 

		size(image0.width * 4, 50 + image0.height * 2);
		
		image(image0, 0, 10);
		image(image1, image0.width, 10);
		image(lpImage, 2 * image0.width, 10);
		image(hpImage, 3 * image0.width, 10);

		//ode(PApplet.CENTER);
		image(resImage, 0, image0.height + 50);
		resImage.resize(resImage.width/2, resImage.height/2);
		image(resImage, image0.width+100, image0.height + 50);

	}

	public PImage convolution(float[][] matrix, PImage img) {
		PImage output = createImage(img.width, img.height, PApplet.RGB);
		output.loadPixels();
		// Begin our loop for every pixel
		for (int x = 0; x < output.width; x++) {
			for (int y = 0; y < output.height; y++) {
				// Each pixel location (x,y) gets passed into a function called
				// convolution()
				// which returns a new color value to be displayed.
				int pixelValue = pixelConvolution(x, y, matrix, matrix.length,
						img);
				int loc = x + y * output.width;
				output.pixels[loc] = pixelValue;
			}
		}
		output.updatePixels();
		return output;

	}

	private int pixelConvolution(int x, int y, float[][] matrix,
			int matrixsize, PImage img) {
		float rtotal = 0.0f;
		float gtotal = 0.0f;
		float btotal = 0.0f;
		int offset = matrixsize / 2;
		// Loop through convolution matrix
		for (int i = 0; i < matrixsize; i++) {
			for (int j = 0; j < matrixsize; j++) {
				// What pixel are we testing
				int xloc = x + i - offset;
				int yloc = y + j - offset;
				int loc = xloc + img.width * yloc;
				// Make sure we have not walked off the edge of the pixel array
				loc = constrain(loc, 0, img.pixels.length - 1);
				// Calculate the convolution
				// We sum all the neighboring pixels multiplied by the values in
				// the convolution matrix.
				rtotal += (red(img.pixels[loc]) * matrix[i][j]);
				gtotal += (green(img.pixels[loc]) * matrix[i][j]);
				btotal += (blue(img.pixels[loc]) * matrix[i][j]);
			}
		}
		// Make sure RGB is within range
		rtotal = constrain(rtotal, 0, 255);
		gtotal = constrain(gtotal, 0, 255);
		btotal = constrain(btotal, 0, 255);
		// Return the resulting color
		return color(rtotal, gtotal, btotal);
	}

	private PImage sumImages(PImage lpimage, PImage hpimage) {
		PImage output = createImage(lpimage.width, lpimage.height, PApplet.RGB);
		output.loadPixels();
		// Begin our loop for every pixel
		for (int x = 0; x < output.width; x++) {
			for (int y = 0; y < output.height; y++) {
				int loc = x + y * output.width;
				float r = (red(lpimage.pixels[loc])*0.5f + red(hpimage.pixels[loc])*0.5f);
				float g = (green(lpimage.pixels[loc])*0.5f + green(hpimage.pixels[loc])*0.5f);
				float b = (blue(lpimage.pixels[loc])*0.5f + blue(hpimage.pixels[loc])*0.5f);

				r = constrain(r, 0, 255);
				g = constrain(g, 0, 255);
				b = constrain(b, 0, 255);
				
				output.pixels[loc] = color(r, g, b);
			}
		}
		output.updatePixels();
		return output;
	}
	
}
