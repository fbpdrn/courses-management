package it.unibg.studenti.views.courses;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.UUID;

public class CoursesDialog extends Dialog {
    private final CollaborationBinder<CourseRecord> binder;
    private final CoursesLogic logic;
    private final ResourceBundleWrapper resourceBundle;
    private final CoursesGrid grid;
    private final CourseRecord record;

    private Label errorMessage;

    public CoursesDialog(CoursesLogic logic, CoursesGrid grid,
                         CourseRecord record, ResourceBundleWrapper resourceBundle) {
        this.grid = grid;
        this.logic = logic;
        this.resourceBundle = resourceBundle;
        this.record = normalizeRecord(record);
        this.binder = new CollaborationBinder<>(CourseRecord.class, logic.getUserInfo());
        add(createForm());
    }

    private HorizontalLayout createActions(CollaborationBinder<CourseRecord> binder) {
        HorizontalLayout layout = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));
        btnSave.addClickListener(e -> {
            if(validateRecord(binder) != null) {
                if(record.getIdcourse() != null) {
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
            logic.getService().getCourseService().delete(record);
            grid.refresh();
            this.close();
        });
        if(this.record.getIdcourse()<0)
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

        TextField tfCode = new TextField();
        tfCode.setLabel(resourceBundle.getString("component_courses_code"));
        tfCode.setValueChangeMode(ValueChangeMode.EAGER);
        tfCode.setRequired(true);

        TextField tfSsd = new TextField();
        tfSsd.setLabel(resourceBundle.getString("component_courses_ssd"));
        tfSsd.setValueChangeMode(ValueChangeMode.EAGER);
        tfSsd.setRequired(true);

        TextField tfName = new TextField();
        tfName.setLabel(resourceBundle.getString("component_courses_name"));

        NumberField nfCredits = new NumberField();
        nfCredits.setStep(0.5);
        nfCredits.setMin(0);
        nfCredits.setHasControls(true);
        nfCredits.setLabel(resourceBundle.getString("component_courses_credits"));

        NumberField nfStudents = new NumberField();
        nfStudents.setStep(1);
        nfStudents.setMin(0);
        nfStudents.setHasControls(true);
        nfStudents.setLabel(resourceBundle.getString("component_courses_students"));

        NumberField nfHours = new NumberField();
        nfHours.setStep(1);
        nfHours.setMin(0);
        nfHours.setHasControls(true);
        nfHours.setLabel(resourceBundle.getString("component_courses_hours"));

        TextField tfParams = new TextField();
        tfParams.setLabel(resourceBundle.getString("component_courses_param"));

        NumberField nfPeriod = new NumberField();
        nfPeriod.setLabel(resourceBundle.getString("component_courses_period"));
        nfPeriod.setStep(1);
        nfPeriod.setMin(0);
        nfPeriod.setHasControls(true);
        nfPeriod.setRequiredIndicatorVisible(true);

        NumberField nfYear = new NumberField();
        nfYear.setLabel(resourceBundle.getString("component_courses_year"));
        nfYear.setStep(1);
        nfYear.setMin(0);
        nfYear.setHasControls(true);
        nfYear.setRequiredIndicatorVisible(true);

        TextField tfYearOff = new TextField();
        tfYearOff.setLabel(resourceBundle.getString("component_courses_yearoff"));
        tfYearOff.setValueChangeMode(ValueChangeMode.EAGER);
        tfYearOff.setRequired(true);

        layout.add(tfCode, tfSsd, tfName, tfCode, nfCredits, nfStudents, nfHours, tfParams, nfPeriod, nfYear, tfYearOff);
        layout.add(createActions(binder));
        layout.add(errorMessage);
        //Binder section

        binder.forField(tfCode).withValidator(val -> !val.isBlank() && !val.isEmpty(), resourceBundle.getString("error_courses_name")).bind("code");
        binder.forField(tfSsd).withValidator(val -> !val.isBlank() && !val.isEmpty(), resourceBundle.getString("error_courses_ssd")).bind("ssd");
        binder.forField(tfName).bind("name");
        binder.forField(nfCredits).bind("credits");
        binder.forField(nfStudents).bind("students");
        binder.forField(nfHours).bind("hours");
        binder.forField(tfParams).bind("param");
        binder.forField(nfPeriod).withValidator(val -> val !=null && (val>0 && !val.isNaN()), resourceBundle.getString("error_courses_value")).bind("period");
        binder.forField(nfYear).withValidator(val -> val!=null && (val>0 && !val.isNaN()), resourceBundle.getString("error_courses_value")).bind("year");
        binder.forField(tfYearOff).withValidator(val -> !val.isBlank() && !val.isEmpty(), resourceBundle.getString("error_courses_yearoff")).bind("yearoff");
        binder.setExpirationTimeout(Duration.ofMinutes(0));
        binder.setTopic(topic, () -> logic.getService().getCourseService().getOne(record.getIdcourse()));

        return layout;
    }

    private CourseRecord validateRecord(CollaborationBinder<CourseRecord> binder) {
        if(binder.isValid()) {
            if(record.getIdcourse() != null)
                if (record.getIdcourse() < 0)
                    record.setIdcourse(null);
            record.setCode(Utility.convertToString(getValueFromBinder(binder, "code")));
            record.setSsd(Utility.convertToString(getValueFromBinder(binder, "ssd")));
            record.setName(Utility.convertToString(getValueFromBinder(binder, "name")));
            record.setCredits(Utility.convertToDouble(getValueFromBinder(binder, "credits")));
            record.setStudents(Utility.convertToDouble(getValueFromBinder(binder, "students")));
            record.setHours(Utility.convertToDouble(getValueFromBinder(binder, "hours")));
            record.setParam(Utility.convertToString(getValueFromBinder(binder, "param")));
            record.setPeriod(Utility.convertToDouble(getValueFromBinder(binder, "period")));
            record.setYear(Utility.convertToDouble(getValueFromBinder(binder, "year")));
            record.setYearoff(Utility.convertToString(getValueFromBinder(binder, "yearoff")));
            return record;
        }
        else return null;
    }

    private Object getValueFromBinder(CollaborationBinder<CourseRecord> binder, String property){
        if(binder.getBinding(property).isPresent())
            return binder.getBinding(property).get().getField().getValue();
        return null;
    }

    private CourseRecord normalizeRecord(CourseRecord record){
        if(record == null) {
            record = new CourseRecord();
            record.setIdcourse(-1);
        }
        return record;
    }

    private String generateTopic(@NotNull CourseRecord record) {
        String prefix = "course/";
        if(record.getIdcourse() > 0)
            return prefix + record.getIdcourse();
        else
            return prefix + UUID.randomUUID();
    }
}
