import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

import java.util.Comparator;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.*;



public class ImageCompare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IplImage orig = cvLoadImage("fridge\\actimel.jpg");
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
				String temp = "fridge\\cropped_actimel" + count + ".jpg";
				cvSaveImage(temp, cropped);
				Lena.demo("fridge\\actimel.jpg", temp);
				System.out.println("Iteration " + i);
			}
		}		
		
		for(int i=0;i<count;i++){
			for(int j=0;j<4;j++){
				double value = SSIM.getSimilarity("fridge\\cropped" + (1 +j) +".jpg", "fridge\\cropped_actimel" + (1 + i) + ".jpg");
			System.out.println(value);
			}
			System.out.println("-----------");
		}
		
		
	}

}
