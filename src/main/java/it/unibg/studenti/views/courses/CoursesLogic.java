package it.unibg.studenti.views.courses;

import com.vaadin.collaborationengine.UserInfo;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.views.LogicInterface;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class CoursesLogic implements LogicInterface<CoursesView, CourseRecord> {
    private final CoursesView view;
    private final ServiceManager service;
    private final ResourceBundleWrapper resourceBundle;
    private final UserInfo userInfo;

    public CoursesLogic(CoursesView view, ServiceManager service, ResourceBundleWrapper resourceBundle, UserInfo userInfo) {
        this.view = view;
        this.service = service;
        this.resourceBundle = resourceBundle;
        this.userInfo = userInfo;
    }

    @Override
    public ServiceManager getService() {
        return service;
    }

    @Override
    public ResourceBundleWrapper getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public CoursesView getView() {
        return view;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean insert(CourseRecord record) {
        boolean result = getService().getCourseService().insert(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public boolean update(CourseRecord record) {
        boolean result = getService().getCourseService().insert(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public void delete(CourseRecord record) {
        getService().getCourseService().delete(record);
        getView().getNotification(resourceBundle.getString("database_delete"), true);
    }
}
