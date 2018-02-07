package thijszijdel.savesome.connections;

import thijszijdel.savesome.constants.Theme;
import thijszijdel.savesome.database.data.SettingsData;
import thijszijdel.savesome.models.Balance;

public class Settings {

    private static final String bgColorDarkL = "#353b48";
    private static final String bgColorDarkD = "#2f3640";

    private static final String bgColorLightL = "#f5f6fa";
    private static final String bgColorLightD = "#dcdde1";

    private static final String alertColor ="#e84118";


    private static final String textColor ="#ffffff";


    private static Balance preferredBalance = null;

    private  Theme theme;

    private final SettingsData data;


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

    public static String getTextColor() {
        return textColor;
    }

    public Balance getPreferredBalance() {
        return preferredBalance;
    }
}
