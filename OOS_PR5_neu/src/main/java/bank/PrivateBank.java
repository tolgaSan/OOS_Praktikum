package bank;

import bank.exceptions.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private String directoryName;

    /**
     *Konstruktor, um die Bank zu erzeugen
     * @param name beschreibt den Namen der Bank
     * @param incomingInterest beschreibt die Gebühr für Einzahlung
     * @param outgoingInterest beschreibt die Gebühr für Auszahlung
     * @param directoryName Verzeichnis die mithilfe des Konstruktor erzeugt wird (mkdir)
     * folgendes sind Exception, die jeweils in ihrer eigenen Klasse beschrieben wird
     * @throws TransactionAlreadyExistException
     * @throws AccountAlreadyExistsException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     * @throws OutgoingInterestException
     * @throws IOException
     * @throws IncomingInterestException
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {

        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        setDirectoryName(directoryName);

        File f = new File("/Users/TolgaSanli/IdeaProjects/OOS_PR5/" + directoryName);
        if(!f.exists()){
            //  C:\Users\TolgaSanli\IdeaProjects\OOS_PR5\
            if (f.mkdir() == true) {
                System.out.println("Verzeichnis hinzugefügt.");
            }
            else {
                System.out.println("Verzeichnis konnte nicht hinzugefuegt werden.");
            }
        }
        try {
            readAccounts();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Copykonstruktor, um die Bank zu erzeugen
     * @param privateBank "kopiere" den Konstruktor
     */
    public PrivateBank(PrivateBank privateBank) throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {
        this(privateBank.name, privateBank.incomingInterest, privateBank.outgoingInterest, privateBank.directoryName);
        this.accountsToTransaction = privateBank.accountsToTransaction;
    }

    /**
     * Getter, um das Verzeichnis, wo die Accounts gespeichert werden, zurückgeben
     * @return name der Verzeichnis
     */
    public String getDirectoryName() {
        return directoryName;
    }

    /**
     * Sette den Verzeichnis
     * @param directoryName Name der Verzeichnis
     * @return gebe Name der Verzeichnis zurück
     */
    public String setDirectoryName(String directoryName) {
        return this.directoryName = directoryName;
    }

    /**
     * gette den Namen der Bank
     * @return name der Bank
     */
    public String getName() {
        return name;
    }

    /**
     * Sette den Namen der Bank
     * @param name setzen
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt ein double Wert vom incomingInterest
     * @return Gebühr für Einazhlung
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * sette die Gebühr für Interest
     * @param incomingInterest Gebühr für Einazhlung setzen
     */
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    /**
     * gibt ein double Wert vom outgoingInterest
     * @return Gebühr für Auszahlung
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * sette die Gebühr für Interest
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
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransaction.containsKey(account))
        {
            throw new AccountAlreadyExistsException("Account existiert schon");
        }
        else
        {
            accountsToTransaction.put(account, List.of());
            writeAccount(account);
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
     * @throws IOException throwt exception, wenn I/O failt
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if (accountsToTransaction.containsKey(account))
        {
            throw new AccountAlreadyExistsException("Account existiert schon");
        }
        else if(accountsToTransaction.containsValue(transactions))
        {
            throw new TransactionAlreadyExistException("Transaction existiert schon");
        }

        else if (transactions instanceof Transaction || account.isEmpty())
        {
            throw new TransactionAttributeException("Attribute fehler, nur Payments und Transfers");
        }
        else
        {

            createAccount(account);
            for(Transaction transaction:transactions){
                try {
                    addTransaction(account, transaction);
                } catch (AccountDoesNotExistException e) {
                    throw new RuntimeException(e);
                } catch (IncomingInterestException e) {
                    throw new RuntimeException(e);
                } catch (OutgoingInterestException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Account mit Liste von Transaktionen erstellt!");
        }
    }

    /**
     * Eine Funktion, um ein vorhandenes Account zu löschen
     * @param account bestehendes Account, das zu löschen ist
     * @throws AccountDoesNotExistException throwt Exception, wenn Account nicht existiert
     * @throws IOException throwt Exception, wenn kp
     */
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException{
        if(!accountsToTransaction.containsKey(account)){
            throw new AccountDoesNotExistException("Account " + account +"existiert nicht!");
        }else{
            accountsToTransaction.remove(account);
            Path file = Path.of("/Users/TolgaSanli/IdeaProjects/OOS_PR5/" + this.directoryName+ "/"+account+".json");
            Files.deleteIfExists(file);
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingInterestException, OutgoingInterestException, IOException {
        if (!(accountsToTransaction.containsKey(account)))
        {
            throw new AccountDoesNotExistException("Account existiert nicht");
        }
        else if (accountsToTransaction.isEmpty())
        {
            throw new TransactionAttributeException("Attribute fehler");
        }
        else if (accountsToTransaction.get(account).contains(transaction))
        {
            throw new TransactionAlreadyExistException("Transactio existiert schon");
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
                writeAccount(account);
                System.out.println("Transaktion(Payment) wurde hinzugefügt!\n");
            }
            else
            {
                List <Transaction> transactionsList = new ArrayList<>(accountsToTransaction.get(account));
                transactionsList.add(transaction);
                accountsToTransaction.put(account,transactionsList);
                writeAccount(account);
                System.out.println("Transaktion hinzugefügt");
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
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IncomingInterestException, OutgoingInterestException, IOException {
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
            writeAccount(account);
            System.out.println("Neue Liste ersetzt!");

        }

    }

    /**
     * Eine Funktion, um alle accounts auszugeben
     * @return eine Liste(Array) mit accounts
     * keySet() returnt eine Mapped liste
     */
    public List<String> getAllAccounts(){
        /*List<String> accounts = null;
        for(Transaction transaction: accountsToTransaction.get(accounts))*/
        return new ArrayList<String>(accountsToTransaction.keySet());
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
                "Account: " + accountsToTransaction +
                "Directory: " + getDirectoryName();
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
                    && (this.directoryName == privateBank.directoryName)
            ));
        }
        else{
            return false;
        }
    }

    /**
     * eine Methode, um die .json Dateien auszulesen.
     * @throws IOException
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     * @throws Exception
     */
    public void readAccounts() throws IOException, AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, Exception {
        File file = new File("/Users/TolgaSanli/IdeaProjects/OOS_PR5/" + this.directoryName);
        File[] accountList = Objects.requireNonNull(file.listFiles());
        for(File account: accountList){
            String accountName = account.getName().replace(".json", "");
            String fileName = account.getName();
            List<Transaction> transactionList = new ArrayList<>();
            Reader reader = new FileReader(file.toString() + "/" + fileName);

            JsonArray jsonArray = (JsonArray) JsonParser.parseReader(reader);
            System.out.println(jsonArray);
            for(JsonElement jsonElementTransaction : jsonArray.getAsJsonArray()){
                JsonObject jsonObjectTransaction = jsonElementTransaction.getAsJsonObject();
                Gson gson = new GsonBuilder().registerTypeAdapter(Transaction.class, new CustomSerializierDeserializer()).create();
                String instanz = gson.toJson(jsonObjectTransaction.get("INSTANCE"));

                if(jsonObjectTransaction.get("CLASSNAME").getAsString().equals("Payment")){
                    Payment payment = gson.fromJson(instanz, Payment.class);
                    transactionList.add(payment);
                }
                else if(jsonObjectTransaction.get("CLASSNAME").getAsString().equals("OutgoingTransfer")){
                    OutgoingTransfer outgoingTransfer = gson.fromJson(instanz, OutgoingTransfer.class);
                    transactionList.add(outgoingTransfer);
                }
                else if(jsonObjectTransaction.get("CLASSNAME").getAsString().equals("IncomingTransfer")){
                    IncomingTransfer incomingTransfer = gson.fromJson(instanz, IncomingTransfer.class);
                    transactionList.add(incomingTransfer);
                }
            }
            PrivateBank.this.createAccount(accountName, transactionList);
        }
    }

    /**
     * eine Methode, um .json zu persistieren/serialisieren
     * @param account der zu übergebende Account aus spezifischen Klassen
     */
    private void writeAccount(String account) {
        try (FileWriter file = new FileWriter("/Users/TolgaSanli/IdeaProjects/OOS_PR5/" + this.directoryName +"/" + account + ".json")) {
            file.write("[");
            for (Transaction transaction : accountsToTransaction.get(account)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(transaction.getClass(), new CustomSerializierDeserializer()).setPrettyPrinting().create();
                String json = gson.toJson(transaction);
                if (accountsToTransaction.get(account).indexOf(transaction) != 0){
                    file.write("\n,");
                }
                file.write(json);
            }
            file.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
