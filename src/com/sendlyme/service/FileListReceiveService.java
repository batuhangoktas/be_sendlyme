package com.sendlyme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sendlyme.modals.FileListModal;
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
		
        	String sourceUserId = RedisUtil.getInstance().getSessionCoupleId(sessionId, userId);
        	
        	if(sourceUserId!=null)
        	{
        	List<String> fileIdList = RedisUtil.getInstance().getFileListFromSourceId(sourceUserId);
        	List<FileListModal> fileList = RedisUtil.getInstance().getFileListInfo(fileIdList);
        	
        	
        	response.setFileListModalList(fileList);
        	response.setStatus(true);
        	}
        	
        	return response;
	}
}
