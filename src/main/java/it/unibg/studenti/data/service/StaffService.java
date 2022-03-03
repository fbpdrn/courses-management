package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Course.COURSE;
import static it.unibg.studenti.generated.tables.Referent.REFERENT;
import static it.unibg.studenti.generated.tables.Staff.STAFF;

@Component
public class StaffService extends DatabaseService implements DatabaseDAO<StaffRecord>{

    public StaffService(@Autowired DSLContext dsl) {
        super(dsl);
    }

    @Override
    public StaffRecord getOne(int id) {
        return getDSL().selectFrom(STAFF)
                .where(STAFF.IDSTAFF.eq(id)).fetchOne();
    }

    @Override
    public List<StaffRecord> getAll() { return getDSL().selectFrom(STAFF).fetchInto(StaffRecord.class); }

    @Override
    public Integer insert(StaffRecord staffRecord) {
        try {
            return getDSL().insertInto(STAFF).set(staffRecord)
                    .returning(STAFF.IDSTAFF).fetch()
                    .getValue(0, STAFF.IDSTAFF);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public Integer update(StaffRecord staffRecord) {
        try {
            getDSL().update(STAFF).set(staffRecord)
                    .where(STAFF.IDSTAFF.eq(staffRecord.getIdstaff())).execute();
            return 1;
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void delete(StaffRecord staffRecord) {
        delete(staffRecord.getIdstaff());
    }

    @Override
    public void delete(int id) {
        getDSL().delete(STAFF)
                .where(STAFF.IDSTAFF.eq(id))
                .execute();
    }

    public List<StaffRecord> getFiltered(String str) {
        return getDSL().selectFrom(STAFF).where(STAFF.SURNAME
                        .likeIgnoreCase("%" + str +"%"))
                .or(STAFF.MIDDLENAME.likeIgnoreCase("%" + str +"%"))
                .or(STAFF.FIRSTNAME.likeIgnoreCase("%" + str +"%"))
                .or(STAFF.EMAIL.likeIgnoreCase("%" + str +"%"))
                .fetchInto(StaffRecord.class);
    }

    public List<StaffRecord> getStaffByCourse(CourseRecord courseRecord) {
        return getStaffByCourse(courseRecord.getIdcourse());
    }

    public List<StaffRecord> getStaffByCourse(int id) {
        return getDSL().select().from(STAFF)
                .join(REFERENT).on(REFERENT.STAFF_IDSTAFF.eq(STAFF.IDSTAFF))
                .join(COURSE).on(REFERENT.COURSE_IDCOURSE.eq(COURSE.IDCOURSE))
                .where(COURSE.IDCOURSE.eq(id))
                .fetchInto(StaffRecord.class);
    }
}