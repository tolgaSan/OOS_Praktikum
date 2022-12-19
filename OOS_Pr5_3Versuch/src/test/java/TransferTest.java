import bank.*;
import bank.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

public class TransferTest {

    Transfer transfer = new Transfer("22.22", 30, "Transfer");
    Transfer transfer2 = new Transfer("22.22", 30, "Transfer", "Tolga", "Faek");

    Transfer copyTransfer = new Transfer(transfer);
    Transfer copyTransfer2 = new Transfer(transfer2);

    public TransferTest() throws AttributeException {
    }

    @BeforeEach
    public void initMehtode() throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {

    }

    @Test
    @DisplayName("Konstruktor soll richtig konstruieren")
    public void konstruktorTest(){
        assertNotNull(transfer);
        assertNotNull(transfer2);

        assertEquals("22.22", transfer.getDate());
        assertEquals(30, transfer.getAmount());
        assertEquals("Transfer", transfer.getDescription());

        assertEquals("Tolga", transfer2.getSender());
        assertEquals("Faek", transfer2.getRecipient());

    }

    @Test
    @DisplayName("Copykonstruktor soll richtig kopieren und konstruieren")
    public void copyKonstruktorTest(){
        assertNotNull(copyTransfer);
        assertNotNull(copyTransfer2);

        assertEquals("22.22", copyTransfer.getDate());
        assertEquals(30, copyTransfer.getAmount());
        assertEquals("Transfer", copyTransfer.getDescription());

        assertEquals("Tolga", copyTransfer2.getSender());
        assertEquals("Faek", copyTransfer2.getRecipient());

        copyTransfer.setSender("Tolg");
        assertNotEquals(transfer.getSender(), copyTransfer.getSender());
    }

    @Test
    @DisplayName("calculate-Methode soll richtig berechnen")
    public void calculateTest() throws AttributeException {
        assertEquals(30, transfer.calculate()); //Kein Interest -> normalerweise von der Klasse PrivateBank übernommen

        Transfer incomingTransfer = new IncomingTransfer("30.11.", 300, "transfer", "Tolga", "Faek");
        Transfer outgoingTransfer = new OutgoingTransfer("30.11.", 50, "transfer", "Tolga", "Faek");

        assertEquals(300, incomingTransfer.calculate());
        assertEquals(-50, outgoingTransfer.calculate());
    }

    @Test
    @DisplayName("Equals soll richtig vergleichen")
    public void equalsTest(){

        assertEquals(true, transfer.equals(copyTransfer));
        assertEquals(true, transfer2.equals(copyTransfer2));

    }

    @Test
    @DisplayName("toString soll String richtig ausgeben")
    public void toStringTest() throws AttributeException {

        assertEquals("22.22 30.0 Transfer null null", transfer.toString());
        assertEquals("22.22 30.0 Transfer Tolga Faek", transfer2.toString());

    }

    @Test
    @DisplayName("Setter und Getter müssen richtig funktionieren")
    public void setterUndGetterTest() throws AttributeException {
        transfer2.setDate("10.10");
        transfer2.setAmount(100);
        transfer2.setDescription("beschreibung");
        transfer2.setRecipient("neuerTolga");
        transfer2.setSender("neuerFaek");

        assertNotEquals(copyTransfer2.getDate(), transfer2.getDate());
        assertNotEquals(copyTransfer2.getAmount(), transfer2.getAmount());
        assertNotEquals(copyTransfer2.getDescription(), transfer2.getDescription());
        assertNotEquals(copyTransfer2.getSender(), transfer2.getSender());
        assertNotEquals(copyTransfer2.getRecipient(), transfer2.getRecipient());
    }

}
