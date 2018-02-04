package thijszijdel.savesome.models;

import thijszijdel.savesome.constants.Theme;

public class Settings {

    private final String bgColorDarkL = "#353b48";
    private final String bgColorDarkD = "#2f3640";

    private final String bgColorLightL = "#f5f6fa";
    private final String bgColorLightD = "#dcdde1";

    private final String alertColor ="#e84118";

    private  Theme theme;

    /**
     * Constructor for defining witch theme to use
     *
     * @param theme type of constants/Theme.java
     */
    public Settings(Theme theme){
        this.theme = theme;
    }

    public String getBackground(){
        return bgColorLightL;
    }
}
