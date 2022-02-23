package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.YearRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Year.YEAR;

@Component
public class YearService extends DatabaseService implements DatabaseDAO<YearRecord>{
    public YearService(DSLContext dsl) {
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
    public int insert(YearRecord record) {
        return getDSL().insertInto(YEAR).set(record)
                .returning(YEAR.IDYEAR).fetch()
                .getValue(0, YEAR.IDYEAR);
    }

    @Override
    public int update(YearRecord record) {
        return getDSL().update(YEAR).set(record).where(YEAR.IDYEAR.eq(record.getIdyear()))
                .returning(YEAR.IDYEAR).fetch()
                .getValue(0, YEAR.IDYEAR);
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
