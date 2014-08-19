package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import android.content.Context;
import android.util.Log;

import com.example.lprogroceries.db.model.Object;
import com.example.lprogroceries.features.FeatureTest;


public class ImageDecode{

	private List<Object> completeList;
	private String photo_uri;
	private Context appContext;
	
	//////FIELDS/////
	
	public ImageDecode(List<Object> completeList, String photo_uri){
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		this.completeList = completeList;
		this.photo_uri = photo_uri;
       

	}
	
	public LinkedList<Object> getFoundObjects(){
		
		LinkedList<Object> list = new LinkedList<Object>();
		
		ArrayList<Mat> userObjects = new ArrayList<Mat>();
		
		
		//IMPORTANT: index numbers in "completeList" will be the same as in "userObjects"
		for(Object o : completeList){
			Log.e("ImageDecode", "Ref: "+o.getRef());
			Mat temp = Highgui.imread(o.getRef(), Highgui.CV_LOAD_IMAGE_COLOR);
			userObjects.add(temp);
		}
		
		FeatureTest previousDetector = new FeatureTest(userObjects);
		Log.e("ImageDecode", "About to compute images...");
		previousDetector.ComputeImages();
		
		Mat pic = Highgui.imread(photo_uri, Highgui.CV_LOAD_IMAGE_COLOR);
		
		FeatureTest detector = new FeatureTest();
		detector.setFrame(pic);
		detector.DrawMatches();
		detector.ProcessFrame();
		Log.e("ImageDecode", "You're gonna get the results now.");

		for(int i = 0; i < previousDetector.getDescriptors().size(); i++){
			Log.e("ImageDecode", "Let's look for stuff...");
			if (detector.Process(previousDetector.getDescriptors().get(i), 
					"object "+i) && detector.clean()){
				Log.e("ImageDecode", "Yay, I found something!");
				list.add(completeList.get(i));
			}
			else{
				Log.e("ImageDecode", "I didn't find that one :(");
			}
		}
		
		return list;

	}
	
	
}
