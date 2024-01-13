package com.springBatch.Controller;
import java.util.List;

import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBatch.Request.JobParamsRequest;
import com.springBatch.Service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
  @Autowired
  private JobService jobService;
  
  @Autowired
  private JobOperator jobOperator;
	
  @PostMapping("/start/{jobName}") 
  public String startJob(@PathVariable String jobName , 
		  @RequestBody List<JobParamsRequest> jobParamsRequestList) throws Exception { 
	  
	  jobService.startJob(jobName , jobParamsRequestList); 
	  
	   return "Job Started"; 
  
  }

  @GetMapping("/stop/{jobExecutionId}")
  public String stopJob(@PathVariable long jobExecutionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
	  jobOperator.stop(jobExecutionId);
	  
	  return "Job Stoped.." ;
  }
	
}
