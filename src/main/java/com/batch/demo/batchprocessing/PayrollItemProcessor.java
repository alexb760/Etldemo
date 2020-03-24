package com.batch.demo.batchprocessing;

import com.batch.demo.model.PayrollDump;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 * Class processor if any additional transform is needed
 *
 * @author Alexander Bravo
 */
public class PayrollItemProcessor implements ItemProcessor<PayrollDump, PayrollDump>
{
    public static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd][MM-dd-yyyy]");

    @Override
    public PayrollDump process(final PayrollDump item) throws Exception
    {
        return new PayrollDump(
            ifNull(item.getCompany()),
            item.getTypeTransaction(),
            item.getNumTransaction(),
            dateTransformer(item.getDate()),
            item.getReg(),
            getRandomInt(item.getDumpSID()),
            ifNull(item.getCcosto()),
            ifNull(item.getArea()),
            item.getInput(),
            item.getOutput(),
            item.getTipoReg()
        );
    }

    private String ifNull(final String  value)
    {
        return StringUtils.isBlank(value) ? "." : value;
    }

    private String getRandomInt(final String value)
    {
        return StringUtils.isAlphanumeric(value) ?
            value :
            String.valueOf(RandomUtils.nextInt(10000000, 999999999));
    }

    private String dateTransformer(final String value)
    {
        return DateTimeFormatter
            .ofPattern("dd-MMM-yyyy")
            .format(LocalDate.parse(value.replace("/","-"), FORMATTER));
    }
}
