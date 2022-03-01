package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.DegreeRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Degree.DEGREE;

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
    public int insert(DegreeRecord degreeRecord) {
        try {
            return getDSL().insertInto(DEGREE).set(degreeRecord)
                    .returning(DEGREE.IDDEGREE).fetch()
                    .getValue(0, DEGREE.IDDEGREE);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void update(DegreeRecord degreeRecord) {
        getDSL().update(DEGREE).set(degreeRecord)
                .where(DEGREE.IDDEGREE.eq(degreeRecord.getIddegree())).execute();
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
}
