import bank.*;
import bank.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.IOException;

public class PaymentTest {

    Payment payment = new Payment("22.22", 30, "Zahlung");
    Payment payment2 = new Payment("22.22", 30, "Zahlung",0.01,0.01);
    Payment copyPayment = new Payment(payment);
    Payment copyPayment2 = new Payment(payment2);
    Payment paymentOutgoingInterest = new Payment("22.22", -30, "Zahlung",0.01,0.01);
    Payment paymentZero = new Payment("22",0,"zero");
    public PaymentTest() throws OutgoingInterestException, IncomingInterestException, AttributeException {
    }

    @BeforeEach
    public void initMehtode() throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {

    }

    @Test
    @DisplayName("Konstruktor soll funktionieren")
    public void konstruktor() throws OutgoingInterestException, IncomingInterestException, AttributeException {

        assertNotNull(payment);
        assertEquals(payment.getDate(), "22.22");
        assertEquals(payment.getAmount(), 30);
        assertEquals(payment.getDescription(), "Zahlung");

        assertNotNull(payment2);
        assertEquals(payment2.getDate(), "22.22");
        assertEquals(payment2.getAmount(), 30);
        assertEquals(payment2.getDescription(), "Zahlung");
        assertEquals(payment2.getIncomingInterest(), 0.01);
        assertEquals(payment2.getOutgoingInterest(), 0.01);
    }

    @Test
    @DisplayName("Copykonstruktor soll funktionieren")
    public void copyKonstruktor() throws OutgoingInterestException, IncomingInterestException, AttributeException {

        assertNotNull(copyPayment);
        assertEquals(copyPayment.getDate(), "22.22");
        assertEquals(copyPayment.getAmount(), 30);
        assertEquals(copyPayment.getDescription(), "Zahlung");

        assertNotNull(copyPayment2);
        assertEquals(copyPayment2.getDate(), "22.22");
        assertEquals(copyPayment2.getAmount(), 30);
        assertEquals(copyPayment2.getDescription(), "Zahlung");
        assertEquals(copyPayment2.getIncomingInterest(), 0.01);
        assertEquals(copyPayment2.getOutgoingInterest(), 0.01);

        payment.setAmount(50);
        assertNotEquals(payment.getAmount(), copyPayment.getAmount());
    }

    @Test
    @DisplayName("Calculate-Methode soll richtig ausgeführt werden")
    public void calculateTest() {


        assertEquals(30, payment.calculate()); //Kein Interest -> normalerweise von der Klasse PrivateBank übernommen
        assertEquals(29.7, payment2.calculate()); // Test im Falle eines ingoingInterest
        assertEquals(29.7, copyPayment2.calculate());
        assertEquals(-30.3, paymentOutgoingInterest.calculate()); // Test im Falle eines outgoingInterest
        assertEquals(0.0, paymentZero.calculate());

    }



    @Test
    @DisplayName("Equals soll richtig vergleichen")
    public void equalsTest(){

        assertEquals(true, payment.equals(copyPayment));
        assertEquals(true, payment2.equals(copyPayment2));
        assertEquals(false, payment.equals(paymentZero));

    }

    @Tests
    @DisplayName("toString soll String richtig ausgeben")
    public void toStringTest(){

        assertEquals("22.22 30.0 Zahlung 0.0 0.0", payment.toString());
        assertEquals("22.22 29.7 Zahlung 0.01 0.01", payment2.toString());

    }


}
