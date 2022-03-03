package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.YearRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static it.unibg.studenti.generated.tables.Course.COURSE;
import static it.unibg.studenti.generated.tables.Degree.DEGREE;
import static it.unibg.studenti.generated.tables.DegreeHasCourse.DEGREE_HAS_COURSE;
import static org.jooq.impl.DSL.sum;

@Component
public class DegreeService extends DatabaseService implements DatabaseDAO<DegreeRecord>{

    public DegreeService(@Autowired DSLContext dsl) {
        super(dsl);
    }

    @Override
    public DegreeRecord getOne(int id) {
        return getDSL().selectFrom(DEGREE).where(DEGREE.IDDEGREE.eq(id)).fetchOne();
    }

    @Override
    public List<DegreeRecord> getAll() {
        return getDSL().selectFrom(DEGREE).fetchInto(DegreeRecord.class);
    }

    @Override
    public Integer insert(DegreeRecord degreeRecord) {
        try {
            return getDSL().insertInto(DEGREE).set(degreeRecord)
                    .returning(DEGREE.IDDEGREE).fetch()
                    .getValue(0, DEGREE.IDDEGREE);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public Integer update(DegreeRecord degreeRecord) {
        try {
            getDSL().update(DEGREE).set(degreeRecord)
                    .where(DEGREE.IDDEGREE.eq(degreeRecord.getIddegree())).execute();
            return 1;
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void delete(DegreeRecord degreeRecord) {
        delete(degreeRecord.getIddegree());
    }

    @Override
    public void delete(int id) {
        getDSL().delete(DEGREE)
                .where(DEGREE.IDDEGREE.eq(id))
                .execute();
    }

    public List<DegreeRecord> getDegreeByYear(YearRecord yearRecord) {
        return getDegreeByYear(yearRecord.getIdyear());
    }

    public List<DegreeRecord> getDegreeByYear(int id) {
        return getDSL().selectFrom(DEGREE)
                .where(DEGREE.YEAR_IDYEAR.eq(id))
                .fetchInto(DegreeRecord.class);
    }

    public Record1<BigDecimal> getCreditsAssigned(DegreeRecord degreeRecord) {
        return getCreditsAssigned(degreeRecord.getIddegree());
    }

    public Record1<BigDecimal> getCreditsAssigned(int degreeid) {
        return getDSL().select(sum(COURSE.CREDITS))
                .from(COURSE)
                .join(DEGREE_HAS_COURSE).on(DEGREE_HAS_COURSE.COURSE_IDCOURSE.eq(COURSE.IDCOURSE))
                .join(DEGREE).on(DEGREE_HAS_COURSE.DEGREE_IDDEGREE.eq(DEGREE.IDDEGREE))
                .where(DEGREE.IDDEGREE.eq(degreeid)).fetchOne();
    }
}
