package it.unibg.studenti.views.planner;

import com.vaadin.collaborationengine.UserInfo;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.views.LogicInterface;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class PlannerLogic implements LogicInterface<PlannerView, DegreeRecord> {
    private final PlannerView view;
    private final ServiceManager service;
    private final ResourceBundleWrapper resourceBundle;
    private final UserInfo userInfo;
    private DegreeRecord currentDegree;

    public PlannerLogic(PlannerView view, ServiceManager service, ResourceBundleWrapper resourceBundle, UserInfo userInfo) {
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
    public PlannerView getView() {
        return view;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public DegreeRecord getCurrentDegree() {
        return currentDegree;
    }

    public void setCurrentDegree(DegreeRecord currentDegree) {
        this.currentDegree = currentDegree;
    }

    @Override
    public boolean insert(DegreeRecord record) {
        boolean result = getService().getDegreeService().insert(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public boolean update(DegreeRecord record) {
        boolean result = getService().getDegreeService().update(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_update") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public void delete(DegreeRecord record) {
        getService().getDegreeService().delete(record);
        getView().getNotification(resourceBundle.getString("database_delete"), true);
    }

    public boolean addCourse(DegreeRecord d, CourseRecord c) {
        boolean result = (getService().getDegreeService().addCourse(d, c) > 0);
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }
}
