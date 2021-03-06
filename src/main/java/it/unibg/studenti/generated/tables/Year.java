/*
 * This file is generated by jOOQ.
 */
package it.unibg.studenti.generated.tables;


import it.unibg.studenti.generated.CourseManagement;
import it.unibg.studenti.generated.Keys;
import it.unibg.studenti.generated.tables.records.YearRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Year extends TableImpl<YearRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>course_management.year</code>
     */
    public static final Year YEAR = new Year();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<YearRecord> getRecordType() {
        return YearRecord.class;
    }

    /**
     * The column <code>course_management.year.idyear</code>.
     */
    public final TableField<YearRecord, Integer> IDYEAR = createField(DSL.name("idyear"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>course_management.year.yearstart</code>.
     */
    public final TableField<YearRecord, String> YEARSTART = createField(DSL.name("yearstart"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.year.yearend</code>.
     */
    public final TableField<YearRecord, String> YEAREND = createField(DSL.name("yearend"), SQLDataType.VARCHAR(255), this, "");

    private Year(Name alias, Table<YearRecord> aliased) {
        this(alias, aliased, null);
    }

    private Year(Name alias, Table<YearRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>course_management.year</code> table reference
     */
    public Year(String alias) {
        this(DSL.name(alias), YEAR);
    }

    /**
     * Create an aliased <code>course_management.year</code> table reference
     */
    public Year(Name alias) {
        this(alias, YEAR);
    }

    /**
     * Create a <code>course_management.year</code> table reference
     */
    public Year() {
        this(DSL.name("year"), null);
    }

    public <O extends Record> Year(Table<O> child, ForeignKey<O, YearRecord> key) {
        super(child, key, YEAR);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : CourseManagement.COURSE_MANAGEMENT;
    }

    @Override
    public Identity<YearRecord, Integer> getIdentity() {
        return (Identity<YearRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<YearRecord> getPrimaryKey() {
        return Keys.KEY_YEAR_PRIMARY;
    }

    @Override
    public List<UniqueKey<YearRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_YEAR_YEAR_UQ);
    }

    @Override
    public Year as(String alias) {
        return new Year(DSL.name(alias), this);
    }

    @Override
    public Year as(Name alias) {
        return new Year(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Year rename(String name) {
        return new Year(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Year rename(Name name) {
        return new Year(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
