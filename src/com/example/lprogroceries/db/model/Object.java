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

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Object other = (Object) obj;
		if (object_name == null) {
			if (other.object_name != null)
				return false;
		} else if (!object_name.equals(other.object_name))
			return false;
		return true;
	}
	
	public String toString(){
		return this.object_name;
	}
	
	

}