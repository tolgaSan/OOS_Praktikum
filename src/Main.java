import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.Transfer;
import bank.exceptions.*;

import java.sql.SQLOutput;
import java.util.List;

//Klasse Main, um Systemausgabe, testings o.ä. durchzuführen
public class Main
{
    public static void main(String[] args) {

        /*ransfer tra = new Transfer("02.05.", 30, "Transfer", "sender", "empfänger");
        Transfer tracopy = new Transfer(tra);
        System.out.println(tra.toString());

        System.out.println(tra.equals(tracopy));

        tra.setAmount(500);

        System.out.println(tra.equals(tracopy));

        Transfer trans1 = new Transfer("05.05.", 50, "Nescjreobuing", "sender", "emoffmsdf");
        System.out.println(trans1.toString());

        Payment calcu0 = new Payment("08.05", -500, "Auszahlung", 0.05, 0.1);
        Payment calcu0copy = new Payment(calcu0);
        System.out.println(calcu0.toString());

        Payment calcu1 = new Payment("09.05", 100, "Einzahlung", 0.05, 0.1);
        System.out.println(calcu1.toString());

        Payment calcu2 = new Payment("10.05", 0, "nullbetragstest", 0.05, 0.1);
        System.out.println(calcu2.toString());

        System.out.println(calcu0.equals(calcu0copy));
        calcu0.setAmount(30);
        calcu0.setIncomingInterest(0.9);
        System.out.println(calcu0);
        System.out.println(calcu0.equals(calcu0copy));

         */

      /*  PrivateBank privateBank = new PrivateBank("Bank", 0.3, 0.2);

            try
            {
                PrivateBank privateBank1 = new PrivateBank("Bank", 0.3, 0.2);
                privateBank.createAccount("abc");
            }
            catch (AccountAlreadyExistsException e)
            {

            }

       */
/*
        PrivateBank p = new PrivateBank("abc", 1,1);

        Transaction tran = new Transfer("nameTran", 1000, "beschreibung");
        Transaction tran2 = new Transfer("nameTran2", 3000, "beschreibung2");
        Transaction tran3 = new Transfer("nameTran3", 1600, "beschreibung3");
        Transaction tran4 = new Transfer("nameTran4", 2100, "beschreibung4");
        Transaction tran5 = new Transfer("nameTran5", 1500, "beschreibung5");

        PrivateBank accountOhneTranktionen = new PrivateBank("accountOhneTr", 0, 0);
        try{
            accountOhneTranktionen.createAccount("accountTest");
        }catch(AccountAlreadyExistsException e){
            System.out.println(e);
        }

        try
        {
            p.createAccount("abc", List.of(new Transfer("2002.12.2",1000,"ddfdsf")));
        }
        catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException e)
        {
            System.out.println(e);
        }

        try {
            p.removeTransaction("abc", new Transfer("2002.12.2",1000,"ddfdsf"));
        }
        catch (AccountDoesNotExistException | TransactionDoesNotExistException e)
        {
            System.out.println(e);
        }
        try{
            p.createAccount("asd");
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        try{
            accountOhneTranktionen.addTransaction("accountTest", tran);
        }catch (TransactionAttributeException | TransactionAlreadyExistException | AccountDoesNotExistException e){
            System.out.println(e);
        }

        PrivateBank accountMitTrans = new PrivateBank(" AccountTest4 ", 0.5, 0.5);
        try{
            accountMitTrans.createAccount(" AccountTest5 ", List.of());
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }

        try {
            accountMitTrans.addTransaction(" AccountTest5 ", tran);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try {
            accountMitTrans.addTransaction(" AccountTest5 ", tran2);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try {
            accountMitTrans.addTransaction(" AccountTest5 ", tran3);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try {
            accountMitTrans.addTransaction(" AccountTest5 ", tran4);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try {
            accountMitTrans.addTransaction(" AccountTest5 ", tran5);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }

        System.out.println(accountMitTrans);
        System.out.println(accountMitTrans.getAccountBalance(" AccountTest5 "));
        System.out.println(accountMitTrans.getTransactions(" AccountTest5 "));
        try{
            accountMitTrans.removeTransaction(" AccountTest5 ", tran);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionDoesNotExistException e) {
            throw new RuntimeException(e);
        }

        System.out.println(accountMitTrans);
        System.out.println(accountMitTrans.getAccountBalance(" AccountTest5 "));
        System.out.println(accountMitTrans.getTransactions(" AccountTest5 "));
        System.out.println(accountMitTrans.getTransactionsSorted(" AccountTest5 ", true));
        System.out.println(accountMitTrans.getTransactionsSorted(" AccountTest5 ", false));
        System.out.println(accountMitTrans.getTransactionsByType(" AccountTest5 ", true));
*/

        Payment payT1 = new Payment("Pay1", 1000, "beschreibung" );
        Payment payT2 = new Payment("Pay2", 3000, "beschreibung2");
        Payment payT3 = new Payment("Pay3", -1600, "beschreibung3");
        Payment payT4 = new Payment("Pay4", 2100, "beschreibung4");
        Payment payT5 = new Payment("Pay5", -1500, "beschreibung5");

        PrivateBank payAcc = new PrivateBank("PayAccount", 0.05, 0.01);

        try{
            payAcc.createAccount("Pay Account ", List.of());
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(payAcc);
        try{
            payAcc.addTransaction("Pay Account ", payT1);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            payAcc.addTransaction("Pay Account ", payT2);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            payAcc.addTransaction("Pay Account ", payT3);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            payAcc.addTransaction("Pay Account ", payT4);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        try{
            payAcc.addTransaction("Pay Account ", payT5);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(payAcc);
        System.out.println(payAcc.getAccountBalance("Pay Account "));
        System.out.println(payAcc.getTransactions("Pay Account "));
        System.out.println(payAcc.getTransactionsSorted("Pay Account ", true));
        System.out.println(payAcc.getTransactionsSorted("Pay Account ", false));
        System.out.println(payAcc.getTransactionsByType("Pay Account ", true));
        System.out.println(payAcc.getTransactionsByType("Pay Account ", false));

        System.out.println(payAcc.containsTransaction("Pay Account ", payT2));



    }
}











