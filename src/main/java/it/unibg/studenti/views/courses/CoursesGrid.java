package it.unibg.studenti.views.courses;

import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.grid.Grid;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class CoursesGrid extends Grid<CourseRecord> {

    private ServiceManager service;

    public CoursesGrid(ServiceManager service, ResourceBundleWrapper resourceBundle, UserInfo currentUser) {
        this.service = service;

        addColumn(CourseRecord::getIdcourse)
                .setHeader(resourceBundle.getString("component_courses_id"));
        addColumn(CourseRecord::getCode)
                .setHeader(resourceBundle.getString("component_courses_code"));
        addColumn(CourseRecord::getSsd)
                .setHeader(resourceBundle.getString("component_courses_ssd"));
        addColumn(CourseRecord::getName)
                .setHeader(resourceBundle.getString("component_courses_name"));
        addColumn(CourseRecord::getCredits)
                .setHeader(resourceBundle.getString("component_courses_credits"));
        addColumn(CourseRecord::getStudents)
                .setHeader(resourceBundle.getString("component_courses_students"));
        addColumn(CourseRecord::getHours)
                .setHeader(resourceBundle.getString("component_courses_hours"));
        addColumn(CourseRecord::getParam)
                .setHeader(resourceBundle.getString("component_courses_param"));
        addColumn(CourseRecord::getPeriod)
                .setHeader(resourceBundle.getString("component_courses_period"));
        addColumn(CourseRecord::getYear)
                .setHeader(resourceBundle.getString("component_courses_year"));

        refresh();
        addItemClickListener(e -> {
            CoursesDialog dialog = new CoursesDialog(service, resourceBundle, this, e.getItem(), currentUser);
            dialog.open();
        });
    }


    public void refresh(CourseRecord item) {
        getDataCommunicator().refresh(item);
    }

    public void refresh() {
        setItems(service.getCourseService().getAll());
    }

    public void refresh(String str) {
        setItems(service.getCourseService().getFiltered(str));
    }
}
