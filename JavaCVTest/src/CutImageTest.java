import org.bytedeco.javacpp.DoublePointer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import java.util.Arrays;

public class CutImageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IplImage orig = cvLoadImage("fridge\\original.jpg");
		// Creating rectangle by which bounds image will be cropped
		int lines = 2;
		int heightTemp = orig.height() / lines;
		int columns = 2;
		int widthTemp = orig.width() / columns;
		int count = 0;

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
				cvCvtColor(orig, cropped,CV_BGR2GRAY);
				String temp = "fridge\\cropped" + count + ".jpg";
				cvSaveImage(temp, cropped);
				Lena.demo("fridge\\original.jpg", temp);
				System.out.println("Iteration " + i);
			}
		}

		
		
		for(int i=0;i<count;i++){
			
			System.out.println(SSIM.getSimilarity("fridge\\cropped1.jpg", "fridge\\cropped" + (1 + i) + ".jpg"));
		}
		
		
		
	}

}
