import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.IplImage;

public class TemplateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IplImage src = cvLoadImage("figures\\cropped2.jpg");
		IplImage tmp = cvLoadImage("figures\\cropped1.jpg");
		IplImage result = cvCreateImage(
				cvSize(src.width() - tmp.width() + 1,
						src.height() - tmp.height() + 1), IPL_DEPTH_32F, 1);
		cvZero(result);
		cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);
		cvShowImage("RESULT", result);
		cvWaitKey();
	}

}
