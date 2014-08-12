package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.example.lprogroceries.db.helper.DatabaseHelper;
import com.example.lprogroceries.db.model.Object;
import com.example.lprogroceries.imageSSIM.CutImage;
import com.example.lprogroceries.imageSSIM.ImageCompare;


public class ImageDecode {

	private DatabaseHelper db;
	private List<Object> completeList;
	
	//////FIELDS/////
	
	public ImageDecode(List<Object> completeList){
		this.completeList = completeList;
	}
	
	
	public LinkedList<Object> getFoundObjects(String URI,  long id){
		LinkedList<Object> listImage = CutImage.getObjects(URI);
		List<Object> listDB = db.getAllObjectsByList(id);
		LinkedList<Object> listFound = ImageCompare.compareList(listImage, listDB);
		

		return listFound;
	}
	
	
}
