package com.sendlyme.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sendlyme.modals.FileListAndTimeModal;
import com.sendlyme.modals.FileListModal;
import com.sendlyme.modals.TimeAndUserModal;
import com.sendlyme.response.FileListReceiveResponse;
import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.utils.RedisUtil;

@RefreshScope
@Service(value = SendlyBeanConstants.SERVICE_FILE_LIST_RECEIVE)
@Scope(value = "request", proxyMode=ScopedProxyMode.INTERFACES)
public class FileListReceiveService {

	@Autowired
	ApplicationContext context;
	
	public FileListReceiveResponse getFileListReceive(String sessionId, String userId)
	{
		FileListReceiveResponse response = context.getBean(SendlyBeanConstants.COMPONENT_LIST_RECEIVE_RESPONSE,FileListReceiveResponse.class);
		
		response.setStatus(false);
		
        	TimeAndUserModal timeAndUser = RedisUtil.getInstance().getSessionCoupleId(sessionId, userId);
        	FileListAndTimeModal fileListAndTime = new FileListAndTimeModal();
        	
        	long diff = System.currentTimeMillis() - Long.parseLong(timeAndUser.getSessionTime());
        	
        	long diffMinutes = diff / (60 * 1000) % 60; 
        	
        	if(diffMinutes > 30)
        	{
        		fileListAndTime.setTimeStatus(false);
        		response.setStatus(true);
        	}	
        	else
        	{
        	if(timeAndUser.getUser1Id()!=null)
        	{
        	List<String> fileIdList = RedisUtil.getInstance().getFileListFromSourceId(timeAndUser.getUser1Id());
        	List<FileListModal> fileList = RedisUtil.getInstance().getFileListInfo(fileIdList);
        	
        	fileListAndTime.setTimeStatus(true);
        	fileListAndTime.setTime(String.valueOf(30-diffMinutes));
        	
        	fileListAndTime.setFileListModalList(fileList);
        	response.setStatus(true);
        	}
        	}

        	response.setFileListAndTime(fileListAndTime);
        	return response;
	}
}
