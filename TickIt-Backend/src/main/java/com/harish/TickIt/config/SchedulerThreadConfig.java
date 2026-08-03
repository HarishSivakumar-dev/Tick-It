package com.harish.TickIt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerThreadConfig
{
	@Bean
	public TaskScheduler taskScheduler()
	{
		ThreadPoolTaskScheduler tpts= new ThreadPoolTaskScheduler();
		tpts.setPoolSize(5);
		tpts.setThreadNamePrefix("cron-");
		
		return tpts;
	}

}
