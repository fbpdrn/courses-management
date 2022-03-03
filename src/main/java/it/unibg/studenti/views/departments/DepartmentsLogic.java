package it.unibg.studenti.views.departments;

import com.vaadin.collaborationengine.UserInfo;
import it.unibg.studenti.data.service.DepartmentService;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.views.LogicInterface;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

import it.unibg.studenti.generated.tables.records.DepartmentRecord;

public class DepartmentsLogic implements LogicInterface<DepartmentsView, DepartmentRecord> {
    private final DepartmentsView view;
    private final ServiceManager service;
    private final ResourceBundleWrapper resourceBundle;
    private final UserInfo userInfo;
    public DepartmentsLogic(DepartmentsView view, ServiceManager service, ResourceBundleWrapper resourceBundle, UserInfo userInfo) {
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
    public DepartmentsView getView() {
        return view;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean insert(DepartmentRecord record) {
        boolean result = getService().getDepartmentService().insert(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public boolean update(DepartmentRecord record) {
        boolean result = getService().getDepartmentService().update(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    @Override
    public void delete(DepartmentRecord record) {
        getService().getDepartmentService().delete(record);
        getView().getNotification(resourceBundle.getString("database_delete"), true);
    }
}
