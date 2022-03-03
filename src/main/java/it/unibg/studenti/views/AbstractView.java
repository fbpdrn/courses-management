package it.unibg.studenti.views;

import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.security.AuthenticatedUser;

public class AbstractView extends VerticalLayout {

    public UserRecord getCurrentUser(ServiceManager service, AuthenticatedUser user){
        return service.getUserService().getOne(user.get().get().getIduser());
    }

    public UserInfo getUserInfo(ServiceManager service, AuthenticatedUser user) {
        UserRecord currentUser = getCurrentUser(service, user);
        return new UserInfo(String.valueOf(currentUser.getIduser()),
                currentUser.getName() + " " + currentUser.getSurname(),
                currentUser.getProfilepictureurl());
    }

    public void getNotification(String message, boolean isSuccessful) {
        Notification notification = Notification.show(message);
        if(!isSuccessful)
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
