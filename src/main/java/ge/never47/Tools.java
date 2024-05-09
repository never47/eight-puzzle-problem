package ge.never47;

import javafx.scene.control.Alert;

public class Tools { // helper class
    /*
        Creates default design windows
        And shows some alarm
     */
    public static void showAlarm(Alert.AlertType alertType, String Title,
                                 String Content){
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setContentText(Content);

        alert.showAndWait();
    }
}
