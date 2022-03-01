package it.unibg.studenti.data.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager extends DatabaseService{
    private DepartmentService departmentService;
    private StaffService staffService;
    private UserService userService;
    private CourseService courseService;

    public ServiceManager(@Autowired DSLContext dsl, @Autowired DepartmentService departmentService, @Autowired StaffService staffService, @Autowired UserService userService,
                          @Autowired CourseService courseService) {
        super(dsl);
        this.departmentService = departmentService;
        this.staffService = staffService;
        this.userService = userService;
        this.courseService = courseService;
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

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
