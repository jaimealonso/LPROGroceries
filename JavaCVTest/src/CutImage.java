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
		int lines = 4;
		int heightTemp = orig.height() / lines;
		int columns = 3;
		int widthTemp = orig.width() / columns;

		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {

				CvRect r = new CvRect();

				r.x(j * widthTemp);
				r.y(i * heightTemp);
				r.width(widthTemp);
				r.height(heightTemp);

				// After setting ROI (Region-Of-Interest) all processing will
				// only
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

}
