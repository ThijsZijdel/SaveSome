package thijszijdel.savesome.connections;

import thijszijdel.savesome.constants.Theme;
import thijszijdel.savesome.settings.SettingsData;
import thijszijdel.savesome.connections.Balance.Balance;

public class Settings {

    // TODO: enums..!
    private static final String bgColorDarkL = "#353b48";
    private static final String bgColorDarkD = "#2f3640";

    private static final String bgColorLightL = "#f5f6fa";
    private static final String bgColorLightD = "#dcdde1";

    private static final String alertColorD ="#c23616";
    private static final String alertColor ="#e84118";
    private static final String accentColor ="#00a8ff";

    private static final String textColorW ="#ffffff";

    private static final String succesColor = "#44bd32";
    private static Balance preferredBalance = null;

    private  Theme theme;

    private final SettingsData data;

    public static final String EURO = "\u20AC";
    /**
     * Constructor for defining witch theme to use
     *
     * @param theme type of constants/Theme.java
     */
    public Settings(Theme theme){
        this.data = new SettingsData();
        this.theme = theme;
    }

    public static String getBackground(){
        return bgColorLightL;
    }

    public static String getAlertColor(){
        return alertColor;
    }

    public static String getAlertColorD() {
        return alertColorD;
    }

    public static String getAccentColor() {
        return accentColor;
    }

    public static String getTextColorW() {
        return textColorW;
    }

    public Balance getPreferredBalance() {
        return preferredBalance;
    }

    public static String getSuccesColor() {
        return succesColor;
    }
}
