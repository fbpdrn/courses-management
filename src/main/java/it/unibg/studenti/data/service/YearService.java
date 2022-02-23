package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.YearRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.Year.YEAR;

@Component
public class YearService extends DatabaseService{
    public YearService(DSLContext dsl) {
        super(dsl);
    }

    public List<YearRecord> getAll(){
        return getDSL().selectFrom(YEAR).fetch();
    }


    public void insert(YearRecord record) {
        getDSL().insertInto(YEAR).set(record).execute();
    }

    public void update(YearRecord record) {
        getDSL().update(YEAR).set(record).where(YEAR.IDYEAR.eq(record.getIdyear())).execute();
    }

    public void delete(YearRecord record){
        getDSL().deleteFrom(YEAR).where(YEAR.IDYEAR.eq(record.getIdyear())).execute();
    }

}
