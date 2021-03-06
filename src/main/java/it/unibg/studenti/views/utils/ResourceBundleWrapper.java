package it.unibg.studenti.views.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Scope("session")
@Component
public class ResourceBundleWrapper {
    private transient ResourceBundle resourceBundle;
    private static final String BASENAME = "Localization/messages";

    public ResourceBundleWrapper() {
        Locale.setDefault(Locale.UK);
        this.resourceBundle = ResourceBundle.getBundle(BASENAME, Locale.getDefault());
    }

    public Locale getLocale() {
        return this.resourceBundle.getLocale();
    }

    public void setLocale(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BASENAME, locale);
    }

    public String getString(String string) {
        try {
            return this.resourceBundle.getString(string);
        } catch (MissingResourceException mre) {
            return "{{MRE: " + string + "}}";
        }
    }
}
