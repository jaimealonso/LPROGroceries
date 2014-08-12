package model;

import java.util.Date;

public class Capture {

	int id;
	int idFoundList;
	int idMissingList;
	String capture_ref;
	String created_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdFoundList() {
		return idFoundList;
	}

	public void setIdFoundList(int idFoundList) {
		this.idFoundList = idFoundList;
	}

	public int getIdMissingList() {
		return idMissingList;
	}

	public void setIdMissingList(int idMissingList) {
		this.idMissingList = idMissingList;
	}

	public String getRef() {
		return capture_ref;
	}

	public void setRef(String ref) {
		this.capture_ref = ref;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Capture(int id, int idFoundList, int idMissingList, String ref,
			String created_at) {
		super();
		this.id = id;
		this.idFoundList = idFoundList;
		this.idMissingList = idMissingList;
		this.capture_ref = ref;
		this.created_at = created_at;
	}

	public Capture(int idFoundList, int idMissingList, String ref,
			String created_at) {
		super();
		this.idFoundList = idFoundList;
		this.idMissingList = idMissingList;
		this.capture_ref = ref;
		this.created_at = created_at;
	}

	public Capture() {
	}

}