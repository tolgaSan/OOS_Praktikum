package bank;

import bank.exceptions.*;

import java.util.*;
import java.util.HashMap;

/**
 * Die Klasse erzeugt eine eigene Bank mit zusätzlicher Liste für Transaktionen.
 * Zudem hat die Klasse einige Methoden um einige Funktionen zu realisieren
 */
public class PrivateBank implements Bank{

    /**
     * name beschreibt die Name der Bank
     */
    private String name;
    /**
     * incomingInterest beschreibt die Gebühr für Einzahlung
     */
    private double incomingInterest;
    /**
     * OutgoingInterest beschreibt die Gebühr für Auszahlung
     */
    private double outgoingInterest;
    /**
     * Die HashMap besteht aus einem String Key mit einer Liste aus Transaktionen. Transaction ist an String gekoppelt
     */
    public Map<String, List<Transaction>> accountsToTransaction = new HashMap<>();

    /**
     * Konstruktor, um die Bank zu erzeugen
     * @param name beschreibt den Namen der Bank
     * @param incomingInterest beschreibt die Gebühr für Einzahlung
     * @param outgoingInterest beschreibt die Gebühr für Auszahlung
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copykonstruktor, um die Bank zu erzeugen
     * @param privateBank "kopiere" den Konstruktor
     */
    public PrivateBank(PrivateBank privateBank) {
        this(privateBank.name, privateBank.incomingInterest, privateBank.outgoingInterest );
        this.accountsToTransaction = privateBank.accountsToTransaction;

    }

    /**
     *
     * @return name der Bank
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name setzen
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Gebühr für Einazhlung
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     *
     * @param incomingInterest Gebühr für Einazhlung setzen
     */
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    /**
     *
     * @return Gebühr für Auszahlung
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     *
     * @param outgoingInterest Gebühr für Auszahlung setzen
     */
    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Methode, um ein Account zu erzeugen
     * @param account the account to be added
     * @throws AccountAlreadyExistsException Gibt eine Exception, wenn Key(account) bereits existiert
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException
    {
        if (accountsToTransaction.containsKey(account))
        {
            throw new AccountAlreadyExistsException("Account existiert schon");
        }
        else
        {
            accountsToTransaction.put(account, List.of());
            System.out.println("Account erstellt!");
        }
    }

    /**
     * Eine Methode um eine Klasse mit Liste zu erzeugen
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException throwt exception, wenn Account bereits existiert
     * @throws TransactionAlreadyExistException throwt exception, wenn Transaction bereits existiert
     * @throws TransactionAttributeException throwt exception, wenn Attribut nicht stimmt (Amount fehlerhaft)
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, AccountDoesNotExistException, OutgoingInterestException, IncomingInterestException {
        if (accountsToTransaction.containsKey(account))
        {
            throw new AccountAlreadyExistsException("Account existiert schon");
        }
        else if(accountsToTransaction.containsKey(account) && accountsToTransaction.containsValue(transactions))
        {
            throw new TransactionAlreadyExistException("Account & Transaction existiert schon");
        }
        else if (transactions instanceof Transaction || account.isEmpty())
        {
            throw new TransactionAttributeException("Attribute fehler");
        }

        else
        {

            for(Transaction transaction:transactions){
                addTransaction(account, transaction);
            }
            System.out.println("Account mit Liste von Transaktionen erstellt!");
        }
    }

    /**
     * Eine Methode, um eine Transaktion einer vorhandenen Key in Hashmap hinzuzufügen
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException  throwt exception, wenn Transaction bereits existiert
     * @throws AccountDoesNotExistException throwt exception, wenn Klasse nicht existiert
     * @throws TransactionAttributeException throwt exception, wenn Attribut fehlerhaft
     * @throws IncomingInterestException throwt exception, wenn IncomingInterest fehlerhaft
     * @throws OutgoingInterestException throwt exception, wenn OutgoingInterest fehlerhaft
     */
    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingInterestException, OutgoingInterestException {
        if (!(accountsToTransaction.containsKey(account)))
        {
            throw new AccountDoesNotExistException("Account existiert nicht");
        }
        else if (this.containsTransaction(account, transaction))
        {
            throw new TransactionAlreadyExistException("Transaction existiert schon");
        }
        else if (accountsToTransaction.isEmpty())
        {
            throw new TransactionAttributeException("Attribute fehlt oder hat einen Fehler!");
        }
        else
        {
            if (transaction instanceof Payment payment)
            {
                payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
                List <Transaction> transactionsList = new ArrayList<>(accountsToTransaction.get(account));
                transactionsList.add(transaction);
                accountsToTransaction.put(account,transactionsList);
                System.out.println("Transaktion(Payment) wurde hinzugefügt!\n");
            }
            else
            {
                List <Transaction> transactionsList = new ArrayList<>(accountsToTransaction.get(account));
                transactionsList.add(transaction);
                accountsToTransaction.put(account,transactionsList);
                System.out.println("Transaktion(Transfer) wurde hinzugefügt!\n");
            }
        }
    }

    /**
     * Entfernt eine Transaktion aus einer vorhandene Liste
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException throwt eine Exception, wenn Account nicht existiert
     * @throws TransactionDoesNotExistException throwt eine exception, wenn Transaction nicht existiert
     * @throws IncomingInterestException throwt eine exception, wenn IncomingInterest fehlerhaft ist
     * @throws OutgoingInterestException throwt eine exception, wenn OutgoingInterest fehlerhaft ist
     */
    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IncomingInterestException, OutgoingInterestException {
        if (!(accountsToTransaction.containsKey(account)))
        {
            throw new AccountDoesNotExistException("Account existiert nicht");
        }
        else if (!(accountsToTransaction.get(account).contains(transaction)))
        {
            throw new TransactionDoesNotExistException("Transactio existiert nicht");
        }
        else
        {
            List<Transaction> transactionList = new ArrayList<>(accountsToTransaction.get(account));
            transactionList.remove(transaction);
            accountsToTransaction.put(account,transactionList);
            System.out.println("Neue Liste ersetzt!");

        }

    }

    /**
     * Eine Methode, um Guthaben zu erhalten
     * @param account the selected account
     * @return accountBalance, den Betrag des Account
     */
    @Override
    public double getAccountBalance(String account)
    {
        double accountBalance = 0;
        if(accountsToTransaction.containsKey(account)){
            for(Transaction transaction : accountsToTransaction.get(account)){
                accountBalance += transaction.calculate();
            }
            return accountBalance;
        }else{
            return accountBalance;
        }

    }

    /**
     * Diese Methode fragt ab, ob die Value(Liste) in Key(Account) existiert
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     * @return boolean
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if(accountsToTransaction.get(account).contains(transaction)){
            return true;
        }
        return false;
    }

    /**
     * gibt die Transaktionen des Accounts zurück
     * @param account the selected account
     * @return Transaktionsliste vom Account
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransaction.get(account);
    }

    /**
     * Diese Methode gibt die Transaktionsliste in auf- oder absteigender Reihenfolge zurück
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return die sortierte Liste sortList
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> sortList = new ArrayList<>(accountsToTransaction.get(account));
        if(asc){
            sortList.sort(Comparator.comparingDouble(Transaction::calculate));
        }else{
            sortList.sort(Comparator.comparingDouble(Transaction::calculate).reversed());
        }
        return sortList;
    }

    /**
     * Diese Methode gibt die Transaktionsliste mit nur positive oder negative Beträge an
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return die sortiere Liste nach pos. oder neg. Zahlen sortListTyp
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> sortListTyp = new ArrayList<>();
        if(positive){
            System.out.println("Liste der positiven Transaktionen: " + account + ": ");
        }else{
            System.out.println("Liste der negativen Transaktionen: " + account + ": ");
        }
        int pos = 0;
        for(Transaction transaction:accountsToTransaction.get(account))
        {

            if(positive && accountsToTransaction.get(account).get(pos).calculate() >= 0)
            {
                sortListTyp.add(transaction);
            }
            else if (!positive && accountsToTransaction.get(account).get(pos).calculate() < 0)
            {
                sortListTyp.add(transaction);
            }
            pos++;
        }
        return sortListTyp;
    }

    /**
     * Methode, um Attribute einanderzuhängen
     * @return alle Attribute als String
     */
    @Override
    public String toString() {
        return "Name: " + getName() + " " +
                "Gebühr Einzahlung: " + getIncomingInterest() + " "  +
                "Gebühr Auszahlung: " + getOutgoingInterest() + " " +
                "Account: " + accountsToTransaction;
    }

    /**
     * vergleich mit Objekten
     * @param other ein Objekt, was mi tinstanceof die Typen/Aufbau des PrivateBank vergleicht
     * @return true oder false
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof PrivateBank privateBank) {
            return ((this.name == privateBank.name)
                    && (this.incomingInterest == privateBank.incomingInterest)
                    && (this.outgoingInterest == privateBank.outgoingInterest)
                    && (accountsToTransaction.equals(privateBank.accountsToTransaction)
            ));
        }
        else{
            return false;
        }
    }
}
