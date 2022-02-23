package it.unibg.studenti.data.service;

import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static it.unibg.studenti.generated.tables.Department.DEPARTMENT;

public class DepartmentService extends DatabaseService implements DatabaseDAO<DepartmentRecord>{

    public DepartmentService(DSLContext dsl) {
        super(dsl);
    }

    @Override
    public DepartmentRecord getOne(int id) {
        return getDSL().selectFrom(DEPARTMENT)
                .where(DEPARTMENT.IDDEPARTMENT.eq(id)).fetchOne();
    }

    @Override
    public List<DepartmentRecord> getAll() {
        return null;
    }

    @Override
    public int insert(DepartmentRecord departmentRecord) {
        try {
            return getDSL().insertInto(DEPARTMENT).set(departmentRecord)
                    .returning(DEPARTMENT.IDDEPARTMENT).fetch()
                    .getValue(0, DEPARTMENT.IDDEPARTMENT);
        } catch(DuplicateKeyException e) {
            return -1;
        }
    }

    @Override
    public int update(DepartmentRecord departmentRecord) {
        return getDSL().update(DEPARTMENT).set(departmentRecord)
                .where(DEPARTMENT.IDDEPARTMENT.eq(departmentRecord.getIddepartment()))
                .returning(DEPARTMENT.IDDEPARTMENT).fetch()
                .getValue(0, DEPARTMENT.IDDEPARTMENT);
    }

    @Override
    public void delete(DepartmentRecord departmentRecord) {
        delete(departmentRecord.getIddepartment());
    }

    @Override
    public void delete(int id) {
        getDSL().delete(DEPARTMENT)
                .where(DEPARTMENT.IDDEPARTMENT.eq(id))
                .execute();
    }

    public List<DepartmentRecord> getFiltered(String str) {
        return getDSL().selectFrom(DEPARTMENT).where(DEPARTMENT.NAME
                        .likeIgnoreCase("%" + str +"%"))
                .or(DEPARTMENT.DESCRIPTION.likeIgnoreCase("%" + str +"%"))
                .fetchInto(DepartmentRecord.class);
    }
}
