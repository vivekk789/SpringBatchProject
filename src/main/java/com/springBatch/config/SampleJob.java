package com.springBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springBatch.Service.SecondTasklet;
import com.springBatch.listener.FirstJobListener;
import com.springBatch.listener.FirstStepListener;
import com.springBatch.processor.FirstItemProcessor;
import com.springBatch.reader.FirstItemReader;
import com.springBatch.writer.FirstItemWriter;

@Configuration
public class SampleJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SecondTasklet secondTasklet;
	
	@Autowired
	private FirstJobListener firstJobListener;


	@Autowired
	private FirstStepListener firstStepListener;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	
	@Autowired
	private FirstItemWriter firstItemWriter;
	
	
	

	//@Bean  (if you comment the bean then job will not execute-- is se realated kuch nhi chalega)
	public Job firstJob() {
		return jobBuilderFactory.get("first job")
		.incrementer(new RunIdIncrementer())		
		.start(firstStep())
		.next(secondStep())
		.listener(firstJobListener)
		.build();
	}
	
	private Step firstStep() {
		return stepBuilderFactory.get("first step")
		.tasklet(firstTask())
		.listener(firstStepListener)
		.build();
		
	}
	
	private Tasklet firstTask() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step");
				return RepeatStatus.FINISHED ;
			}
		};
	}
	
	private Step secondStep() {
		return stepBuilderFactory.get("second step")
		.tasklet(secondTasklet)
		.build();
		
	}
	
	/*
	 * private Tasklet secondTask() { return new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception {
	 * System.out.println("This is second tasklet step"); return
	 * RepeatStatus.FINISHED ; } }; }
	 */
	
	@Bean
	public Job secondJob() {
		return jobBuilderFactory.get("second job")
		.incrementer(new RunIdIncrementer())	
		.start(firstChunkStep())
		.build();
	}
	
	private Step firstChunkStep() {
		return stepBuilderFactory.get("first chunk step")
		.<Integer , Long>chunk(4)
		.reader(firstItemReader)
		.processor(firstItemProcessor)
		.writer(firstItemWriter)
		.build();
	}
	
}
