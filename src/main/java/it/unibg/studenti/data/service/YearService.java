package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.YearRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Year.YEAR;

@Component
public class YearService extends DatabaseService implements DatabaseDAO<YearRecord>{
    public YearService(@Autowired DSLContext dsl) {
        super(dsl);
    }

    @Override
    public YearRecord getOne(int id) {
        return getDSL().selectFrom(YEAR).where(YEAR.IDYEAR.eq(id)).fetchOne();
    }

    @Override
    public List<YearRecord> getAll(){
        return getDSL().selectFrom(YEAR).fetch();
    }


    @Override
    public Integer insert(YearRecord record) {
        try {
            return getDSL().insertInto(YEAR).set(record)
                    .returning(YEAR.IDYEAR).fetch()
                    .getValue(0, YEAR.IDYEAR);
        } catch(DuplicateKeyException e) {
        return -1;
    }
    }

    @Override
    public Integer update(YearRecord record) {
        try {
            getDSL().update(YEAR).set(record).where(YEAR.IDYEAR.eq(record.getIdyear())).execute();
            return 1;
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void delete(YearRecord record){
        delete(record.getIdyear());
    }

    @Override
    public void delete(int id) {
        getDSL().deleteFrom(YEAR).where(YEAR.IDYEAR.eq(id)).execute();
    }

}
