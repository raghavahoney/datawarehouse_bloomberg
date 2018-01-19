package com.progresssoft.datawarehouse.entity;

import java.io.Serializable;

public class MyKey implements Serializable {

	private String id;
	private String fileName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
