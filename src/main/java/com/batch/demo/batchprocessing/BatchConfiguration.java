package com.batch.demo.batchprocessing;

import com.batch.demo.model.PayrollDump;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Batch Job configuration load all {@link Bean}
 *
 *  https://spring.io/guides/gs/batch-processing/
 * @author Alexander Bravo
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration
{
    private static final int CHUNK_SIZE = 100000;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public PayrollFlatFileItemReader flatFileItemReader()
    {
        return new PayrollFlatFileItemReader();
    }

    @Bean
    public PayrollItemProcessor processor()
    {
        return new PayrollItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<PayrollDump> writer(DataSource dataSource)
    {
        return new JdbcBatchItemWriterBuilder<PayrollDump>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("insert into payroll values(:company, :typeTransaction, :numTransaction, :reg, :date, :dumpSID, :ccosto, :area, :input, :output, :tipoReg)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importPayrollJob(JobCompletionNotificationListener listener, Step step1)
    {
        return jobBuilderFactory.get("importPayrollJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<PayrollDump> writer) {
        return stepBuilderFactory.get("step1")
            .<PayrollDump, PayrollDump> chunk(CHUNK_SIZE)
            .reader(flatFileItemReader())
            .processor(processor())
            .writer(writer)
            .build();
    }
}
