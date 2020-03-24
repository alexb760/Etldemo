package com.batch.demo.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Job listener for executing any final operation either after job has finished or after job
 * has started.
 *
 * @author Alexander Bravo
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport
{
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution)
    {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            log.info("Job has finished great...! whit status {}", jobExecution.getStatus().name());
            log.info(
                String.format("%d were recorded in ORACLE Database.",
                jdbcTemplate.queryForObject("select count(*) from payroll", Integer.class)));
        }
        jobExecution.getStepExecutions().forEach(stepExecution ->
        {
            log.info("Read rows {} ", stepExecution.getReadCount());
            log.info("Written rows {} ", stepExecution.getWriteCount());
            log.info("Skipped rows {} ", stepExecution.getReadSkipCount());
        });
    }
}
