package com.sendlyme.modals;

import java.util.List;


public class FileListAndTimeModal {

	List<FileListModal> fileListModalList;
	String time;
	boolean timeStatus;
	
	public List<FileListModal> getFileListModalList() {
		return fileListModalList;
	}
	public void setFileListModalList(List<FileListModal> fileListModalList) {
		this.fileListModalList = fileListModalList;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isTimeStatus() {
		return timeStatus;
	}
	public void setTimeStatus(boolean timeStatus) {
		this.timeStatus = timeStatus;
	}
	
}
