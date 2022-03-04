package it.unibg.studenti.views.planner;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import it.unibg.studenti.data.Color;
import it.unibg.studenti.generated.tables.records.DegreeRecord;
import it.unibg.studenti.generated.tables.records.YearRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import it.unibg.studenti.views.utils.Utility;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PlannerDegreeDialog extends Dialog {
    private final CollaborationBinder<DegreeRecord> binder;
    private final PlannerLogic logic;
    private final ResourceBundleWrapper resourceBundle;
    private final DegreeRecord record;

    private Label errorMessage;

    public PlannerDegreeDialog(PlannerLogic logic, DegreeRecord record, ResourceBundleWrapper resourceBundle){
        this.logic = logic;
        this.record = normalizeRecord(record);
        this.resourceBundle = resourceBundle;
        this.binder = new CollaborationBinder<>(DegreeRecord.class, logic.getUserInfo());
        add(createForm());
    }

    private HorizontalLayout createActions(CollaborationBinder<DegreeRecord> binder) {
        HorizontalLayout layout = new HorizontalLayout();
        Button btnSave = new Button(resourceBundle.getString("component_common_button_saveandrefresh"));
        btnSave.addClickListener(e -> {
            if(validateRecord(binder) != null) {
                if(record.getIddegree() != null) {
                    if(logic.update(record)) {
                        logic.getView().getSelectYear().setValue(null);
                        this.close();
                    }
                } else {
                    if(logic.insert(record)) {
                        logic.getView().getSelectYear().setValue(null);
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
        Button btnDelete = new Button(resourceBundle.getString("component_common_button_deleteandrefresh"));
        btnDelete.addClickListener(e -> {
            logic.delete(record);
            logic.getView().getSelectYear().setValue(null);
            this.close();
        });
        if(record.getIddegree() < 0)
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

        TextField tfName = new TextField();
        tfName.setLabel(resourceBundle.getString("component_planner_name"));
        tfName.setRequired(true);

        TextField tfCode = new TextField();
        tfCode.setLabel(resourceBundle.getString("component_planner_code"));
        tfCode.setRequired(true);

        NumberField nfAccYear = new NumberField();
        nfAccYear.setLabel(resourceBundle.getString("component_planner_accyear"));
        nfAccYear.setHasControls(true);
        nfAccYear.setMin(1);
        nfAccYear.setStep(1);
        nfAccYear.setRequiredIndicatorVisible(true);

        NumberField nfTotalCredits = new NumberField();
        nfTotalCredits.setHasControls(true);
        nfTotalCredits.setMin(0);
        nfTotalCredits.setLabel(resourceBundle.getString("component_planner_totalcredits"));
        nfTotalCredits.setRequiredIndicatorVisible(true);

        Select<Integer> selectYear = new Select<>();
        ArrayList<YearRecord> yearList = new ArrayList<>(logic.getService().getYearService().getAll());
        ArrayList<Integer> l = new ArrayList<>();
        for(YearRecord d : yearList)
            l.add(d.getIdyear());
        selectYear.setItems(l);
        selectYear.setLabel(resourceBundle.getString("component_planner_year"));
        selectYear.setRequiredIndicatorVisible(true);
        selectYear.addValueChangeListener(e -> {
            if(e.getValue() == null)
                selectYear.setErrorMessage(resourceBundle.getString("error_planner_degree_year"));
        });
        selectYear.setItemLabelGenerator(e -> logic.getService().getYearService().getOne(e).getYearstart() + " → " +
                logic.getService().getYearService().getOne(e).getYearend());

        layout.add(tfName, tfCode, nfAccYear, nfTotalCredits, selectYear);
        layout.add(createActions(binder));
        layout.add(errorMessage);
        //Binder section

        binder.forField(tfName)
                .withValidator(name -> name.trim().length() > 0, resourceBundle.getString("error_planner_degree_name"))
                .withValidator(name -> !name.isBlank(), resourceBundle.getString("error_planner_degree_name")).bind("name");
        binder.forField(tfCode)
                .withValidator(code -> code.trim().length() > 0, resourceBundle.getString("error_planner_degree_code"))
                .withValidator(code -> !code.isBlank(), resourceBundle.getString("error_planner_degree_code")).bind("code");
        binder.forField(nfAccYear)
                .withValidator(val -> val !=null && (val>=1 && !val.isNaN()), resourceBundle.getString("error_planner_degree_accyear")).bind("accyear");
        binder.forField(nfTotalCredits)
                .withValidator(val -> val !=null && (val>=0 && !val.isNaN()), resourceBundle.getString("error_planner_degree_totalcredits")).bind("totalcredits");
        binder.forField(selectYear).withValidator(Objects::nonNull, resourceBundle.getString("error_planner_degree_year")).bind("yearid");
        binder.setExpirationTimeout(Duration.ofMinutes(0));
        binder.setTopic("degree/" + (record.getIddegree() < 0 ? UUID.randomUUID() : record.getIddegree()),
                () -> logic.getService().getDegreeService().getOne(record.getIddegree()));
        return layout;
    }

    private DegreeRecord validateRecord(CollaborationBinder<DegreeRecord> binder){
        if(binder.isValid()) {
            if(record.getIddegree() != null)
                if(record.getIddegree() < 0)
                    record.setIddegree(null);
            record.setName(Utility.convertToString(getValueFromBinder(binder, "name")));
            record.setCode(Utility.convertToString(getValueFromBinder(binder, "code")));
            record.setAccyear(Utility.convertToDouble(getValueFromBinder(binder, "accyear")));
            record.setTotalcredits(Utility.convertToDouble(getValueFromBinder(binder, "totalcredits")));
            YearRecord yearRecord = logic.getService().getYearService().getOne(Utility.convertToInt(getValueFromBinder(binder, "yearid")));
            record.setYearid(yearRecord.getIdyear());
            return record;
        } else return null;
    }

    private Object getValueFromBinder(CollaborationBinder<DegreeRecord> binder, String property){
        if(binder.getBinding(property).isPresent())
            return binder.getBinding(property).get().getField().getValue();
        return null;
    }

    private DegreeRecord normalizeRecord(DegreeRecord record){
        if(record == null) {
            record = new DegreeRecord();
            record.setIddegree(-1);
        }
        return record;
    }

    private String generateTopic(@NotNull DegreeRecord record) {
        String prefix = "degree/";
        if(record.getIddegree() > 0)
            return prefix + record.getIddegree();
        else
            return prefix + UUID.randomUUID();
    }
}
