package com.example.lprogroceries.imageSSIM;

import org.bytedeco.javacpp.DoublePointer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import java.util.Arrays;
///////////////////////////////////////////////////////////////////
//*                                                             *//
//* As the author of this code, I place all of this code into   *//
//* the public domain. Users can use it for any legal purpose.  *//
//*                                                             *//
//*             - Dave Grossman                                 *//
//*                                                             *//
///////////////////////////////////////////////////////////////////
public class Lena
{
    public static void main(String[] args)
    {
        System.out.println("STARTING...\n");
        demo("lena.jpg", "eyes.jpg");
        System.out.println("ALL DONE");
    }

    public static void demo(String main, String temp)
    {
    	IplImage src = cvLoadImage(main,0);
    	IplImage tmp = cvLoadImage(temp,0);	
    		
    	IplImage result = cvCreateImage(cvSize(src.width()-tmp.width()+1, src.height()-tmp.height()+1), IPL_DEPTH_32F, 1);
    	cvZero(result);

    	//Match Template Function from OpenCV
    	cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);
    			
    	double[] min_val = new double[2];
    	double[] max_val = new double[2];

    	DoublePointer minval= new DoublePointer();
    	DoublePointer maxval= new DoublePointer();
    	
    	CvPoint minLoc = new CvPoint();
    	CvPoint maxLoc = new CvPoint();

    	//Get the Max or Min Correlation Value
    	cvMinMaxLoc(result, min_val, max_val);
    	cvMinMaxLoc(result, minval, maxval, minLoc, maxLoc, null);
    	
    	System.out.println(Arrays.toString(min_val));
    	System.out.println(Arrays.toString(max_val));
    			
    	CvPoint point = new CvPoint();
    	point.x(maxLoc.x()+tmp.width());
    	point.y(maxLoc.y()+tmp.height());

    	cvRectangle(src, maxLoc, point, CvScalar.WHITE, 2, 8, 0);//Draw a Rectangle for Matched Region

    	cvShowImage("Lena Image", src);
    	cvWaitKey(0);
    	cvReleaseImage(src);
    	cvReleaseImage(tmp);
    	cvReleaseImage(result);
    	
    	
    	
    }
    
}