import bank.*;
import bank.exceptions.*;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

//Klasse Main, um Systemausgabe, testings o.ä. durchzuführen
public class Main
{
    public static void main(String[] args) throws AttributeException, OutgoingInterestException, IncomingInterestException, TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, IOException {

        /*
        Payment pay1 = new Payment("01.01.", 200, "Zahlung", 0, 0);
        Payment pay2 = new Payment("01.01.", 6000, "Lohn");
        //Variante 1
        OutgoingTransfer out2 = new OutgoingTransfer("02.01.", 200, "Ich sende das raus");
        Transfer outg1 = new Transfer(out2);
        //Variante 2
        Transfer tran1 = new Transfer("02.01.", 20, "Transfer", "Tolga", "Faik");
        Transfer tran2 = new Transfer("03.01.", 20, "Transfer", "Faik", "Tolga");
        Transfer tran3 = new Transfer("03.01.", 20, "Transfer", "Tolga", "");


        PrivateBank bank = new PrivateBank("Unibank", 0.01, 0.01, "Daten");
        PrivateBankAlt altBank = new PrivateBankAlt("Sparkasse", 0.01, 0.02);

        try{
           altBank.createAccount("Tolga", List.of());
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.createAccount("Faik", List.of());
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.addTransaction("Tolga", pay1);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.addTransaction("Tolga", tran1);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.addTransaction("Faik", tran2);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.createAccount("AltAccount2", List.of());
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            altBank.addTransaction("AltAccount2", tran3);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }

        System.out.println(altBank.toString());
        System.out.println(altBank.getAccountBalance("Tolga"));
        System.out.println(altBank.getAccountBalance("Faik"));

        try{
            bank.createAccount("LeitersKonto");
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            bank.addTransaction("LeitersKonto", outg1);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            bank.createAccount("Mitarbeiter1", List.of(
                    new Payment("03.11.", 3000, "Lohn", 0.01, 0.01),
                    new OutgoingTransfer("04.11.", 50, "Wette", "Mitarbeiter1", "Leiterskonto"),
                    new OutgoingTransfer("04.11.", 30, "Wette", "Leiterskonto", "Leiterskonto"),
                    new OutgoingTransfer("04.11.", 60, "Wette", "Leiterskonto", "Leiterskonto"),
                    new OutgoingTransfer("04.11.", 80, "Wette", "Leiterskonto", "Leiterskonto"),
                    new Payment("10.11.", 1200, "Bonus"),
                    new IncomingTransfer("30.11.", 300, "Wette", "Leiterskonto", "Mitarbeiter1")
                    ));
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            bank.addTransaction("LeitersKonto", pay2);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bank.toString());
        System.out.println(bank.getName());
        System.out.println(bank.getAccountBalance("LeitersKonto"));
        System.out.println(bank.getAccountBalance("Mitarbeiter1"));

        try{
            bank.removeTransaction("Mitarbeiter1", new OutgoingTransfer("04.11.", 50, "Wette", "Mitarbeiter1", "Leiterskonto"));
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bank.toString());
        System.out.println(bank.getTransactionsSorted("Mitarbeiter1", true));
        System.out.println(bank.getTransactionsSorted("Mitarbeiter1", false));

        //getTransactionByType testen = nur positive oder negative Beträge ausgeben

        Payment payPos1 = new Payment("12.12.", 2000, "ps");
        Payment payPos2 = new Payment("12.12.", 3000, "ps");
        Payment payNeg1 = new Payment("12.12.", -2000, "ps");
        Payment payNeg2 = new Payment("12.12.", -5200, "ps");

        PrivateBank type = new PrivateBank("ByTyp", 0, 0, "Daten");



        PrivateBank equtest1 = new PrivateBank("test1", 0.0, 0.0, "Daten");

        PrivateBank equtest3 = new PrivateBank(equtest1);
        System.out.println(equtest1.equals(equtest1));
        System.out.println(equtest1.equals(equtest3));
        PrivateBank equtest2 = new PrivateBank("test1", 0.01, 0.0, "Daten");
        System.out.println(equtest3.equals(equtest2));

        */

      //  PrivateBank bank = new PrivateBank("Unibank", 0.01, 0.01, "Daten");
       // PrivateBank bank2 = new PrivateBank("Unibank2", 0.01, 0.01, "Oke");

        /*bank.createAccount("accountTest", List.of(
                new Payment("03.11.", 3000, "Lohn", 0.01, 0.01),
                new IncomingTransfer("03.11.", 3000, "Lohn", "Toggo", "faek"),
                new OutgoingTransfer("03.11.", 3000, "Lohn", "Toggo", "Ausland")
        ));

        bank2.createAccount("accountTest2", List.of(
                new Payment("03.11.", 3000, "Lohn", 0.01, 0.01),
                new IncomingTransfer("03.11.", 3000, "Lohn", "Toggo", "faek"),
                new OutgoingTransfer("03.11.", 3000, "Lohn", "Toggo", "Ausland")
        ));*/

        //PrivateBank bankCopy = new PrivateBank(bank);




        //System.out.println(bank);

        //bank.toString();

    }
}











