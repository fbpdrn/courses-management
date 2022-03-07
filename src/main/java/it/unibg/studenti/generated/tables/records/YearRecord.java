/*
 * This file is generated by jOOQ.
 */
package it.unibg.studenti.generated.tables.records;


import it.unibg.studenti.generated.tables.Year;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class YearRecord extends UpdatableRecordImpl<YearRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>course_management.year.idyear</code>.
     */
    public void setIdyear(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>course_management.year.idyear</code>.
     */
    public Integer getIdyear() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>course_management.year.yearstart</code>.
     */
    public void setYearstart(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>course_management.year.yearstart</code>.
     */
    public String getYearstart() {
        return (String) get(1);
    }

    /**
     * Setter for <code>course_management.year.yearend</code>.
     */
    public void setYearend(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>course_management.year.yearend</code>.
     */
    public String getYearend() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Year.YEAR.IDYEAR;
    }

    @Override
    public Field<String> field2() {
        return Year.YEAR.YEARSTART;
    }

    @Override
    public Field<String> field3() {
        return Year.YEAR.YEAREND;
    }

    @Override
    public Integer component1() {
        return getIdyear();
    }

    @Override
    public String component2() {
        return getYearstart();
    }

    @Override
    public String component3() {
        return getYearend();
    }

    @Override
    public Integer value1() {
        return getIdyear();
    }

    @Override
    public String value2() {
        return getYearstart();
    }

    @Override
    public String value3() {
        return getYearend();
    }

    @Override
    public YearRecord value1(Integer value) {
        setIdyear(value);
        return this;
    }

    @Override
    public YearRecord value2(String value) {
        setYearstart(value);
        return this;
    }

    @Override
    public YearRecord value3(String value) {
        setYearend(value);
        return this;
    }

    @Override
    public YearRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached YearRecord
     */
    public YearRecord() {
        super(Year.YEAR);
    }

    /**
     * Create a detached, initialised YearRecord
     */
    public YearRecord(Integer idyear, String yearstart, String yearend) {
        super(Year.YEAR);

        setIdyear(idyear);
        setYearstart(yearstart);
        setYearend(yearend);
    }
}
