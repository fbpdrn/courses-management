package it.unibg.studenti.views;

import com.vaadin.collaborationengine.UserInfo;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public interface LogicInterface<T, E>{
    ServiceManager getService();
    ResourceBundleWrapper getResourceBundle();
    T getView();
    UserInfo getUserInfo();
    boolean insert(E record);
    boolean update(E record);
    void delete(E record);
}
