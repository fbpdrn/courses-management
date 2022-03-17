package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.data.service.DepartmentService;
import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;


public class DepartmentsDialog extends Dialog {
    private final Binder<DepartmentRecord> binder;

    public DepartmentsDialog(DepartmentsLogic logic, DepartmentsGrid departmentsGrid,
                             boolean isNew, ResourceBundleWrapper resourceBundle){
        binder = new Binder<>();
        FormLayout layout = new FormLayout();
        layout.setId("form-layout");

        Label errorMessage = new Label(resourceBundle.getString("error_departments_name"));
        errorMessage.setId("error-label");
        errorMessage.setVisible(false);
        errorMessage.getStyle().set("color", Color.ERROR.getColorValue());

        TextField tfName = new TextField();
        tfName.setId("name-field");
        tfName.setRequired(true);
        tfName.addValueChangeListener(e -> {
           if(!(tfName.getValue().trim().length() >0) || tfName.getValue().isBlank())
               tfName.setErrorMessage(resourceBundle.getString("error_departments_name"));
        });
        tfName.setValueChangeMode(ValueChangeMode.EAGER);
        tfName.setLabel(resourceBundle.getString("component_departments_name"));

        TextField tfDescription = new TextField();
        tfDescription.setId("description-field");
        tfDescription.setLabel(resourceBundle.getString("component_departments_description"));

        TextField tfLocation = new TextField();
        tfLocation.setId("location-field");
        tfLocation.setLabel(resourceBundle.getString("component_departments_location"));

        TextField tfStreet = new TextField();
        tfStreet.setId("street-field");
        tfStreet.setLabel(resourceBundle.getString("component_departments_street"));

        TextField tfStreetNum = new TextField();
        tfStreetNum.setId("streetnum-field");
        tfStreetNum.setLabel(resourceBundle.getString("component_departments_streetnum"));

        TextField tfPhone = new TextField();
        tfPhone.setId("phone-field");
        tfPhone.setLabel(resourceBundle.getString("component_departments_phone"));

        layout.add(tfName, tfDescription, tfLocation, tfStreet, tfStreetNum, tfPhone);

        binder.forField(tfName)
                .withValidator(name -> name.trim().length() > 0, resourceBundle.getString("error_departments_name"))
                .withValidator(name -> !name.isBlank(), resourceBundle.getString("error_departments_name"))
                .withValidationStatusHandler(status -> tfName.setErrorMessage(status.getMessage()
                        .orElse(resourceBundle.getString("error_common_unknown"))))
                .asRequired()
                .bind(DepartmentRecord::getName, DepartmentRecord::setName);
        binder.forField(tfDescription).bind(DepartmentRecord::getDescription, DepartmentRecord::setDescription);
        binder.forField(tfLocation).bind(DepartmentRecord::getCity, DepartmentRecord::setCity);
        binder.forField(tfStreet).bind(DepartmentRecord::getStreetname, DepartmentRecord::setStreetname);
        binder.forField(tfStreetNum).bind(DepartmentRecord::getStreetnumber, DepartmentRecord::setStreetnumber);
        binder.forField(tfPhone).bind(DepartmentRecord::getPhone, DepartmentRecord::setPhone);

        HorizontalLayout actions = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));
        btnSave.setId("save-button");
        btnSave.addClickListener(e -> {
            if (binder.isValid()) {
                if (isNew)
                    if(logic.insert(binder.getBean())){
                        departmentsGrid.refresh();
                        this.close();
                    } else {
                        errorMessage.setText(resourceBundle.getString("error_common_duplicatekey"));
                        errorMessage.setVisible(true);
                    }
                else
                    if(logic.update(binder.getBean())) {
                        departmentsGrid.refresh();
                        this.close();
                    } else {
                        errorMessage.setText(resourceBundle.getString("error_common_duplicatekey"));
                        errorMessage.setVisible(true);
                    }
            } else {
                errorMessage.setText(resourceBundle.getString("error_departments_name"));
                errorMessage.setVisible(true);
            }
        });
        Button btnAbort = new Button(resourceBundle.getString("component_common_button_abort"));
        btnAbort.setId("abort-button");
        btnAbort.addClickListener(e -> this.close());
        Button btnDelete = new Button(resourceBundle.getString("component_common_button_delete"));
        btnDelete.setId("delete-button");
        btnDelete.addClickListener(e -> {
            logic.delete(binder.getBean());
            departmentsGrid.refresh();
            this.close();
        });
        if(isNew)
            actions.add(btnSave, btnAbort);
        else
            actions.add(btnSave, btnAbort, btnDelete);
        layout.add(actions);
        layout.add(errorMessage);
        layout.setColspan(errorMessage, 2);
        add(layout);
    }

    public void openAndSetBinder(DepartmentRecord record) {
        binder.setBean(record);
        this.open();
    }
}
