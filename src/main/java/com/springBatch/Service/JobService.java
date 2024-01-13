package com.springBatch.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springBatch.Request.JobParamsRequest;

@Service
public class JobService {
	
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("firstJob")
	@Autowired
	Job firstJob;
	
	@Qualifier("secondJob")
	@Autowired
	Job secondJob;
	
	@Async
	public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestList) {
		Map<String , JobParameter> params  = new HashMap<>();
		
		//this parameter is set here 
		params.put("currentTime", new JobParameter(System.currentTimeMillis()));
		
		// this came from json 
		jobParamsRequestList.stream().forEach(jobPramReq -> {
			params.put(jobPramReq.getParamKey() , 
					new JobParameter(jobPramReq.getParamValue()) );
		});
		
		JobParameters jobParameters = new JobParameters(params); 
		
		
		try {
			JobExecution jobExecution = null;
			if(jobName.equals("First Job")) {
				jobExecution = jobLauncher.run(firstJob, jobParameters);
			}else if (jobName.equals("Second Job")) {
				jobExecution = jobLauncher.run(secondJob, jobParameters);
			}
			
			System.out.println("jobExecution ID "+ jobExecution.getId());
			
		} catch (Exception e) {
				System.out.println("exception while execution job");
		}
		
	}

}
