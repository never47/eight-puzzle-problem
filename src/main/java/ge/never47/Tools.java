package ge.never47;

import javafx.scene.control.Alert;

public class Tools {
    public static void showAlarm(Alert.AlertType alertType, String Title,
                                 String Content){
        // creating alert
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setContentText(Content);

        alert.showAndWait();
    }
}
