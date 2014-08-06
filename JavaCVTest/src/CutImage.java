import org.bytedeco.javacpp.DoublePointer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import java.util.Arrays;

public class CutImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IplImage orig = cvLoadImage("lena.jpg");
		// Creating rectangle by which bounds image will be cropped


		for (int i = 0; i < 4; i++) {

			CvRect r = new CvRect();
			if (i < 2)
				r.x(0);
			else
				r.x(256);
			if (i == 1 || i == 3)
				r.y(256);
			else
				r.y(0);
			r.width(256);
			r.height(256);

			// After setting ROI (Region-Of-Interest) all processing will only
			// be done on the ROI
			cvSetImageROI(orig, r);
			IplImage cropped = cvCreateImage(cvGetSize(orig), orig.depth(),
					orig.nChannels());
			// Copy original image (only ROI) to the cropped image
			cvCopy(orig, cropped);
			String temp = "cropped" + i + ".jpg";
			cvSaveImage(temp, cropped);
			Lena.demo("lena.jpg", temp);
			System.out.println("Iteration " + i);
		}

	}

}
