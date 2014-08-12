package com.example.lprogroceries.imageSSIM;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.*;
import com.example.lprogroceries.db.model.Object;

public class ImageCompare {

	/**
	 * @param listDB
	 * @param args
	 */

	public static LinkedList<Object> compareList(LinkedList<Object> listImage,
			List<Object> listDB) {
		// TODO Auto-generated method stub

		for (int i = 0; i < listImage.size(); i++) {
			String imageURI = listImage.get(i).getRef();
			for (int j = 0; j < listDB.size(); j++) {
				String dbURI = listDB.get(j).getRef();
				double value = SSIM.getSimilarity(imageURI, dbURI);
				System.out.println(value);
			}
			System.out.println("-----------");

		}

		//Create the method to fill linkedlist
		
		
		return null;
	}

}
