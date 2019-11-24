package com.sendlyme.modals;

public class FileListModal {

	String id;
	String name;
	String status;
	String fileSize;
	
	
	public FileListModal(String id, String name, String status, String fileSize) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.fileSize = fileSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public FileListModal(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
