import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

import java.awt.*;

public class MainviewController {

    @FXML
    private TextField textField;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    private StringProperty textProperty;

    public void initialize() {
        // bind the text field's text property to the controller's textProperty
        textField.textProperty().bindBidirectional(textProperty);
    }
    @FXML
    private void handleButton1Action(ActionEvent event) {
        textProperty.set("Button 1 clicked");
    }

    @FXML
    private void handleButton2Action(ActionEvent event) {
        textProperty.set("Button 2 clicked");
    }

    public StringProperty textPropertyProperty() {
        return textProperty;
    }

    public void setTextProperty(StringProperty textProperty) {
        this.textProperty = textProperty;
    }
    

   

}
