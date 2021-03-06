/*
 * This file is generated by jOOQ.
 */
package it.unibg.studenti.generated.tables;


import it.unibg.studenti.generated.CourseManagement;
import it.unibg.studenti.generated.Indexes;
import it.unibg.studenti.generated.Keys;
import it.unibg.studenti.generated.tables.records.DegreeRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class Degree extends TableImpl<DegreeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>course_management.degree</code>
     */
    public static final Degree DEGREE = new Degree();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DegreeRecord> getRecordType() {
        return DegreeRecord.class;
    }

    /**
     * The column <code>course_management.degree.iddegree</code>.
     */
    public final TableField<DegreeRecord, Integer> IDDEGREE = createField(DSL.name("iddegree"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>course_management.degree.name</code>.
     */
    public final TableField<DegreeRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>course_management.degree.code</code>.
     */
    public final TableField<DegreeRecord, String> CODE = createField(DSL.name("code"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>course_management.degree.accyear</code>.
     */
    public final TableField<DegreeRecord, Double> ACCYEAR = createField(DSL.name("accyear"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>course_management.degree.totalcredits</code>.
     */
    public final TableField<DegreeRecord, Double> TOTALCREDITS = createField(DSL.name("totalcredits"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>course_management.degree.isPublished</code>.
     */
    public final TableField<DegreeRecord, Integer> ISPUBLISHED = createField(DSL.name("isPublished"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("1", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>course_management.degree.yearid</code>.
     */
    public final TableField<DegreeRecord, Integer> YEARID = createField(DSL.name("yearid"), SQLDataType.INTEGER.nullable(false), this, "");

    private Degree(Name alias, Table<DegreeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Degree(Name alias, Table<DegreeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>course_management.degree</code> table reference
     */
    public Degree(String alias) {
        this(DSL.name(alias), DEGREE);
    }

    /**
     * Create an aliased <code>course_management.degree</code> table reference
     */
    public Degree(Name alias) {
        this(alias, DEGREE);
    }

    /**
     * Create a <code>course_management.degree</code> table reference
     */
    public Degree() {
        this(DSL.name("degree"), null);
    }

    public <O extends Record> Degree(Table<O> child, ForeignKey<O, DegreeRecord> key) {
        super(child, key, DEGREE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : CourseManagement.COURSE_MANAGEMENT;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.DEGREE_FK_DEGREE_YEAR1_IDX);
    }

    @Override
    public Identity<DegreeRecord, Integer> getIdentity() {
        return (Identity<DegreeRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<DegreeRecord> getPrimaryKey() {
        return Keys.KEY_DEGREE_PRIMARY;
    }

    @Override
    public List<UniqueKey<DegreeRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_DEGREE_UQ_DEGREE);
    }

    @Override
    public List<ForeignKey<DegreeRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_DEGREE_YEAR1);
    }

    private transient Year _year;

    /**
     * Get the implicit join path to the <code>course_management.year</code>
     * table.
     */
    public Year year() {
        if (_year == null)
            _year = new Year(this, Keys.FK_DEGREE_YEAR1);

        return _year;
    }

    @Override
    public Degree as(String alias) {
        return new Degree(DSL.name(alias), this);
    }

    @Override
    public Degree as(Name alias) {
        return new Degree(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Degree rename(String name) {
        return new Degree(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Degree rename(Name name) {
        return new Degree(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, Double, Double, Integer, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
