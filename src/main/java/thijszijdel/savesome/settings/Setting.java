package thijszijdel.savesome.settings;

public class Setting {
    private int id;
    private String name, description, value;

    public Setting(int id, String name, String description, String value){
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
