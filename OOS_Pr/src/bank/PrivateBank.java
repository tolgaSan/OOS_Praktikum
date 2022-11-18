package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAttributeException;
import bank.exceptions.TransactionDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;

import java.sql.Array;
import java.util.*;
import java.util.HashMap;


public class PrivateBank implements Bank{

    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    public Map<String, List<Transaction>> accountsToTransaction = new HashMap<>();

    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    public PrivateBank(PrivateBank privateBank) {
        this(privateBank.name, privateBank.incomingInterest, privateBank.outgoingInterest );

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest = outgoingInterest;
    }

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

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException
    {
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
            if(transactions instanceof Payment payment){
                payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
            }
            accountsToTransaction.put(account,transactions);
            System.out.println("Account mit Liste von Transaktionen erstellt!");
        }
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException
    {
        if (!(accountsToTransaction.containsKey(account)))
        {
            throw new AccountDoesNotExistException("Account existiert nicht");
        }
        else if (accountsToTransaction.get(account).contains(transaction))
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
            }
            else
            {
                List <Transaction> transactionsList = new ArrayList<>(accountsToTransaction.get(account));
                transactionsList.add(transaction);
                accountsToTransaction.put(account,transactionsList);
            }
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException
    {
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
            if(transaction instanceof Payment payment){
                payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
            }
            List <Transaction> transactionsList = new ArrayList<>(accountsToTransaction.get(account));
            transactionsList.remove(transaction);
            accountsToTransaction.put(account,transactionsList);
        }
    }

    @Override
    public double getAccountBalance(String account)
    {
        double accountBalance = 0;
        for(Transaction transaction : accountsToTransaction.get(account)){
            accountBalance += transaction.calculate();
        }
        /*if(!(accountsToTransaction.get(account).isEmpty())){


            accountBalance += transaction.calculate();
        }*/
        return accountBalance;
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if(accountsToTransaction.get(account).contains(transaction)){
            return true;
        }
        return false;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransaction.get(account);
    }

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

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> sortList = new ArrayList<>(accountsToTransaction.get(account));
        /*if(positive){
        }else{
        }*/
        for(Transaction transaction:accountsToTransaction.get(account)){
            if(positive && transaction.calculate() >= 0){
                sortList.add(transaction);
            } else if (!positive && transaction.calculate() <= 0) {
                sortList.add(transaction);
            }
        }
        return sortList;
    }

    @Override
    public String toString() {
        return getName() + " " +  getIncomingInterest() + " " + getOutgoingInterest() + " " + accountsToTransaction;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PrivateBank privateBank) {
            return ((this.name == privateBank.name)
                    && (this.incomingInterest == privateBank.incomingInterest)
                    && (this.outgoingInterest == privateBank.outgoingInterest)
                    && (this.accountsToTransaction == privateBank.accountsToTransaction));
        }
        else{
            return false;
        }
    }
}
