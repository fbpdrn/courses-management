package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.CourseRecord;
import org.jooq.DSLContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Course.COURSE;

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
}
