package it.unibg.studenti.views.courses;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import it.unibg.studenti.data.service.ServiceManager;
import it.unibg.studenti.generated.tables.records.CourseRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.UUID;

public class CoursesDialog extends Dialog {
    private CollaborationBinder<CourseRecord> binder;
    private final ServiceManager service;
    private final ResourceBundleWrapper resourceBundle;
    private final CoursesGrid grid;
    private final UserInfo currentUser;
    private CourseRecord record;

    public CoursesDialog(ServiceManager service, ResourceBundleWrapper resourceBundle, CoursesGrid grid, CourseRecord record ,UserInfo currentUser) {
        this.service = service;
        this.resourceBundle = resourceBundle;
        this.grid = grid;
        this.currentUser = currentUser;
        this.record = normalizeRecord(record);
        this.binder = new CollaborationBinder<>(CourseRecord.class, currentUser);
        add(createForm());
    }

    private HorizontalLayout createActions(CollaborationBinder<CourseRecord> binder) {
        HorizontalLayout layout = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_save"));
        btnSave.addClickListener(e -> {
            if(this.record.getIdcourse() > 0){
                if(validateRecord(binder) != null)
                    this.service.getCourseService().update(this.record);
                else
                    return;
            } else
                if(validateRecord(binder) != null)
                    this.service.getCourseService().insert(this.record);
                else
                    return;
            this.grid.refresh();
            this.close();
        });

        Button btnAbort = new Button(resourceBundle.getString("component_common_button_abort"));
        btnAbort.addClickListener(e -> this.close());

        Button btnDelete = new Button(resourceBundle.getString("component_common_button_delete"));
        btnDelete.addClickListener(e -> {
            this.service.getCourseService().delete(this.record);
            this.grid.refresh();
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
                currentUser, topic);

        add(avatarGroup);

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

        layout.add(tfCode, tfSsd, tfName, tfCode, nfCredits, nfStudents, nfHours, tfParams, nfPeriod, nfYear);
        layout.add(createActions(binder));

        //Binder section

        binder.forField(tfCode).withValidator(val -> !val.isBlank() && !val.isEmpty(), resourceBundle.getString("error_courses_name")).bind("code");
        binder.forField(tfSsd).withValidator(val -> !val.isBlank() && !val.isEmpty(), resourceBundle.getString("error_courses_name")).bind("ssd");
        binder.forField(tfName).bind("name");
        binder.forField(nfCredits).bind("credits");
        binder.forField(nfStudents).bind("students");
        binder.forField(nfHours).bind("hours");
        binder.forField(tfParams).bind("param");
        binder.forField(nfPeriod).withValidator(val -> val !=null && (val>0 && !val.isNaN()), resourceBundle.getString("error_courses_value")).bind("period");
        binder.forField(nfYear).withValidator(val -> val!=null && (val>0 && !val.isNaN()), resourceBundle.getString("error_courses_value")).bind("year");
        binder.setExpirationTimeout(Duration.ofMinutes(0));
        binder.setTopic(topic, () -> service.getCourseService().getOne(this.record.getIdcourse()));

        return layout;
    }

    private CourseRecord validateRecord(CollaborationBinder<CourseRecord> binder) {
        if(binder.isValid()) {
            if (this.record.getIdcourse() < 0)
                record.setIdcourse(null);
            this.record.setCode(Utility.convertToString(getValueFromBinder(binder, "code")));
            this.record.setSsd(Utility.convertToString(getValueFromBinder(binder, "ssd")));
            this.record.setName(Utility.convertToString(getValueFromBinder(binder, "name")));
            this.record.setCredits(Utility.convertToDouble(getValueFromBinder(binder, "credits")));
            this.record.setStudents(Utility.convertToDouble(getValueFromBinder(binder, "students")));
            this.record.setHours(Utility.convertToDouble(getValueFromBinder(binder, "hours")));
            this.record.setParam(Utility.convertToString(getValueFromBinder(binder, "param")));
            this.record.setPeriod(Utility.convertToDouble(getValueFromBinder(binder, "period")));
            this.record.setYear(Utility.convertToDouble(getValueFromBinder(binder, "year")));
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
