import org.bytedeco.javacpp.DoublePointer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import java.util.Arrays;
import java.util.LinkedList;
import model.Object;

public class CutImage {

	/**
	 * @param args
	 */
	public static LinkedList<Object> getObjects(String URI) {
		// TODO Auto-generated method stub

		IplImage orig = cvLoadImage(URI);
		// Creating rectangle by which bounds image will be cropped
		int lines = 2;
		int heightTemp = orig.height() / lines;
		int columns = 2;
		int widthTemp = orig.width() / columns;
		int count = 0;
		String[] parts = URI.split(".");
		LinkedList<Object> objectList = new LinkedList<Object>();

		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				count++;
				CvRect r = new CvRect();

				r.x(j * widthTemp);
				r.y(i * heightTemp);
				r.width(widthTemp);
				r.height(heightTemp);

				// After setting ROI (Region-Of-Interest) all processing will
				// only
				// be done on the ROI
				cvSetImageROI(orig, r);
				IplImage cropped = cvCreateImage(cvGetSize(orig), IPL_DEPTH_8U,
						1);
				// Copy original image (only ROI) to the cropped image
				cvCvtColor(orig, cropped, CV_BGR2GRAY);
				String temp = parts[0] + "_" + count + ".jpg";
				cvSaveImage(temp, cropped);
				Lena.demo(URI, temp);
				Object object = new Object("img" + count, temp);
				objectList.add(object);
			}
		}
		return objectList;

		// for(int i=0;i<count;i++){
		//
		// System.out.println(SSIM.getSimilarity(URI, "fridge\\cropped" + (1 +
		// i) + ".jpg"));
		// }

	}

}
