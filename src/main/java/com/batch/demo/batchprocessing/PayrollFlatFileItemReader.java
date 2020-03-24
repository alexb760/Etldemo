package com.batch.demo.batchprocessing;

import com.batch.demo.model.PayrollDump;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * Flat File Reader from CSV to Java Objects.
 *
 * @author Alexander Bravo
 */
public class PayrollFlatFileItemReader extends FlatFileItemReader<PayrollDump>
{
    public PayrollFlatFileItemReader()
    {
        this.setName("payrollReader");
        this.setResource(new ClassPathResource("payroll.csv"));
        this.setLinesToSkip(1);
        this.setLineMapper(new DefaultLineMapper<PayrollDump>()
        {
            {
                setLineTokenizer(new DelimitedLineTokenizer()
                {
                    {
                        setNames(
                            "company",
                            "typeTransaction",
                            "numTransaction",
                            "reg",
                            "date",
                            "dumpSID",
                            "ccosto",
                            "area",
                            "input",
                            "output",
                            "tipoReg");
                        //',' is by default Put it if we need to change though.
                        setDelimiter(",");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<PayrollDump>()
                {
                    {
                        setTargetType(PayrollDump.class);
                    }
                });
            }
        });
    }
}
