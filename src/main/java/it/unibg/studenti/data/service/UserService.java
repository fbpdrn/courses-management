package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.unibg.studenti.generated.tables.User.USER;

@Component
public class UserService extends DatabaseService implements DatabaseDAO<UserRecord>{
    public UserService(@Autowired DSLContext dsl) {
        super(dsl);
    }

    public UserRecord findByUsername(String username){
        return getDSL().selectFrom(USER).where(USER.USERNAME.eq(username)).fetchOne();
    }

    @Override
    public UserRecord getOne(int id) {
        return getDSL().selectFrom(USER).where(USER.IDUSER.eq(id)).fetchOne();
    }

    @Override
    public List<UserRecord> getAll() {
        return getDSL().selectFrom(USER).fetch();
    }

    @Override
    public Integer insert(UserRecord record) {
        try {
            return getDSL().insertInto(USER).set(record).returning(USER.IDUSER).fetch()
                    .getValue(0, USER.IDUSER);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public Integer update(UserRecord record) {
        try {
            getDSL().update(USER).set(record).where(USER.IDUSER.eq(record.getIduser())).execute();
            return 1;
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public void delete(UserRecord record) {
        delete(record.getIduser());
    }

    @Override
    public void delete(int id) {
        getDSL().deleteFrom(USER).where(USER.IDUSER.eq(id)).execute();
    }
}
