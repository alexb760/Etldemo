package com.batch.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payroll POJO
 *
 * @author Alexander Bravo
 */

//I think it does not support builder patter. Not sure
//@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDump
{
    private String company;
    private String typeTransaction;
    private String numTransaction;
    private String date;
    private String reg;
    private String dumpSID;
    private String ccosto;
    private String area;
    private Double input;
    private Double output;
    private Integer tipoReg;
}
