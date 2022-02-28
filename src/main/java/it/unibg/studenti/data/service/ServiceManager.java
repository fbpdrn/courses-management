package it.unibg.studenti.data.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager extends DatabaseService{
    private DepartmentService departmentService;
    private StaffService staffService;
    private UserService userService;

    public ServiceManager(@Autowired DSLContext dsl) {
        super(dsl);
        departmentService = new DepartmentService(dsl);
        staffService = new StaffService(dsl);
        userService = new UserService(dsl);
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public StaffService getStaffService() {
        return staffService;
    }

    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
