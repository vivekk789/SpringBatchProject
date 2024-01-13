package com.springBatch.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class SecondJobScheduler {
	
//Currently it is commented  ..
	//remove commented from - service , Scheduled , main class -> EnableScheduling 
	

	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("secondJob")
	@Autowired
	Job secondJob;
	
		//(this is scheduled for 1 min ...cronmaker.com)
	
//	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void secondJobStarter() {
		
		Map<String , JobParameter> params  = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(params); 
		
		
		try {
			JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
			System.out.println("jobExecution ID "+ jobExecution.getId());
			
		} catch (Exception e) {
				System.out.println("exception while execution job");
		}
		
		
		
	}
	
	

}
