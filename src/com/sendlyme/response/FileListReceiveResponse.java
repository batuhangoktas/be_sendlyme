package com.sendlyme.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sendlyme.modals.FileListAndTimeModal;

@Component (value = SendlyBeanConstants.COMPONENT_LIST_RECEIVE_RESPONSE)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonTypeName
@XmlRootElement (name = SendlyBeanConstants.COMPONENT_LIST_RECEIVE_RESPONSE)
public class FileListReceiveResponse extends BaseResponse{
	
	@JsonProperty("data")
	FileListAndTimeModal fileListAndTime;

	public FileListAndTimeModal getFileListAndTime() {
		return fileListAndTime;
	}

	public void setFileListAndTime(FileListAndTimeModal fileListAndTime) {
		this.fileListAndTime = fileListAndTime;
	}
	
	

}
