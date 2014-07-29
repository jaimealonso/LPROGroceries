package com.example.lprogroceries.db.model;

public class Object {
	
	int id;
	String object_name;
	String object_ref;
 
	
	
	public Object(int id, String name, String ref) {
		super();
		this.id = id;
		this.object_name = name;
		this.object_ref = ref;
	}
	
	public Object(){
		
	}
	
	public Object(String name, String ref){
		this.object_name = name;
		this.object_ref = ref;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return object_name;
	}
	public void setName(String name) {
		this.object_name = name;
	}
	public String getRef() {
		return object_ref;
	}
	public void setRef(String ref) {
		this.object_ref = ref;
	}
	
	
	

}