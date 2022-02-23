package it.unibg.studenti.data.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    private final DSLContext DSL;

    @Autowired
    public DatabaseService(DSLContext dsl){
        this.DSL = dsl;
    }

    public DSLContext getDSL() {
        return this.DSL;
    }
}
