package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.User.USER;

@Component
public class UserService extends DatabaseService{
    public UserService(@Autowired DSLContext dsl) {
        super(dsl);
    }

    public UserRecord findByUsername(String username){
        return getDSL().selectFrom(USER).where(USER.USERNAME.eq(username)).fetchOne();
    }

    public List<UserRecord> getAll() {
        return getDSL().selectFrom(USER).fetch();
    }

    public void insert(UserRecord record) {
        getDSL().insertInto(USER).set(record).execute();
    }

    public void update(UserRecord record) {
        getDSL().update(USER).set(record).where(USER.IDUSER.eq(record.getIduser())).execute();
    }

    public void delete(UserRecord record) {
        getDSL().deleteFrom(USER).where(USER.IDUSER.eq(record.getIduser())).execute();
    }
}
