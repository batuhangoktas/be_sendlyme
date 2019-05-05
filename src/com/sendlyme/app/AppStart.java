package com.sendlyme.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;


public class AppStart implements WebApplicationInitializer{


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		JobDetail job = JobBuilder.newJob(HelloJob.class)
    			.withIdentity("dummyJobName", "group1").build();

    		//Quartz 1.6.3
    	    	//CronTrigger trigger = new CronTrigger();
    	    	//trigger.setName("dummyTriggerName");
    	    	//trigger.setCronExpression("0/5 * * * * ?");
    	    	
    	    	Trigger trigger = TriggerBuilder
    			.newTrigger()
    			.withIdentity("dummyTriggerName", "group1")
    			.withSchedule(
    				CronScheduleBuilder.cronSchedule("0 15,30,45 * ? * * *"))
    			.build();
    	    	
    	    	//schedule it
    	    	Scheduler scheduler;
				try {
					scheduler = new StdSchedulerFactory().getScheduler();
					scheduler.start();
	    	    	scheduler.scheduleJob(job, trigger);
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}	
}

