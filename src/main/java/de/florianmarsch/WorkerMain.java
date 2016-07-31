package de.florianmarsch;

import static org.quartz.SimpleScheduleBuilder.repeatMinutelyForever;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerMain {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	public static void main(String[] args) throws Exception {

		logger.info("START WORKER");
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.start();

		Trigger hourly = newTrigger().startNow().withSchedule(repeatMinutelyForever(60)).build();
		JobBuilder newJob = JobBuilder.newJob(ConfigJob.class);
		JobDetail ConfigJobDetail = newJob.build();
		scheduler.scheduleJob(ConfigJobDetail, hourly);

	}

}
