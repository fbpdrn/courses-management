/*
 * This file is generated by jOOQ.
 */
package it.unibg.studenti.generated.tables;


import it.unibg.studenti.generated.CourseManagement;
import it.unibg.studenti.generated.Indexes;
import it.unibg.studenti.generated.Keys;
import it.unibg.studenti.generated.tables.records.StaffRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row14;
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
public class Staff extends TableImpl<StaffRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>course_management.staff</code>
     */
    public static final Staff STAFF = new Staff();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StaffRecord> getRecordType() {
        return StaffRecord.class;
    }

    /**
     * The column <code>course_management.staff.idstaff</code>.
     */
    public final TableField<StaffRecord, Integer> IDSTAFF = createField(DSL.name("idstaff"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>course_management.staff.email</code>.
     */
    public final TableField<StaffRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>course_management.staff.firstname</code>.
     */
    public final TableField<StaffRecord, String> FIRSTNAME = createField(DSL.name("firstname"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.staff.middlename</code>.
     */
    public final TableField<StaffRecord, String> MIDDLENAME = createField(DSL.name("middlename"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.staff.surname</code>.
     */
    public final TableField<StaffRecord, String> SURNAME = createField(DSL.name("surname"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.staff.sex</code>.
     */
    public final TableField<StaffRecord, String> SEX = createField(DSL.name("sex"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.phonenumber</code>.
     */
    public final TableField<StaffRecord, String> PHONENUMBER = createField(DSL.name("phonenumber"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.roomnumber</code>.
     */
    public final TableField<StaffRecord, String> ROOMNUMBER = createField(DSL.name("roomnumber"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.streetname</code>.
     */
    public final TableField<StaffRecord, String> STREETNAME = createField(DSL.name("streetname"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.staff.streetnumber</code>.
     */
    public final TableField<StaffRecord, String> STREETNUMBER = createField(DSL.name("streetnumber"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.city</code>.
     */
    public final TableField<StaffRecord, String> CITY = createField(DSL.name("city"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>course_management.staff.zipcode</code>.
     */
    public final TableField<StaffRecord, String> ZIPCODE = createField(DSL.name("zipcode"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.contract</code>.
     */
    public final TableField<StaffRecord, String> CONTRACT = createField(DSL.name("contract"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>course_management.staff.departmentid</code>.
     */
    public final TableField<StaffRecord, Integer> DEPARTMENTID = createField(DSL.name("departmentid"), SQLDataType.INTEGER.nullable(false), this, "");

    private Staff(Name alias, Table<StaffRecord> aliased) {
        this(alias, aliased, null);
    }

    private Staff(Name alias, Table<StaffRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>course_management.staff</code> table reference
     */
    public Staff(String alias) {
        this(DSL.name(alias), STAFF);
    }

    /**
     * Create an aliased <code>course_management.staff</code> table reference
     */
    public Staff(Name alias) {
        this(alias, STAFF);
    }

    /**
     * Create a <code>course_management.staff</code> table reference
     */
    public Staff() {
        this(DSL.name("staff"), null);
    }

    public <O extends Record> Staff(Table<O> child, ForeignKey<O, StaffRecord> key) {
        super(child, key, STAFF);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : CourseManagement.COURSE_MANAGEMENT;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.STAFF_FK_STAFF_DEPARTMENT1_IDX);
    }

    @Override
    public Identity<StaffRecord, Integer> getIdentity() {
        return (Identity<StaffRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<StaffRecord> getPrimaryKey() {
        return Keys.KEY_STAFF_PRIMARY;
    }

    @Override
    public List<UniqueKey<StaffRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_STAFF_EMAIL_UNIQUE);
    }

    @Override
    public List<ForeignKey<StaffRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_STAFF_DEPARTMENT1);
    }

    private transient Department _department;

    /**
     * Get the implicit join path to the
     * <code>course_management.department</code> table.
     */
    public Department department() {
        if (_department == null)
            _department = new Department(this, Keys.FK_STAFF_DEPARTMENT1);

        return _department;
    }

    @Override
    public Staff as(String alias) {
        return new Staff(DSL.name(alias), this);
    }

    @Override
    public Staff as(Name alias) {
        return new Staff(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Staff rename(String name) {
        return new Staff(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Staff rename(Name name) {
        return new Staff(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Integer, String, String, String, String, String, String, String, String, String, String, String, String, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}
