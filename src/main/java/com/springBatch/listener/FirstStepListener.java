package com.springBatch.listener;



import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
@Component
public class FirstStepListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Before step "+ stepExecution.getStepName());
		System.out.println("Before job Exec Contex "+ stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Before step Exec Contex "+ stepExecution.getExecutionContext());
		
		stepExecution.getExecutionContext().put("sec", "value");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("after step "+ stepExecution.getStepName());
		System.out.println("after job Exec Contex "+ stepExecution.getJobExecution().getExecutionContext());
		System.out.println("after step Exec Contex "+ stepExecution.getExecutionContext());
		return null;
	}

	

}
