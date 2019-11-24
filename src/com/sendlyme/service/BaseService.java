package com.sendlyme.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sendlyme.response.BaseResponse;
import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.utils.DataUtil;
import com.sendlyme.utils.RedisUtil;

@RefreshScope
@Service(value = SendlyBeanConstants.SERVICE_BASE)
@Scope(value = "request", proxyMode=ScopedProxyMode.INTERFACES)
public class BaseService {

	@Autowired
	private ApplicationContext context;
	
	public BaseResponse getHasSessionSyncService(String ip, String sessionId)
	{
		BaseResponse response = context.getBean(SendlyBeanConstants.COMPONENT_BASE_RESPONSE,BaseResponse.class);
		
		if(DataUtil.ipValidate(ip))
		{
			response.setStatus(RedisUtil.getInstance().hasSessionSync(sessionId));
			return response;
		}
			response.setStatus(false);
			return response;
	}
	
	public BaseResponse getFileUpload(String sessionId, String userId, MultipartFile file)
	{
		BaseResponse response = context.getBean(SendlyBeanConstants.COMPONENT_BASE_RESPONSE,BaseResponse.class);
		
		 if (file.isEmpty()) {
	        	response.setStatus(false);
	        	return response;
	        }

	        try {
	        	
	        	String fileId = "f_"+UUID.randomUUID().toString()+"_"+System.currentTimeMillis();
	        	String UPLOADED_FOLDER = "/FILE/";

	            byte[] bytes = file.getBytes();
	            
	            String originalFileName = file.getOriginalFilename();
	            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."),originalFileName.length()).toLowerCase();
	            
	            originalFileName = originalFileName.replace(originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length()),fileExtension);
	            
	            Path path = Paths.get(UPLOADED_FOLDER + fileId + fileExtension);
	            Files.write(path, bytes);

	            RedisUtil.getInstance().userFile(userId,fileId);
	            RedisUtil.getInstance().saveFile(fileId, originalFileName, path.toString(),"0",bytes.length);
	            response.setStatus(true);
	        	return response;
	            
	        } catch (IOException e) {
	        	  response.setStatus(false);
		        	return response;
	        }

	}
	
	public BaseResponse getTookFile(String fileId)
	{
		BaseResponse response = context.getBean(SendlyBeanConstants.COMPONENT_BASE_RESPONSE,BaseResponse.class);
		if(RedisUtil.getInstance().tookFile(fileId))
		{
			response.setStatus(true);
			
			 String fileWay = RedisUtil.getInstance().getFile(fileId);
			 File file = null;
		     file = new File(fileWay);
		       
		        if(file.exists()){
		           file.delete();
		        }
		}
		
		
		return response;
	}
	
	public BaseResponse getFinishSession(String sessionId)
	{
		BaseResponse response = context.getBean(SendlyBeanConstants.COMPONENT_BASE_RESPONSE,BaseResponse.class);
		
		
		if(RedisUtil.getInstance().finishSession(sessionId))
		{
			response.setStatus(true);
		}
		
		
		return response;
	}
}
