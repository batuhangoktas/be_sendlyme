package com.sendlyme.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.utils.RedisUtil;

@RefreshScope
@Service(value = SendlyBeanConstants.SERVICE_DOWNLOAD_FILE)
@Scope(value = "request", proxyMode=ScopedProxyMode.INTERFACES)
public class DownloadFileService {

	public void downloadFile(HttpServletResponse response, String fileId) throws IOException
	{
		 String fileWay = RedisUtil.getInstance().getFile(fileId);
		 String fileName = RedisUtil.getInstance().getFileName(fileId);
	        File file = null;
	         
	            file = new File(fileWay);
	       
	        if(!file.exists()){
	            String errorMessage = "Sorry. The file you are looking for does not exist";
	            System.out.println(errorMessage);
	            OutputStream outputStream = response.getOutputStream();
	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
	            outputStream.close();
	            return;
	        }
	         
	        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
	        if(mimeType==null){
	            System.out.println("mimetype is not detectable, will take default");
	            mimeType = "application/octet-stream";
	        }
	         
	        System.out.println("mimetype : "+mimeType);
	         
	        response.setContentType(mimeType);
	         
	       response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName +"\""));
	 
	       
	        response.setContentLength((int)file.length());
	 
	        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 
	        //Copy bytes from source to destination(outputstream in this example), closes both streams.
	        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}
