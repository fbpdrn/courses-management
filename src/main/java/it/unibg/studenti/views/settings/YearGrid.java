package it.unibg.studenti.views.settings;

import com.vaadin.flow.component.grid.Grid;
import it.unibg.studenti.data.service.YearService;
import it.unibg.studenti.generated.tables.records.YearRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class YearGrid extends Grid<YearRecord> {
    private YearDialog dialog;
    private YearService yearService;
    private ResourceBundleWrapper resourceBundle;

    public YearGrid(YearService service, ResourceBundleWrapper resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.yearService = service;
        dialog = new YearDialog(service, this, false, resourceBundle);
        addColumn(YearRecord::getIdyear)
                .setHeader(resourceBundle.getString("component_settings_misc_id"));
        addColumn(YearRecord::getYearstart)
                .setHeader(resourceBundle.getString("component_settings_misc_yearsd"));
        addColumn(YearRecord::getYearend)
                .setHeader(resourceBundle.getString("component_settings_misc_yeared"));
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
