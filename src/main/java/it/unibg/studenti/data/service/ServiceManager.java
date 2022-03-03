package it.unibg.studenti.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager {
    private DepartmentService departmentService;
    private StaffService staffService;
    private UserService userService;
    private CourseService courseService;
    private YearService yearService;
    private DegreeService degreeService;

    public ServiceManager(@Autowired DepartmentService departmentService, @Autowired StaffService staffService, @Autowired UserService userService,
                          @Autowired CourseService courseService, @Autowired YearService yearService, @Autowired DegreeService degreeService) {
        this.departmentService = departmentService;
        this.staffService = staffService;
        this.userService = userService;
        this.courseService = courseService;
        this.yearService = yearService;
        this.degreeService = degreeService;
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

    public YearService getYearService() {
        return yearService;
    }

    public void setYearService(YearService yearService) {
        this.yearService = yearService;
    }

    public DegreeService getDegreeService() {
        return degreeService;
    }

    public void setDegreeService(DegreeService degreeService) {
        this.degreeService = degreeService;
    }
}
