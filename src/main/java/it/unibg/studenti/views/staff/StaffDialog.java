package it.unibg.studenti.views.staff;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.data.Contract;
import it.unibg.studenti.data.Sex;
import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class StaffDialog extends Dialog {
    private final CollaborationBinder<StaffRecord> binder;
    private final StaffLogic logic;
    private final ResourceBundleWrapper resourceBundle;
    private final StaffGrid grid;
    private final StaffRecord record;

    private Label errorMessage;

    public StaffDialog(StaffLogic logic, StaffGrid grid,
                       StaffRecord record, ResourceBundleWrapper resourceBundle){
        this.grid = grid;
        this.logic = logic;
        this.resourceBundle = resourceBundle;
        this.record = normalizeRecord(record);
        this.binder = new CollaborationBinder<>(StaffRecord.class, logic.getUserInfo());
        add(createForm());
    }

    private HorizontalLayout createActions(CollaborationBinder<StaffRecord> binder) {
        HorizontalLayout layout = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));
        btnSave.addClickListener(e -> {
            if(validateRecord(binder) != null) {
                if(record.getIdstaff() != null) {
                    if(logic.update(record)) {
                        grid.refresh();
                        this.close();
                    }
                } else {
                    if(logic.insert(record)) {
                        grid.refresh();
                        this.close();
                    }
                }
                errorMessage.setText(resourceBundle.getString("error_common_duplicatekey"));
                errorMessage.setVisible(true);
            }
            errorMessage.setText(resourceBundle.getString("error_common_genericfield"));
            errorMessage.setVisible(true);
        });

        Button btnAbort = new Button(resourceBundle.getString("component_common_button_abort"));
        btnAbort.addClickListener(e -> this.close());
        Button btnDelete = new Button(resourceBundle.getString("component_common_button_delete"));
        btnDelete.addClickListener(e -> {
            logic.delete(record);
            grid.refresh();
            this.close();
        });
        if(record.getIdstaff() < 0)
            btnDelete.setEnabled(false);
        layout.add(btnSave, btnAbort, btnDelete);
        return layout;
    }

    private FormLayout createForm() {
        //UI Section

        FormLayout layout = new FormLayout();

        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("1000px", 3)
        );
        String topic = generateTopic(record);
        CollaborationAvatarGroup avatarGroup = new CollaborationAvatarGroup(
                logic.getUserInfo(), topic);
        add(avatarGroup);

        errorMessage = new Label(resourceBundle.getString("error_common_genericfield"));
        errorMessage.setVisible(false);
        errorMessage.getStyle().set("color", Color.ERROR.getColorValue());

        TextField tfEmail = new TextField();
        tfEmail.setRequired(true);
        tfEmail.setValueChangeMode(ValueChangeMode.EAGER);
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
        ArrayList<String> slist = new ArrayList<>();
        for(Sex s : Sex.values()) {
            slist.add(s.getSexName());
        }
        tfSex.setItems(slist);
        tfSex.setLabel(resourceBundle.getString("component_staff_sex"));

        Select<String> tfContract = new Select<>();
        ArrayList<String> clist = new ArrayList<>();
        for(Contract s : Contract.values()) {
            clist.add(s.getTypeName());
        }
        tfContract.setItems(clist);
        tfContract.setRequiredIndicatorVisible(true);
        tfContract.addValueChangeListener(e -> {
            if(e.getValue() == null)
                tfContract.setErrorMessage(resourceBundle.getString("error_staff_department"));
        });
        tfContract.setLabel(resourceBundle.getString("component_staff_contract"));

        Select<Integer> tfDepartment = new Select<>();
        ArrayList<DepartmentRecord> dep = new ArrayList<>(logic.getService().getDepartmentService().getAll());
        ArrayList<Integer> l = new ArrayList<>();
        for(DepartmentRecord d : dep)
            l.add(d.getIddepartment());
        tfDepartment.setItems(l);
        tfDepartment.setRequiredIndicatorVisible(true);
        tfDepartment.addValueChangeListener(e -> {
            if(e.getValue() == null)
                tfDepartment.setErrorMessage(resourceBundle.getString("error_staff_department"));
        });
        tfDepartment.setItemLabelGenerator(item -> logic.getService().getDepartmentService().getOne(item).getName());
        tfDepartment.setLabel(resourceBundle.getString("component_staff_department"));

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
        layout.add(tfEmail, tfFirstname, tfMiddlename, tfSurname, tfSex, tfPhone, tfRoom, tfStreet, tfStreetNumber,
                tfCity, tfZipcode, tfContract, tfDepartment);
        layout.add(createActions(binder));
        layout.add(errorMessage);

        //Binder section

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
        binder.forField(tfContract).withValidator(Objects::nonNull, resourceBundle.getString("error_staff_contract")).bind("contract");
        binder.forField(tfDepartment).withValidator(Objects::nonNull, resourceBundle.getString("error_staff_department")).bind("departmentid");
        binder.setExpirationTimeout(Duration.ofMinutes(0));
        binder.setTopic("staff/" + (record.getIdstaff() < 0 ? UUID.randomUUID() : record.getIdstaff()),
                () -> logic.getService().getStaffService().getOne(record.getIdstaff()));

        return layout;
    }

    private StaffRecord validateRecord(CollaborationBinder<StaffRecord> binder){
        if(binder.isValid()) {
            if(record.getIdstaff() != null)
                if(record.getIdstaff() < 0)
                    record.setIdstaff(null);
            record.setEmail(Utility.convertToString(getValueFromBinder(binder, "email")));
            record.setFirstname(Utility.convertToString(getValueFromBinder(binder, "firstname")));
            record.setMiddlename(Utility.convertToString(getValueFromBinder(binder, "middlename")));
            record.setSurname(Utility.convertToString(getValueFromBinder(binder, "surname")));
            record.setSex(Utility.convertToString(getValueFromBinder(binder, "sex")));
            record.setPhonenumber(Utility.convertToString(getValueFromBinder(binder, "phonenumber")));
            record.setRoomnumber(Utility.convertToString(getValueFromBinder(binder, "roomnumber")));
            record.setStreetname(Utility.convertToString(getValueFromBinder(binder, "streetname")));
            record.setStreetnumber(Utility.convertToString(getValueFromBinder(binder, "streetnumber")));
            record.setCity(Utility.convertToString(getValueFromBinder(binder, "city")));
            record.setZipcode(Utility.convertToString(getValueFromBinder(binder, "zipcode")));
            record.setContract(Utility.convertToString(getValueFromBinder(binder, "contract")));
            DepartmentRecord depRecord = logic.getService().getDepartmentService().getOne(Utility.convertToInt(getValueFromBinder(binder, "departmentid")));
            record.setDepartmentid(depRecord.getIddepartment());
            return record;
        } else return null;
    }

    private Object getValueFromBinder(CollaborationBinder<StaffRecord> binder, String property){
        if(binder.getBinding(property).isPresent())
            return binder.getBinding(property).get().getField().getValue();
        return null;
    }

    private StaffRecord normalizeRecord(StaffRecord record){
        if(record == null) {
            record = new StaffRecord();
            record.setIdstaff(-1);
        }
        return record;
    }

    private String generateTopic(@NotNull StaffRecord record) {
        String prefix = "staff/";
        if(record.getIdstaff() > 0)
            return prefix + record.getIdstaff();
        else
            return prefix + UUID.randomUUID();
    }
}
