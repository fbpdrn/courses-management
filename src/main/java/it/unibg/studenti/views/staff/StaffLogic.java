package it.unibg.studenti.views.staff;

import com.vaadin.collaborationengine.UserInfo;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.LogicInterface;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class StaffLogic implements LogicInterface<StaffView, StaffRecord> {
    private final StaffView view;
    private final ServiceManager service;
    private final ResourceBundleWrapper resourceBundle;
    private final UserInfo userInfo;
    public StaffLogic(StaffView view, ServiceManager service, ResourceBundleWrapper resourceBundle, UserInfo userInfo) {
        this.view = view;
        this.service = service;
        this.resourceBundle = resourceBundle;
        this.userInfo = userInfo;
    }

    public ServiceManager getService() {
        return service;
    }

    public ResourceBundleWrapper getResourceBundle() {
        return resourceBundle;
    }

    public StaffView getView() {
        return view;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean insert(StaffRecord record) {
        boolean result = getService().getStaffService().insert(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_insert") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    public boolean update(StaffRecord record) {
        boolean result = getService().getStaffService().update(record) > 0;
        getView().getNotification((result) ? resourceBundle.getString("database_update") : resourceBundle.getString("error_common_duplicatekey"), result);
        return result;
    }

    public void delete(StaffRecord record) {
        getService().getStaffService().delete(record);
        getView().getNotification(resourceBundle.getString("database_delete"), true);
    }
}
