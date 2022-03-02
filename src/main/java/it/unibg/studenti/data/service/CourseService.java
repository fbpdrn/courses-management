package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static it.unibg.studenti.generated.tables.Course.COURSE;
import static it.unibg.studenti.generated.tables.Degree.DEGREE;
import static it.unibg.studenti.generated.tables.DegreeHasCourse.DEGREE_HAS_COURSE;
import static it.unibg.studenti.generated.tables.Referent.REFERENT;
import static it.unibg.studenti.generated.tables.Staff.STAFF;
import static org.jooq.impl.DSL.sum;

@Component
public class CourseService extends DatabaseService implements DatabaseDAO<CourseRecord>{


    public CourseService(DSLContext dsl) {
        super(dsl);
    }

    @Override
    public CourseRecord getOne(int id) {
        return getDSL().selectFrom(COURSE)
                .where(COURSE.IDCOURSE.eq(id)).fetchOne();
    }

    @Override
    public List<CourseRecord> getAll() {
        return getDSL().selectFrom(COURSE).fetchInto(CourseRecord.class);
    }

    @Override
    public int insert(CourseRecord courseRecord) {
        try {
            return getDSL().insertInto(COURSE).set(courseRecord)
                    .returning(COURSE.IDCOURSE).fetch()
                    .getValue(0, COURSE.IDCOURSE);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void update(CourseRecord courseRecord) {
        getDSL().update(COURSE).set(courseRecord)
                .where(COURSE.IDCOURSE.eq(courseRecord.getIdcourse())).execute();
    }

    @Override
    public void delete(CourseRecord courseRecord) {
        delete(courseRecord.getIdcourse());
    }

    @Override
    public void delete(int id) {
        getDSL().delete(COURSE)
                .where(COURSE.IDCOURSE.eq(id))
                .execute();
    }

    public List<CourseRecord> getFiltered(String str) {
        return getDSL().selectFrom(COURSE).where(COURSE.NAME
                        .likeIgnoreCase("%" + str +"%"))
                .or(COURSE.CODE.likeIgnoreCase("%" + str +"%"))
                .fetchInto(CourseRecord.class);
    }

    public List<CourseRecord> getCoursesByStaff(StaffRecord staffRecord) {
        return getCoursesByStaff(staffRecord.getIdstaff());
    }

    public List<CourseRecord> getCoursesByStaff(int id) {
        return getDSL().select().from(COURSE)
                .join(REFERENT).on(REFERENT.COURSE_IDCOURSE.eq(COURSE.IDCOURSE))
                .join(STAFF).on(REFERENT.STAFF_IDSTAFF.eq(STAFF.IDSTAFF))
                .where(STAFF.IDSTAFF.eq(id))
                .fetchInto(CourseRecord.class);
    }

    public List<CourseRecord> getCoursesByDegreeAndYear(DegreeRecord degreeRecord, Double year){
        return getCoursesByDegreeAndYear(degreeRecord.getIddegree(), year);
    }

    public List<CourseRecord> getCoursesByDegreeAndYear(int degreeid, Double year){
        return getDSL().select().from(COURSE)
                .join(DEGREE_HAS_COURSE).on(COURSE.IDCOURSE.eq(DEGREE_HAS_COURSE.COURSE_IDCOURSE))
                .join(DEGREE).on(DEGREE.IDDEGREE.eq(DEGREE_HAS_COURSE.DEGREE_IDDEGREE))
                .where(COURSE.YEAR.eq(year).and(DEGREE.IDDEGREE.eq(degreeid)))
                .fetchInto(CourseRecord.class);
    }

    public Record1<BigDecimal> getHoursAssigned(CourseRecord courseRecord){
        return getHoursAssigned(courseRecord.getIdcourse());
    }

    public Record1<BigDecimal> getHoursAssigned(int courseid){
        return getDSL().select(sum(REFERENT.HOURS))
                .from(REFERENT)
                .join(COURSE).on(REFERENT.COURSE_IDCOURSE.eq(COURSE.IDCOURSE))
                .where(COURSE.IDCOURSE.eq(courseid)).fetchOne();
    }
}
