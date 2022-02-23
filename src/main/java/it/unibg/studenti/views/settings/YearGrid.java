package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.grid.Grid;
import it.unibg.studenti.data.service.YearService;
import it.unibg.studenti.generated.tables.records.YearRecord;

public class YearGrid extends Grid<YearRecord> {
    private YearDialog dialog;
    private YearService yearService;

    public YearGrid(YearService service) {
        this.yearService = service;
        dialog = new YearDialog(service, this, false);
        addColumn(YearRecord::getIdyear)
                .setHeader("ID");
        addColumn(YearRecord::getYearstart)
                .setHeader("Year Start");
        addColumn(YearRecord::getYearend)
                .setHeader("Year End");
        addItemClickListener(e -> {
            dialog.openAndSetBinder(e.getItem());
        });
    }
    public YearDialog getDialog() {
        return dialog;
    }

    public void setDialog(YearDialog dialog) {
        this.dialog = dialog;
    }

    public void refresh() {
        setItems(yearService.getAll());
    }
}
