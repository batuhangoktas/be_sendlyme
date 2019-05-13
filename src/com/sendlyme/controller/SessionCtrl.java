package com.sendlyme.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sendlyme.response.BaseResponse;
import com.sendlyme.response.FileListReceiveResponse;
import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.response.SessionCreateResponse;
import com.sendlyme.response.SessionJoinResponse;
import com.sendlyme.service.BaseService;
import com.sendlyme.service.DownloadFileService;
import com.sendlyme.service.FileListReceiveService;
import com.sendlyme.service.SessionCreateService;
import com.sendlyme.service.SessionJoinService;

@RestController
@RequestMapping("/session")
@CrossOrigin(origins = "*")
public class SessionCtrl {
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	@Qualifier(value = SendlyBeanConstants.SERVICE_CREATE)
    SessionCreateService sessionCreateService;

	@Autowired
	@Qualifier(value = SendlyBeanConstants.SERVICE_JOIN)
    SessionJoinService sessionJoinService;
	
	@Autowired
	@Qualifier(value = SendlyBeanConstants.SERVICE_BASE)
    BaseService baseService;
	
	@Autowired
	@Qualifier(value = SendlyBeanConstants.SERVICE_FILE_LIST_RECEIVE)
	FileListReceiveService fileListReceiveService;
	
	@Autowired
	@Qualifier(value = SendlyBeanConstants.SERVICE_DOWNLOAD_FILE)
	DownloadFileService downloadFileService;
 
	@RequestMapping(value="/createsession", method={RequestMethod.POST, RequestMethod.GET}, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public SessionCreateResponse createSession(HttpServletRequest request) {
 		
		   String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	       if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	   }
	       
		return sessionCreateService.getCreateResponse(ipAddress);
	}
	
	@RequestMapping(value="/hassessionsync", method={RequestMethod.POST, RequestMethod.GET}, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse hasSessionSync(@RequestParam(value = "sessionid", defaultValue = "") String sessionId,HttpServletRequest request) {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	       if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	   }
	       
		return baseService.getHasSessionSyncService(ipAddress, sessionId);
	}
	
	@RequestMapping(value="/joinsession", method={RequestMethod.POST, RequestMethod.GET}, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public SessionJoinResponse joinSession(@RequestParam(value = "sessionid", defaultValue = "") String sessionId,HttpServletRequest request) {

		 String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	       if (ipAddress == null) {  
	         ipAddress = request.getRemoteAddr();  
	   }
	       
		return sessionJoinService.getJoinResponse(ipAddress, sessionId);
	}
	

	@RequestMapping(value = "/upload", method = {RequestMethod.POST,RequestMethod.GET},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse fileUpload(@RequestParam(value = "sessionid", defaultValue = "") String sessionId,@RequestParam(value = "userid", defaultValue = "") String userId,@RequestParam("file") MultipartFile file
                                   ) {
	
		return baseService.getFileUpload(sessionId,userId,file);
    }
	
	@RequestMapping(value = "/filereceive", method = {RequestMethod.POST,RequestMethod.GET},produces = MediaType.APPLICATION_JSON_VALUE)
    public FileListReceiveResponse fileReceive(@RequestParam(value = "sessionid", defaultValue = "") String sessionId,@RequestParam(value = "userid", defaultValue = "") String userId
                                   ) {
		
		return fileListReceiveService.getFileListReceive(sessionId,userId);            
    }
	
	 @RequestMapping(value="/download", method = {RequestMethod.POST,RequestMethod.GET})
	    public void downloadFile(HttpServletResponse response, @RequestParam("fileid") String fileId) throws IOException {
	     
		downloadFileService.downloadFile(response,fileId);
	    }
	 @RequestMapping(value="/tookfile", method={RequestMethod.POST, RequestMethod.GET}, 
			  produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse tookFile(@RequestParam(value = "fileid", defaultValue = "") String fileId) {

		 	return baseService.getTookFile(fileId);
	}

}


