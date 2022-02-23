package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import it.unibg.studenti.data.service.YearService;
import it.unibg.studenti.generated.tables.records.YearRecord;
import org.springframework.beans.factory.annotation.Autowired;

public class YearDialog extends Dialog {
    private YearRecord record;
    private Binder<YearRecord> binder;
    private DatePicker dtStart;
    private DatePicker dtEnd;

    public YearDialog(@Autowired YearService service, YearGrid yearGrid, boolean isNew){
        binder = new Binder<>();
        FormLayout layout = new FormLayout();

        dtStart = new DatePicker("Year Start Date");
        dtEnd = new DatePicker("Year Start Date");
        dtStart.addValueChangeListener(e -> {
            dtEnd.setMin(e.getValue());
            binder.getBean().setYearstart(String.valueOf(dtStart.getValue()));
        });
        dtEnd.addValueChangeListener(e -> {
            dtStart.setMax(e.getValue());
            binder.getBean().setYearend(String.valueOf(dtEnd.getValue()));
        });

        layout.add(dtStart, dtEnd);

        HorizontalLayout actions = new HorizontalLayout();

        Button btnAbort = new Button("Abort");
        btnAbort.addClickListener(e -> this.close());
        Button btnSave = new Button("Save");
        btnSave.addClickListener(e -> {
            binder.getBean().setYearstart(String.valueOf(dtStart.getValue()));
            binder.getBean().setYearend(String.valueOf(dtEnd.getValue()));
            if(isNew)
                service.insert(binder.getBean());
            else
                service.update(binder.getBean());
            yearGrid.refresh();
            this.close();
        });
        if(!isNew) {
            Button btnDelete = new Button("Delete");
            btnDelete.addClickListener(e -> {
                service.delete(binder.getBean());
                yearGrid.refresh();
                this.close();
            });
            actions.add(btnSave, btnAbort, btnDelete);
        }
        else
            actions.add(btnSave, btnAbort);
        layout.add(actions);
        add(layout);
    }

    public void openAndSetBinder(YearRecord record) {
        this.record = record;
        binder.setBean(record);
        this.open();
    }
}
