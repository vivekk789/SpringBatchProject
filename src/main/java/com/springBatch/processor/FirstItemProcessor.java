package com.springBatch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long> {

	@Override
	public Long process(Integer item) throws Exception {
	System.out.println("Inside item processor");
		return Long.valueOf(item + 20);
	}

}
