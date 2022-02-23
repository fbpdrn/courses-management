package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import it.unibg.studenti.data.Role;
import it.unibg.studenti.data.service.UserService;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.views.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class UserDialog extends Dialog {
    UserRecord record;
    private final Binder<UserRecord> binder;
    public UserDialog(@Autowired UserService service, UserGrid userGrid, boolean isNew){
        binder = new Binder<>();
        FormLayout layout = new FormLayout();
        TextField tfUsername = null;
        if(isNew) {
            tfUsername = new TextField("Username");
            tfUsername.setWidthFull();
            binder.forField(tfUsername).bind(UserRecord::getUsername, UserRecord::setUsername);
            layout.add(tfUsername);
        }

        TextField tfName = new TextField("Name");
        tfName.setWidthFull();
        binder.forField(tfName).bind(UserRecord::getName, UserRecord::setName);

        TextField tfSurname = new TextField("Surname");
        tfSurname.setWidthFull();
        binder.forField(tfSurname).bind(UserRecord::getSurname, UserRecord::setSurname);

        TextField tfPassword = new TextField("Password");
        tfPassword.setWidthFull();
        tfPassword.setRequired(true);
        if(isNew)
            tfPassword.setValue(Utility.SimpleRandomString(16));
        else
            tfPassword.setPlaceholder("Edit to change password");

        Select<String> sRole = new Select<>();
        sRole.setItems(Role.USER.getRoleName(), Role.ADMIN.getRoleName());
        sRole.setLabel("Role");
        if(isNew)
            sRole.setValue(Role.USER.getRoleName());
        binder.forField(sRole).bind(UserRecord::getRole, UserRecord::setRole);

        TextField tfProfilePic = new TextField("Avatar");
        tfProfilePic.setWidthFull();
        tfProfilePic.setRequired(false);
        binder.forField(tfProfilePic).bind(UserRecord::getProfilepictureurl, UserRecord::setProfilepictureurl);

        layout.add(tfName,tfSurname,tfPassword,sRole, tfProfilePic);

        HorizontalLayout actions = new HorizontalLayout();
        Button btnAbort = new Button("Abort");
        btnAbort.addClickListener(e -> this.close());
        Button btnSave = new Button("Save");
        if(!isNew) {
            btnSave.addClickListener(e -> {
                if (!(tfPassword.getValue() == null))
                    binder.getBean().setHashedpassword(new BCryptPasswordEncoder().encode(tfPassword.getValue()));
                service.update(binder.getBean());
                userGrid.refresh();
                this.close();
            });
            Button btnDelete = new Button("Delete");
            btnDelete.addClickListener(e -> {
                service.delete(binder.getBean());
                userGrid.refresh();
                this.close();}
            );
            actions.add(btnSave, btnAbort, btnDelete);
        } else {
            TextField finalTfUsername = tfUsername;
            btnSave.addClickListener(e -> {
                if(finalTfUsername.getValue() == null)
                    binder.getBean().setUsername("U" + UUID.randomUUID());
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                binder.getBean().setHashedpassword(bcrypt.encode(tfPassword.getValue()));
                service.insert(binder.getBean());
                userGrid.refresh();
                this.close();
            });
            actions.add(btnSave, btnAbort);
        }
        layout.add(actions);
        add(layout);
    }

    public void openAndSetBinder(UserRecord record) {
        this.record = record;
        binder.setBean(record);
        this.open();
    }
}
