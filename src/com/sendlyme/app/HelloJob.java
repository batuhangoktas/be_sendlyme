package com.sendlyme.app;

import java.io.File;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sendlyme.utils.RedisUtil;

public class HelloJob implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		List<String> fileList = RedisUtil.getInstance().getFileKeysLike();
		fileList.forEach(fileId ->{
			
			 String fileWay = RedisUtil.getInstance().getFile(fileId);
			 File file = null;
		     file = new File(fileWay);
		       
		        if(file.exists()){
		           file.delete();
		           RedisUtil.getInstance().tookFile(fileId);
		        }
			
		});
		
		
	}
	
}