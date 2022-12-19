module OOS.PR5.neu {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;

    opens com.oos.OOS_PR5_neu to javafx.fxml;
    opens bank;
    opens bank.exceptions;
    exports bank.exceptions;
    exports bank;
    exports com.oos.OOS_PR5_neu;


}