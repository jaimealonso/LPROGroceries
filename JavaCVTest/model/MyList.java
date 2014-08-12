package model;

import java.util.LinkedList;

public class MyList {

	int id;
	String list_name;
	LinkedList<Object> objectList;
	
	

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LinkedList<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(LinkedList<Object> objectList) {
		this.objectList = objectList;
	}

	

	public MyList() {

	}

	public MyList(String list_name, LinkedList<Object> objectList) {
		super();
		this.list_name = list_name;
		this.objectList = objectList;
	}

	public MyList(int id, String list_name, LinkedList<Object> objectList) {
		super();
		this.id = id;
		this.list_name = list_name;
		this.objectList = objectList;
	}

	
}