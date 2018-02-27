package thijszijdel.savesome.ui.displays;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class BillsCalendar {
    public Node getCalendarNode() {

        //JFXDatePickerSkin datePickerSkin = new JFXDatePickerSkin(new JFXDatePicker(LocalDate.now()));

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setShowWeekNumbers(false);

        datePicker.setEffect(null);
        datePicker.setStyle("-fx-effect: null;");

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();

        return popupContent;
    }
}
