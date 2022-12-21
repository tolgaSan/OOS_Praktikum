import bank.PrivateBank;
import bank.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    private final PrivateBank bank;

    {
        try {
            bank = new PrivateBank("Unibank", 0.04, 0.02, "UnisArchiv");
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (OutgoingInterestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IncomingInterestException e) {
            throw new RuntimeException(e);
        }
    }


    private Stage stage;
    private Scene scene;


    //Liste die veränderungen signalisiert
    private ObservableList<String> accountList = FXCollections.observableArrayList();

    @FXML
    private Button addButton;
    @FXML
    private Text text;
    @FXML
    private ListView<String> accountListView;
    @FXML
    private Parent root;

    private void update(){

        try{
            bank.readAccounts();
        }catch (AccountAlreadyExistsException e) {
            System.out.println("Exception Acc existiert schon! ");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        accountList.clear();
        accountList.addAll(bank.getAllAccounts());
        accountListView.setItems(accountList);
        System.out.println("accounts geupdated: " + accountList);
    }

    @FXML
    private void initialize() {
        //werte in der Liste speichern
        update();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem viewAccount = new MenuItem("Account Transaktionen Details");
        MenuItem deleteAccount = new MenuItem("Account löschen!");

        //Unterpunkte zu dem Kontextmenü adden
        contextMenu.getItems().addAll(viewAccount, deleteAccount);
        accountListView.setContextMenu(contextMenu);

        //An object reference that may be updated atomically
        AtomicReference<String> selectedAccount = new AtomicReference<>();

        accountListView.setOnMouseClicked(mouseEvent -> {
            selectedAccount.set(String.valueOf(accountListView.getSelectionModel().getSelectedItems()));
            String account = selectedAccount.toString().replace("[", "").replace("]", "");
            System.out.println(selectedAccount + " ist ausgewählt");
            text.setText("Account " + selectedAccount + " ist ausgewählt");

            // goes to AccountView if double-click on item
            if (mouseEvent.getClickCount() == 2)
                try {
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("accountView.fxml")));
                    root = loader.load();

                    AccountController accountController = loader.getController();
                    accountController.setupData(bank, account);

                    stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        });


        deleteAccount.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Bestätigung Account löschen");
            confirmation.setContentText("Sicher?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    bank.deleteAccount(selectedAccount.toString().replace("[", "").replace("]", ""));

                } catch (AccountDoesNotExistException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(selectedAccount + " ist gelöscht");
                text.setText(selectedAccount + " istt gelöscht");
                update();
            }
        });

        viewAccount.setOnAction(event -> {
            stage = (Stage) root.getScene().getWindow();

            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("accountview.fxml")));
                root = loader.load();

                AccountController accountController = loader.getController();
                accountController.setupData(bank, selectedAccount.toString().replace("[", "").replace("]", ""));
            } catch (IOException e) {
                e.printStackTrace();
            }

            scene = new Scene(root);
            stage.setTitle(selectedAccount.toString());
            stage.setScene(scene);
            stage.show();
        });

        addButton.setOnMouseClicked(event -> {
            text.setText("");
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Füge ein neues Account hinzu");
            dialog.setHeaderText("Account hinzufügen");
            dialog.getDialogPane().setMinWidth(300);


            Label nameLabel = new Label("Name: ");
            TextField nameTextField = new TextField();

            GridPane grid = new GridPane();
            grid.add(nameLabel, 2, 1);
            grid.add(nameTextField, 3, 1);
            dialog.getDialogPane().setContent(grid);
            dialog.setResizable(true);

            ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == buttonTypeOk) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    if (!Objects.equals(nameTextField.getText(), "")) {

                        try {
                            bank.createAccount(nameTextField.getText());
                            text.setText("Account " + nameTextField.getText() + " wurde hinzugefügt");
                        } catch (Exception e) {
                            alert.setContentText("Accountname bereits belegt ");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Account " + nameTextField.getText() + " ist bereits vorhanden!");
                            }
                            System.out.println(e.getMessage());
                        }
                        update();
                    }
                    else {
                        alert.setContentText("Falsche eingae");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            text.setText("Acc wurde nicht hinzugefügt");
                        }
                    }

                }
                return null;
            });

            dialog.show();
        });
    }








}