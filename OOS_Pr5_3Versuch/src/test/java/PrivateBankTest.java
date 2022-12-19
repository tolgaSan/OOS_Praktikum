import bank.*;
import bank.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PrivateBankTest {
    private PrivateBank bank;
    private PrivateBank copyBank;


    public PrivateBankTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, IOException {
    }

    @BeforeEach
    public void initMehtode() throws Exception {
        assertDoesNotThrow(()-> bank = new PrivateBank("UniBank", 0.01, 0.01, "TestBank"));
        assertDoesNotThrow(()-> copyBank = new PrivateBank(bank));
    }

    @AfterEach
    public void initMehtodeDelete() throws Exception {

        File file = new File("TestBank/createAccountTest.json");
        File file2 = new File("TestBank/createTestAccount.json");
        File file3 = new File("TestBank/createTestAccount2.json");
        File file4 = new File("TestBanke/CreateTestAccountTest.json");
        File file5 = new File("TestBank/CreateTestAccountMitTransaktionen.json");

        file.delete();
        file2.delete();
        file3.delete();

        file5.delete();
        file4.delete();
    }

    @Test
    @DisplayName("Konstruktor soll richtig konstruieren")
    public void konstruktorTest(){
        assertNotNull(bank);
        assertEquals("UniBank", bank.getName());
        assertEquals(0.01, bank.getIncomingInterest());
        assertEquals(0.01, bank.getOutgoingInterest());
        assertEquals("TestBank", bank.getDirectoryName());
    }

    @Test
    @DisplayName("CopyKonstruktor soll richtig konstruieren")
    public void copyKonstruktorTest(){
        assertNotNull(copyBank);
        assertEquals("UniBank", copyBank.getName());
        assertEquals(0.01, copyBank.getIncomingInterest());
        assertEquals(0.01, copyBank.getOutgoingInterest());
        assertEquals("TestBank", copyBank.getDirectoryName());
    }

    @Test
    @DisplayName("createAccountTest soll Account richtig erstellen")
    public void createAccountTest(){


        assertDoesNotThrow(() -> bank.createAccount("createAccountTest"));
        assertThrows(AccountAlreadyExistsException.class, () -> bank.createAccount("createAccountTest"));
        assertNotNull(bank.getName());
        assertNotNull(bank.getDirectoryName());



    }

    @Test
    @DisplayName("createAccountTest soll Account und Transaktionen richtig erstellen")
    public void createAccountTestMitTransaktionenTest() {

        /* bank.createAccount("createAccountTransactionTest");
        bank.addTransaction("createAccountTransactionTest",  new Payment("22.22", 1000, "Zahlung", 0.2, 0.2));

        assertNotNull(bank);
        */

        assertDoesNotThrow(() -> bank.createAccount("CreateTestAccountMitTransaktionen", List.of(
                new Payment("22.11.", 100, "Zahlung", 0.01, 0.02)
        )));
        assertThrows(AccountAlreadyExistsException.class, () -> bank.createAccount("CreateTestAccountMitTransaktionen"));
        //assertThrows(TransactionAlreadyExistException.class, () -> bank.createAccount("CreateTestAccountMitTransaktionen2", List.of(
        //        new Payment("22.11.", 100, "Zahlung", 0.01, 0.02)
        //)));



    }

    @Test
    @DisplayName("addTransaction soll Transaktion korrekt hinzufügen")
    public void addTransactionTestTest(){

        assertDoesNotThrow(() -> bank.createAccount("CreateTestAccount"));
        assertDoesNotThrow(() -> bank.addTransaction("CreateTestAccount", new Payment("21.11.", 100, "beschr.", 0.01, 0.01)));


    }

    @Test
    @DisplayName("removeTransaction soll Transaktion korrekt entfernen")
    public void removeTransactionTest() throws AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException, OutgoingInterestException, IncomingInterestException, AccountDoesNotExistException, TransactionDoesNotExistException {

        bank.createAccount("CreateTestAccount", List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01)
        ));
        bank.removeTransaction("CreateTestAccount", new Payment("21.11.", 100, "beschr.", 0.01, 0.01));



    }

    @Test
    @DisplayName("getAccountBalanceTest soll Guthaben korrekt ausgeben")
    public void getAccountBalanceTestTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        bank.createAccount("CreateTestAccount", List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01)
        ));
        assertEquals(99, bank.getAccountBalance("CreateTestAccount"));


    }

    @Test
    @DisplayName("containsTransaction soll Transaktion enthalten")
    public void containsTransactionTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        bank.createAccount("CreateTestAccount", List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01),
                new OutgoingTransfer("21.10", 500, "beschr.")
        ));
        assertTrue(bank.containsTransaction("CreateTestAccount", new OutgoingTransfer("21.10", 500, "beschr.")));

    }

    @Test
    @DisplayName("getTransaction soll Transaktion ausgeben")
    public void getTransactionTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, TransactionAttributeException {

        bank.createAccount("CreateTestAccount", List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01),
                new OutgoingTransfer("21.10", 500, "beschr.")
        ));
        List<Transaction> transactions = List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01),
                new OutgoingTransfer("21.10", 500, "beschr.")
        );
        assertEquals(transactions, bank.getTransactions("CreateTestAccount"));

    }

    @Test
    @DisplayName("getTransactionsSorted soll Transactionen sortieren")
    public void getTransactionsSortedTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        bank.createAccount("CreateTestAccount", List.of(
                new IncomingTransfer("21.11.", 100, "beschr."),
                new IncomingTransfer("21.10", 500, "beschr."),
                new IncomingTransfer("21.11.", 1000, "beschr."),
                new IncomingTransfer("21.10", 300, "beschr."),
                new IncomingTransfer("21.11.", 400, "beschr."),
                new IncomingTransfer("21.10", 900, "beschr.")
        ));
        List<Transaction> transactions = List.of(
                new IncomingTransfer("21.11.", 100, "beschr."),
                new IncomingTransfer("21.10", 300, "beschr."),
                new IncomingTransfer("21.11.", 400, "beschr."),
                new IncomingTransfer("21.10", 500, "beschr."),
                new IncomingTransfer("21.10", 900, "beschr."),
                new IncomingTransfer("21.11.", 1000, "beschr.")

        );
        assertEquals(transactions, bank.getTransactionsSorted("CreateTestAccount", true));

        bank.createAccount("CreateTestAccount2", List.of(
                new IncomingTransfer("21.110.", 1000, "beschr."),
                new IncomingTransfer("21.100", 5000, "beschr."),
                new IncomingTransfer("21.110.", 10000, "beschr."),
                new IncomingTransfer("21.100", 3000, "beschr."),
                new IncomingTransfer("21.110.", 4000, "beschr."),
                new IncomingTransfer("21.100", 9000, "beschr.")
        ));
        List<Transaction> transactions2 = List.of(
                new IncomingTransfer("21.110.", 10000, "beschr."),
                new IncomingTransfer("21.100", 9000, "beschr."),
                new IncomingTransfer("21.100", 5000, "beschr."),
                new IncomingTransfer("21.110.", 4000, "beschr."),
                new IncomingTransfer("21.100", 3000, "beschr."),
                new IncomingTransfer("21.110.", 1000, "beschr.")
        );
        assertEquals(transactions2, bank.getTransactionsSorted("CreateTestAccount2", false));


    }

    @Test
    @DisplayName("getTransactionsByType soll Transaction nach Typ zurückgeben")
    public void getTransactionsByTypeTest() throws AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException, OutgoingInterestException, IncomingInterestException, AccountDoesNotExistException {
        PrivateBank bankTest = new PrivateBank("UniBanke", 0.00, 0.00, "TestBanke");
        bankTest.createAccount("CreateTestAccountTest", List.of(
                new Payment("21.11.", 100, "beschr.", 0.00, 0.00),
                new Payment("21.10", 500, "beschr.", 0.00, 0.00),
                new Payment("21.11.", -1000, "beschr.", 0.00, 0.00),
                new Payment("21.10", 300, "beschr.", 0.00, 0.00),
                new Payment("21.11.", -400, "beschr.", 0.00, 0.00),
                new Payment("21.10", 900, "beschr.", 0.00, 0.00)
        ));
        List<Transaction> transactionsPositiv = List.of(
                new Payment("21.11.", 100.0, "beschr.", 0.00, 0.00),
                new Payment("21.10", 500.0, "beschr.", 0.00, 0.00),
                new Payment("21.10", 300.0, "beschr.", 0.00, 0.00),
                new Payment("21.10", 900, "beschr.", 0.00, 0.00)
        );
        List<Transaction> transactionsNegativ = List.of(
                new Payment("21.11.", -1000, "beschr.", 0.00, 0.00),
                new Payment("21.11.", -400, "beschr.", 0.00, 0.00)
        );
        assertEquals(transactionsPositiv, bankTest.getTransactionsByType("CreateTestAccountTest", true));
        assertEquals(transactionsNegativ, bankTest.getTransactionsByType("CreateTestAccountTest", false));


    }

    @Test
    @DisplayName("toString soll korrekt ausgeben")
    public void toStringTest(){
        assertEquals("Name: UniBank Gebühr Einzahlung: 0.01 Gebühr Auszahlung: 0.01 Account: {}Directory: TestBank", bank.toString());
    }

    @Test
    @DisplayName("equals soll korrekt vergleichen")
    public void equalsTest() throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {
        PrivateBank bankCopy = new PrivateBank(bank);
        assertTrue(bankCopy.equals(bank));
    }

    @Test
    @DisplayName("deleteAccount soll korrekt gelöscht werden")
    public void deleteAccountTest() throws OutgoingInterestException, IncomingInterestException, AttributeException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        bank.createAccount("Tolga", List.of(
                new Payment("21.11.", 100, "beschr.", 0.01, 0.01),
                new OutgoingTransfer("21.10", 500, "beschr.")
        ));
        assertDoesNotThrow(() -> bank.deleteAccount("Tolga"));
    }
    @Test
    @DisplayName("getAllAccountsTest soll alle accounts ausgeben")
    public void getAllAccountsTest() throws AccountAlreadyExistsException, IOException {
        bank.createAccount("Tolga");
        bank.createAccount("Faik");
        bank.createAccount("Geheim");

        List<String> accounts = new ArrayList<>();
        accounts.add("Geheim");
        accounts.add("Tolga");
        accounts.add("Faik");

        assertEquals(accounts, bank.getAllAccounts());

        assertDoesNotThrow(() -> bank.deleteAccount("Tolga"));
        assertDoesNotThrow(() -> bank.deleteAccount("Faik"));
        assertDoesNotThrow(() -> bank.deleteAccount("Geheim"));
        assertThrows(AccountDoesNotExistException.class, () -> bank.deleteAccount("Tolga"));
        assertThrows(AccountDoesNotExistException.class, () -> bank.deleteAccount("Faik"));
        assertThrows(AccountDoesNotExistException.class, () -> bank.deleteAccount("Geheim"));

        /*File file1 = new File("TestBank/Faik.json");
        File file2 = new File("TestBank/Geheim.json");
        File file3 = new File("TestBank/Tolga.json");

        file1.delete();
        file2.delete();
        file3.delete();*/
    }



}
