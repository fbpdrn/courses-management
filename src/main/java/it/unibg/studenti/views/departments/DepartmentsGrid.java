package it.unibg.studenti.views.departments;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import it.unibg.studenti.data.service.DepartmentService;
import it.unibg.studenti.generated.tables.records.DepartmentRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class DepartmentsGrid extends Grid<DepartmentRecord> {

    private final DepartmentService service;
    public DepartmentsGrid(DepartmentService service, ResourceBundleWrapper resourceBundle){
        setSizeFull();
        this.service = service;
        DepartmentsDialog dialog = new DepartmentsDialog(service, this, false, resourceBundle);
        addColumn(DepartmentRecord::getIddepartment)
                .setHeader(resourceBundle.getString("component_departments_id"))
                .setFlexGrow(20)
                .setVisible(false);
        addColumn(DepartmentRecord::getName)
                .setHeader(resourceBundle.getString("component_departments_name"))
                .setFlexGrow(20)
                .setSortable(true);
        addColumn(DepartmentRecord::getDescription)
                .setHeader(resourceBundle.getString("component_departments_description"))
                .setFlexGrow(30);
        addColumn(createLocationRenderer())
                .setHeader(resourceBundle.getString("component_departments_location"))
                .setAutoWidth(true);
        addColumn(DepartmentRecord::getPhone)
                .setHeader(resourceBundle.getString("component_departments_phone"))
                .setFlexGrow(20);
        addItemClickListener(e -> dialog.openAndSetBinder(e.getItem()));
        setItems(service.getAll());
    }

    private static Renderer<DepartmentRecord> createLocationRenderer() {
        return LitRenderer.<DepartmentRecord>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.city} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.street}" + ", ${item.streetNumber} </br> ${item.zipCode}</span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("city", DepartmentRecord::getCity)
                .withProperty("street", DepartmentRecord::getStreetname)
                .withProperty("streetNumber", DepartmentRecord::getStreetnumber)
                .withProperty("zipCode", DepartmentRecord::getZipcode);
    }

    public void refresh(DepartmentRecord item) {
        getDataCommunicator().refresh(item);
    }

    public void refresh() {
        setItems(service.getAll());
    }
}
