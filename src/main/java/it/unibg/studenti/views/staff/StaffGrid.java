package it.unibg.studenti.views.staff;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import it.unibg.studenti.generated.tables.records.StaffRecord;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;

public class StaffGrid extends Grid<StaffRecord> {

    private final StaffLogic logic;

    public StaffGrid(StaffLogic logic, ResourceBundleWrapper resourceBundle) {
        setId("staff-grid");
        setSizeFull();
        this.logic = logic;
        addColumn(StaffRecord::getIdstaff)
                .setHeader("ID");
        addColumn(createNameRenderer())
                .setHeader("Name");
        addColumn(createContactRenderer())
                .setHeader("Contact")
                .setSortable(true);
        addColumn(createLocationRenderer())
                .setHeader("Location");

        addItemClickListener(e -> {
            StaffDialog dialog = new StaffDialog(logic, this, e.getItem(), resourceBundle);
            dialog.open();
        });
        refresh();
    }

    private static Renderer<StaffRecord> createNameRenderer() {
        return LitRenderer.<StaffRecord>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.firstName} ${item.middleName} ${item.surname} </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("firstName", StaffRecord::getFirstname)
                .withProperty("middleName", StaffRecord::getMiddlename)
                .withProperty("surname", StaffRecord::getSurname);
    }

    private static Renderer<StaffRecord> createContactRenderer() {
        return LitRenderer.<StaffRecord>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.email} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.phone}</span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("email", StaffRecord::getEmail)
                .withProperty("phone", StaffRecord::getPhonenumber);
    }

    private static Renderer<StaffRecord> createLocationRenderer() {
        return LitRenderer.<StaffRecord>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span>Room: ${item.room} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.city}, ${item.street}, ${item.streetNumber}, ${item.zipCode}</span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("city", StaffRecord::getCity)
                .withProperty("street", StaffRecord::getStreetname)
                .withProperty("streetNumber", StaffRecord::getStreetnumber)
                .withProperty("zipCode", StaffRecord::getZipcode)
                .withProperty("room", StaffRecord::getRoomnumber);
    }

    public void refresh(StaffRecord item) {
        getDataCommunicator().refresh(item);
    }

    public void refresh() {
        setItems(logic.getService().getStaffService().getAll());
    }

    public void refresh(String str) {
        setItems(logic.getService().getStaffService().getFiltered(str));
    }
}