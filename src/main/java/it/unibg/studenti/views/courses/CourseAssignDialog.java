package it.unibg.studenti.views.courses;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.data.WorkingHour;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.generated.tables.records.ReferentRecord;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class CourseAssignDialog extends Dialog {
    private final Binder<ReferentRecord> binder;
    private ReferentRecord currentReferent;
    private final CourseAssignGrid assignGrid;
    public CourseAssignDialog(CoursesLogic logic, CourseRecord record, ResourceBundleWrapper resourceBundle) {
        setResizable(true);

        assignGrid = new CourseAssignGrid(logic, record, resourceBundle);

        binder = new Binder<>();
        currentReferent = new ReferentRecord();
        currentReferent.setCourseIdcourse(record.getIdcourse());
        currentReferent.setHours(1.0);
        binder.setBean(currentReferent);

        CollaborationAvatarGroup avatarGroup = new CollaborationAvatarGroup(
                logic.getUserInfo(), generateTopic(record));
        add(avatarGroup);


        //UI

        FormLayout layout = new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4)
        );

        Label errorMessage = new Label(resourceBundle.getString("error_departments_name"));
        errorMessage.setVisible(false);
        errorMessage.getStyle().set("color", Color.ERROR.getColorValue());

        Select<Integer> selectStaff = new Select<>();
        selectStaff.setRequiredIndicatorVisible(true);
        selectStaff.setItemLabelGenerator(e -> logic.getService().getStaffService().getOne(e).getEmail());
        ArrayList<Integer> staffid = new ArrayList<>();
        for(StaffRecord r: logic.getService().getStaffService().getAll())
            staffid.add(r.getIdstaff());
        selectStaff.setItems(staffid);
        selectStaff.setLabel(resourceBundle.getString("component_courses_staff"));

        Select<String> selectType = new Select<>();
        selectType.setRequiredIndicatorVisible(true);
        ArrayList<String> wh = new ArrayList<>();
        for(WorkingHour s : WorkingHour.values())
            wh.add(s.getTypeName());
        selectType.setItems(wh);
        selectType.setLabel(resourceBundle.getString("component_courses_type"));

        NumberField nfHours = new NumberField();
        nfHours.setMin(0);
        nfHours.setHasControls(true);
        nfHours.setValue(1.0);
        nfHours.setStep(1);
        nfHours.setRequiredIndicatorVisible(true);
        nfHours.setLabel(resourceBundle.getString("component_courses_hours"));

        Button btnAdd = new Button(resourceBundle.getString("component_common_button_add"));
        btnAdd.addClickListener(e -> {
            if(binder.isValid()){
                if(logic.insert(binder.getBean())) {
                    currentReferent = new ReferentRecord();
                    currentReferent.setCourseIdcourse(record.getIdcourse());
                    currentReferent.setHours(1.0);
                    errorMessage.setVisible(false);
                    assignGrid.refresh();
                    binder.setBean(currentReferent);
                } else {
                    errorMessage.setVisible(true);
                    errorMessage.setText(resourceBundle.getString("error_common_duplicatekey"));
                }
            } else {
                errorMessage.setVisible(true);
                errorMessage.setText(resourceBundle.getString("error_common_genericfield"));
            }
        });

        layout.add(selectStaff, selectType, nfHours, btnAdd);
        add(layout);
        add(assignGrid);
        add(errorMessage);

        //Binder

        binder.forField(selectStaff).withValidator(Objects::nonNull, "error_courses_staff").bind(ReferentRecord::getStaffIdstaff, ReferentRecord::setStaffIdstaff);
        binder.forField(selectType).withValidator(Objects::nonNull, "error_courses_type").bind(ReferentRecord::getType, ReferentRecord::setType);
        binder.forField(nfHours).withValidator(val -> !val.isNaN() && val >= 0, "error_courses_hours").bind(ReferentRecord::getHours, ReferentRecord::setHours);
    }


    private String generateTopic(@NotNull CourseRecord record) {
        String prefix = "courses/assign/";
        if(record.getIdcourse() > 0)
            return prefix + record.getIdcourse();
        else
            return prefix + UUID.randomUUID();
    }
}
