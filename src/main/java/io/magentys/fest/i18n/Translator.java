package io.magentys.fest.i18n;

import java.util.Locale;

public interface Translator {

    String translate(final String term);

    void setLocale(Locale locale);

}
