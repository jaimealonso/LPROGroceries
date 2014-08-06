package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.example.lprogroceries.db.model.Object;


public class ImageDecode {

	private List<Object> completeList;
	
	//////FIELDS/////
	
	public ImageDecode(List<Object> completeList){
		this.completeList = completeList;
	}
	
	
	public LinkedList<Object> getFoundObjects(){
		LinkedList<Object> list = new LinkedList<Object>();
		
		//Picking 4 random objects
		Random rnd = new Random();
		
		for(int i = 0; i < 4; i++){
			list.add(completeList.get(rnd.nextInt(completeList.size())));
		}
		
		return list;
	}
	
	
}
