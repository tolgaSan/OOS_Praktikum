import bank.*;
import bank.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class AccountController {
    private final ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    private PrivateBank bank;

    @FXML
    public Text text;
    @FXML
    public MenuButton addButton;
    @FXML
    public MenuItem payment;
    @FXML
    public MenuItem incoming;
    @FXML
    public MenuItem outgoing;
    @FXML
    public Parent root;
    @FXML
    public MenuButton optionsButton;
    @FXML
    public MenuItem allTransaction;
    @FXML
    public MenuItem ascending;
    @FXML
    public MenuItem descending;
    @FXML
    public MenuItem positive;
    @FXML
    public MenuItem negative;
    @FXML
    public ListView<Transaction> transactionsListView;
    @FXML
    public Text accountName;
    @FXML
    public Button backButton;

    private void updateListView(List<Transaction> listTransaction) {
        transactionsList.clear();
        transactionsList.addAll(listTransaction);
        transactionsListView.setItems(transactionsList);
    }

    private void setDialogAddTransaction(MenuItem menuItem, String name) {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(350);
        dialog.getDialogPane().setMinHeight(250);
        GridPane gridPane = new GridPane();

        Label date = new Label("Datum: ");
        Label description = new Label("Beschreibung: ");
        Label amount = new Label("Betrag: ");
        Label incomingInterest_sender = new Label();
        Label outgoingInterest_recipient = new Label();

        TextField dateText = new TextField();
        TextField descriptionText = new TextField();
        TextField amountText = new TextField();
        TextField incomingInterest_senderText = new TextField();
        TextField outgoingInterest_recipientText = new TextField();

        gridPane.add(date, 1, 1);
        gridPane.add(dateText, 2, 1);
        gridPane.add(description, 1, 2);
        gridPane.add(descriptionText, 2, 2);
        gridPane.add(amount, 1, 3);
        gridPane.add(amountText, 2, 3);
        gridPane.add(incomingInterest_sender, 1, 4);
        gridPane.add(incomingInterest_senderText, 2, 4);
        gridPane.add(outgoingInterest_recipient, 1, 5);
        gridPane.add(outgoingInterest_recipientText, 2, 5);

        ButtonType okButton = new ButtonType("ADD", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().setContent(gridPane);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        Alert invalid = new Alert(Alert.AlertType.ERROR);
        dialog.show();
        if (Objects.equals(menuItem.getId(), "Payment")) {
            dialog.setTitle("Payment hinzufügen");
            dialog.setHeaderText("Füge neues Payment zu [" + name + "]");

            incomingInterest_sender.setText("IncomingInterest: ");
            outgoingInterest_recipient.setText("OutgoingInterest: ");

            dialog.setResultConverter(buttonType ->  {
                if (buttonType == okButton) {
                    if (Objects.equals(dateText.getText(), "") ||
                            Objects.equals(descriptionText.getText(),"") ||
                            Objects.equals(amountText.getText(), "") ||
                            Objects.equals(incomingInterest_senderText.getText(), "") ||
                            Objects.equals(outgoingInterest_recipientText.getText(), ""))
                    {
                        invalid.setContentText("Bitte gültige Eingabe eingeben");
                        Optional<ButtonType> result = invalid.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            text.setText("Nicht hinzugefügt");
                        }
                    } else {
                        Payment payment = null;
                        try {
                            payment = new Payment(dateText.getText(),
                                    Double.parseDouble(amountText.getText()),
                                    descriptionText.getText(),
                                    Double.parseDouble(incomingInterest_senderText.getText()),
                                    Double.parseDouble(outgoingInterest_recipientText.getText()));
                        } catch (AttributeException e) {
                            throw new RuntimeException(e);
                        } catch (IncomingInterestException e) {
                            throw new RuntimeException(e);
                        } catch (OutgoingInterestException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            bank.addTransaction(name, payment);
                            text.setText("Payment hinzugefügt");
                        } catch (TransactionAlreadyExistException e) {
                            invalid.setContentText("Duplikat payment");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Bereits in dem Account");
                            }
                            System.out.println(e.getMessage());
                        } catch (AccountDoesNotExistException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateListView(bank.getTransactions(name));
                        accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
                    }

                }
                return null;
            });
        }  else {
            incomingInterest_sender.setText("Sender: ");
            outgoingInterest_recipient.setText("Empfänger: ");
            if (Objects.equals(menuItem.getId(), "incoming")) {

                dialog.setTitle("Füge IncomingTransfer hinzu");
                dialog.setHeaderText("Füge IncomingTransfer zu [" + name + "]");

                dialog.setResultConverter(buttonType -> {
                    if (buttonType == okButton) {
                        if (Objects.equals(dateText.getText(), "") ||
                                Objects.equals(descriptionText.getText(),"") ||
                                Objects.equals(amountText.getText(), "") ||
                                Objects.equals(incomingInterest_senderText.getText(), "") ||
                                Objects.equals(outgoingInterest_recipientText.getText(), ""))
                        {
                            invalid.setContentText("Bitte gültige Eingabe eingeben");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Nicht hinzugefügtt");
                            }
                        } else {
                            IncomingTransfer incomingTransfer = null;
                            try {
                                incomingTransfer = new IncomingTransfer(dateText.getText(),
                                        Double.parseDouble(amountText.getText()),
                                        descriptionText.getText(),
                                        incomingInterest_senderText.getText(),
                                        outgoingInterest_recipientText.getText());
                            } catch (AttributeException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                bank.addTransaction(name, incomingTransfer);
                                text.setText("IncomingTransfer hinzugefügt");
                            } catch (TransactionAlreadyExistException e) {
                                invalid.setContentText("Duplikat IncomingTransfer!");
                                Optional<ButtonType> result = invalid.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    text.setText("Bereits in nutzung");
                                }
                                System.out.println(e.getMessage());
                            } catch (AccountDoesNotExistException e) {
                                System.out.println(e.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            updateListView(bank.getTransactions(name));
                            accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");

                        }

                    }
                    return null;
                });
            } else  {
                dialog.setTitle("Add new outgoing transfer");
                dialog.setHeaderText("Add a new outgoing transfer to account [" + name + "]");

                dialog.setResultConverter(buttonType -> {
                    if (buttonType == okButton) {
                        if (Objects.equals(dateText.getText(), "") ||
                                Objects.equals(descriptionText.getText(),"") ||
                                Objects.equals(amountText.getText(), "") ||
                                Objects.equals(incomingInterest_senderText.getText(), "") ||
                                Objects.equals(outgoingInterest_recipientText.getText(), ""))
                        {
                            invalid.setContentText("Bitte gültige Eingabe");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Keine neue OutgoingTraansfer");
                            }
                        } else {
                            OutgoingTransfer outgoingTransfer = null;
                            try {
                                outgoingTransfer = new OutgoingTransfer(dateText.getText(),
                                        Double.parseDouble(amountText.getText()),
                                        descriptionText.getText(),
                                        incomingInterest_senderText.getText(),
                                        outgoingInterest_recipientText.getText());
                            } catch (AttributeException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                bank.addTransaction(name, outgoingTransfer);
                                text.setText("OutgoingTransfer hinzugefügt");
                            } catch (TransactionAlreadyExistException e) {
                                invalid.setContentText("Duplikat!");
                                Optional<ButtonType> result = invalid.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    text.setText("bereits in der Bank");
                                }
                                System.out.println(e.getMessage());
                            } catch (AccountDoesNotExistException e) {
                                System.out.println(e.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            updateListView(bank.getTransactions(name));
                            accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
                        }
                    }
                    return null;
                });
            }
        }
    }

    public void setupData(PrivateBank privateBank, String name) {
        bank = privateBank;
        accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
        updateListView(bank.getTransactions(name));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteTransaction = new MenuItem("Lösche Transaktion");

        contextMenu.getItems().addAll(deleteTransaction);
        transactionsListView.setContextMenu(contextMenu);

        AtomicReference<Transaction> selectedTransaction = new AtomicReference<>();

        transactionsListView.setOnMouseClicked(mouseEvent -> {
            selectedTransaction.set(transactionsListView.getSelectionModel().getSelectedItem());
            System.out.println("[" + selectedTransaction.toString().replace("\n", "]"));
        });
        deleteTransaction.setOnAction(event -> {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Löschung Transaktion");
            confirmation.setContentText("sicher löschen?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    bank.removeTransaction(name, selectedTransaction.get());
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                System.out.println("[" + selectedTransaction.toString().replace("\n", "]") + " gelöscht");
                text.setText(selectedTransaction.toString().replace("\n", "]") + " gelöscht");
                updateListView(bank.getTransactions(name));
                accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
            }
        });


        allTransaction.setOnAction(event -> updateListView(bank.getTransactions(name)));
        ascending.setOnAction(event -> updateListView(bank.getTransactionsSorted(name, true)));
        descending.setOnAction(event -> updateListView(bank.getTransactionsSorted(name, false)));
        positive.setOnAction(event -> updateListView(bank.getTransactionsByType(name, true)));
        negative.setOnAction(event -> updateListView(bank.getTransactionsByType(name, false)));

        payment.setOnAction(event -> setDialogAddTransaction(payment, name));
        incoming.setOnAction(event -> setDialogAddTransaction(incoming, name));
        outgoing.setOnAction(event -> setDialogAddTransaction(outgoing, name));



    }

    @FXML
    private void initialize() {

        backButton.setOnMouseClicked(mouseEvent -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainview.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setTitle("MainView");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}