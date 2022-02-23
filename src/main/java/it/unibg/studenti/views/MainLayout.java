package it.unibg.studenti.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import it.unibg.studenti.generated.tables.records.UserRecord;
import it.unibg.studenti.security.AuthenticatedUser;
import it.unibg.studenti.views.about.AboutView;
import it.unibg.studenti.views.courses.CoursesView;
import it.unibg.studenti.views.departments.DepartmentsView;
import it.unibg.studenti.views.home.HomeView;
import it.unibg.studenti.views.planner.PlannerView;
import it.unibg.studenti.views.settings.SettingsView;
import it.unibg.studenti.views.staff.StaffView;
import it.unibg.studenti.views.studyplan.StudyPlanView;
import it.unibg.studenti.views.utils.ResourceBundleWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-s");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font size and margin
                addClassNames("me-s", "text-l");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }

    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;
    private ResourceBundleWrapper resourceBundle;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, @Autowired ResourceBundleWrapper resourceBundleWrapper) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.resourceBundle = resourceBundleWrapper;

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");
        Component langSwitch = MenuLangSwitch(VaadinSession.getCurrent().getLocale(), resourceBundle);
        langSwitch.getElement().getStyle()
                .set("float", "right")
                .set("margin-right", "10px")
                .set("margin-left","auto");
        Header header = new Header(toggle, viewTitle, langSwitch);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2(resourceBundle.getString("app_name"));
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        com.vaadin.flow.component.html.Section section =
                new com.vaadin.flow.component.html.Section(appName, createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }

        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo(resourceBundle.getString("app_home"), "la la-home", HomeView.class), //

                new MenuItemInfo(resourceBundle.getString("app_studyplan"), "la la-table", StudyPlanView.class), //

                new MenuItemInfo(resourceBundle.getString("app_planner"), "la la-book-open", PlannerView.class), //

                new MenuItemInfo(resourceBundle.getString("app_courses"), "la la-book", CoursesView.class), //

                new MenuItemInfo(resourceBundle.getString("app_staff"), "la la-user-circle", StaffView.class), //

                new MenuItemInfo(resourceBundle.getString("app_departments"), "la la-building", DepartmentsView.class), //

                new MenuItemInfo(resourceBundle.getString("app_settings"), "la la-wrench", SettingsView.class), //

                new MenuItemInfo(resourceBundle.getString("app_about"), "la la-info-circle", AboutView.class), //

        };
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        Optional<UserRecord> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserRecord user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName() + " " + user.getSurname(), user.getProfilepictureurl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem(resourceBundle.getString("component_login_disconnect"), e -> authenticatedUser.logout());

            Span name = new Span(user.getName() + " " + user.getSurname());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", resourceBundle.getString("component_login_signin"));
            layout.add(loginLink);
        }

        return layout;
    }

    private static Component MenuLangSwitch(Locale locale, ResourceBundleWrapper resourceBundleWrapper){
        Image langImage;
        if(locale.equals(Locale.ITALY))
            langImage = new Image("images/countries/united-kingdom.png", "English (GB)");
        else
            langImage = new Image("images/countries/italy.png", "Italian (IT)");
        langImage.setWidth("24px");
        langImage.addClickListener(e -> {
            if(locale.equals(Locale.ITALY)) {
                VaadinSession.getCurrent().setLocale(Locale.UK);
                resourceBundleWrapper.setLocale(Locale.UK);
            }
            else {
                VaadinSession.getCurrent().setLocale(Locale.ITALY);
                resourceBundleWrapper.setLocale(Locale.ITALY);
            }
            UI.getCurrent().getPage().reload();
        });
        langImage.getElement().getStyle().set("float", "right");
        return langImage;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
