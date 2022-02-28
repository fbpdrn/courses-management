package it.unibg.studenti.views.staff;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import it.unibg.studenti.data.Sex;
import it.unibg.studenti.data.service.StaffService;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;

public class StaffDialog extends Dialog {
    private CollaborationBinder<StaffRecord> binder;
    private StaffService service;
    private UserInfo currentUserInfo;
    private UserRecord currentUser;
    private StaffRecord currentRecord;

    public StaffDialog(@Autowired StaffService service, StaffGrid staffGrid,
                       StaffRecord record, ResourceBundleWrapper resourceBundle, UserRecord currentUser){
        this.currentUser = currentUser;
        this.currentUserInfo = getCurrentUserInfo();
        this.currentRecord = record;
        this.binder = new CollaborationBinder<>(
                StaffRecord.class, currentUserInfo);
        FormLayout layout = new FormLayout();

        CollaborationAvatarGroup avatarGroup = new CollaborationAvatarGroup(
                currentUserInfo, "staff/" + (record.getIdstaff() < 0 ? UUID.randomUUID() : record.getIdstaff()));
        layout.add(avatarGroup);

        TextField tfEmail = new TextField();
        tfEmail.setRequired(true);
        tfEmail.addValueChangeListener(e -> {
            if(!(tfEmail.getValue().trim().length() >0) || tfEmail.getValue().isBlank())
                tfEmail.setErrorMessage(resourceBundle.getString("error_staff_email"));
        });
        tfEmail.setLabel(resourceBundle.getString("component_staff_email"));

        TextField tfFirstname = new TextField();
        tfFirstname.setLabel(resourceBundle.getString("component_staff_firstname"));

        TextField tfMiddlename = new TextField();
        tfMiddlename.setLabel(resourceBundle.getString("component_staff_middlename"));

        TextField tfSurname = new TextField();
        tfSurname.setLabel(resourceBundle.getString("component_staff_surname"));

        Select<String> tfSex = new Select<>();
        ArrayList<String> list = new ArrayList<>();
        for(Sex s : Sex.values()) {
            list.add(s.getSexName());
        }
        tfSex.setItems(list);
        tfSex.setLabel(resourceBundle.getString("component_staff_sex"));

        TextField tfPhone = new TextField();
        tfPhone.setLabel(resourceBundle.getString("component_staff_phone"));

        TextField tfRoom = new TextField();
        tfRoom.setLabel(resourceBundle.getString("component_staff_roomnum"));

        TextField tfStreet = new TextField();
        tfStreet.setLabel(resourceBundle.getString("component_staff_street"));

        TextField tfStreetNumber = new TextField();
        tfStreetNumber.setLabel(resourceBundle.getString("component_staff_streetnum"));

        TextField tfCity = new TextField();
        tfCity.setLabel(resourceBundle.getString("component_staff_city"));

        TextField tfZipcode = new TextField();
        tfZipcode.setLabel(resourceBundle.getString("component_staff_zipcode"));

        binder.forField(tfEmail)
                .withValidator(name -> name.trim().length() > 0, resourceBundle.getString("error_staff_email"))
                .withValidator(name -> !name.isBlank(), resourceBundle.getString("error_staff_email")).bind("email");
        binder.forField(tfFirstname).bind("firstname");
        binder.forField(tfMiddlename).bind("middlename");
        binder.forField(tfSex).bind("sex");
        binder.forField(tfSurname).bind("surname");
        binder.forField(tfPhone).bind("phonenumber");
        binder.forField(tfRoom).bind("roomnumber");
        binder.forField(tfStreet).bind("streetname");
        binder.forField(tfStreetNumber).bind("streetnumber");
        binder.forField(tfCity).bind("city");
        binder.forField(tfZipcode).bind("zipcode");
        binder.setTopic("staff/" + (record.getIdstaff() < 0 ? UUID.randomUUID() : record.getIdstaff()), () -> service.getOne(record.getIdstaff()));
        layout.add(tfEmail, tfFirstname, tfMiddlename, tfSurname, tfSex, tfPhone, tfRoom, tfStreet, tfStreetNumber,
                tfCity, tfZipcode);

        binder.setExpirationTimeout(Duration.ofMinutes(10));
        HorizontalLayout actions = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));
        btnSave.addClickListener(e -> {
            if (binder.isValid()) {
                if (record.getIdstaff() < 0)
                    service.insert(bindToRecord(binder));
                else {
                    service.update(bindToRecord(binder));
                }
                staffGrid.refresh();
                this.close();
            }});

        Button btnAbort = new Button(resourceBundle.getString("component_common_button_abort"));
        btnAbort.addClickListener(e -> this.close());
        Button btnDelete = new Button(resourceBundle.getString("component_common_button_delete"));
        btnDelete.addClickListener(e -> {
            service.delete(bindToRecord(binder));
            staffGrid.refresh();
            this.close();
        });
        if(record.getIdstaff() > 0)
            actions.add(btnSave, btnDelete, btnAbort);
        else
            actions.add(btnSave, btnAbort);
        layout.add(actions);
        add(layout);
    }

    private UserInfo getCurrentUserInfo(){
        return new UserInfo(String.valueOf(currentUser.getIduser()),
                currentUser.getName() + " " + currentUser.getSurname(),
                currentUser.getProfilepictureurl());
    }

    private StaffRecord bindToRecord(CollaborationBinder<StaffRecord> binder){
        if(currentRecord.getIdstaff() < 0)
            currentRecord.setIdstaff(null);
        currentRecord.setEmail(String.valueOf(binder.getBinding("email").get().getField().getValue()));
        currentRecord.setFirstname(String.valueOf(binder.getBinding("firstname").get().getField().getValue()));
        currentRecord.setMiddlename(String.valueOf(binder.getBinding("middlename").get().getField().getValue()));
        currentRecord.setSurname(String.valueOf(binder.getBinding("surname").get().getField().getValue()));
        currentRecord.setSex(String.valueOf(binder.getBinding("sex").get().getField().getValue()));
        currentRecord.setPhonenumber(String.valueOf(binder.getBinding("phonenumber").get().getField().getValue()));
        currentRecord.setRoomnumber(String.valueOf(binder.getBinding("roomnumber").get().getField().getValue()));
        currentRecord.setStreetname(String.valueOf(binder.getBinding("streetname").get().getField().getValue()));
        currentRecord.setStreetnumber(String.valueOf(binder.getBinding("streetnumber").get().getField().getValue()));
        currentRecord.setCity(String.valueOf(binder.getBinding("city").get().getField().getValue()));
        currentRecord.setZipcode(String.valueOf(binder.getBinding("zipcode").get().getField().getValue()));
        return currentRecord;
    }
}
