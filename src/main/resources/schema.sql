drop table payroll if exists;

create table payroll(
 company varchar(3),
 typeTransaction varchar(7),
 numTransaction varchar(15),
 reg varchar(15),
 payroll_date Date,
 dumSID varchar2(15),
 ccosto varchar(7),
 area varchar(7),
 input DECIMAL (15,2),
 aouput DECIMAL (15,2),
 tiporeg numeric(1)
);
