import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import processing.core.PApplet;
import processing.core.PImage;

public class HybridImagesFFT extends PApplet {

	PImage image0;
	PImage image1;
	PImage lpImage;
	PImage hpImage;
	PImage resImage;
	PImage halfResImage;

	public void setup() {
		size(1024,768);
		image0 = loadImage("../data/retrato1.jpg");
		image0.resize(120, 120);
		image0.filter(GRAY);

		image1 = loadImage("../data/retrato2.jpg");
		image1.resize(120, 120);
		image1.filter(GRAY);
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
	      System.out.println( "mat = " + mat.dump() );

	}
	
	/**
	 * Run as a java application instead of an applet
	 * 
	 * @param passedArgs
	 */
	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { 
				"--bgcolor=#666666", 
				"--stop-color=#cccccc",
				"HybridImagesFFT" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
