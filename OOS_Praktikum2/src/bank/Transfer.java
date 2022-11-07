package bank;
//eine Klasse, um einen Transfer zwischen 2 Personen zu tätigen
public class Transfer extends Transaction {
    //privates Attribut sender
    private String sender;
    //privates Attribut Empfänger
    private String recipient;

    /**
     *default Konstruktor
     */
    public Transfer(){}

    /**
     * Konstruktor, um 3 Attribute zu konstruktiiren
     * @param date Attribut, dass Datum angibt, allerdings aus der Superklasse übernommen
     * @param amount Attribut, dass Betrag angibt, allerdings aus der Superklasse übernommen
     * @param description Attribut, dass Beschreibung angibt, allerdings aus der Superklasse übernommen
     */
    public Transfer(String date, double amount, String description){
        super(date, amount, description);
    }

    /**
     * Konstrukor, um die 3 üblichen Attribute zu konstruiren, zusätzlich wird das Attribut amount neu gesettet,
     * da der Inhalt sich ändert (überprüfen, ob der Betrag <= 0 ist und die funktion calculate() ausführen(unnötig?))
     * @param date Attribut, dass Datum angibt, allerdings aus der Superklasse übernommen
     * @param amount Attribut, dass Betrag angibt, allerdings aus der Superklasse übernommen
     * @param description Attribut, dass Beschreibung angibt, allerdings aus der Superklasse übernommen
     * @param sender Attribut Sender wird gesettet
     * @param recipient Attribut Empfänger wird gesettet
     */
    public Transfer(String date, double amount, String description, String sender, String recipient){
        super(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     *Copy-Konstruktor, der identisch mit dem normalen Konstruktor ist
     * @param transfer wird an den Copy-Konstruktor gegeben. Somit übernimmt der CopyKonstruktor die Attribute(den Objekt)
     */
    public Transfer(Transfer transfer){
        this(transfer.date, transfer.amount, transfer.description, transfer.sender, transfer.recipient);
    }

    /**
     * Setter für amount, wobei es programmatisch überprüft wird, ob amount größergleich 0 ist. (größer macht mehr Sinn?)
     * @param amount Settet das Attribut amount(Betrag)
     */
    public void setAmount(double amount){
        if(amount <= 0){
            System.out.println("Dein Betrag ist negativ.");
        }else{
            calculate();
            this.amount = amount;
        }
    }

    /**
     * Getter für sender
     * @return gibt sender zurück
     */
    public String getSender(){
        return sender;
    }

    /**
     * Setter für sender
     * @param sender settet mit dem Parameter den sender dieser Klasse
     */
    public void setSender(String sender){
        this.sender = sender;
    }

    /**
     * Getter für recipient
     * @return gibt recipient zurück
     */
    public String getRecipient(){
        return recipient;
    }

    /**
     * Setter für recipient
     * @param recipient settet mit dem Parameter den recipient dieser Klasse
     */
    public void setRecipient(String recipient){
        this.recipient = recipient;
    }


    /**
     * toString wird überschrieben
     * @return gibt den toString() aus der Superklasse (Transaktion) und zusätzlich sender und recipient als String zurück
     */
    @Override
    public String toString(){
        return super.toString() + " " + getSender() + " " + getRecipient() + " gesendeter Betrag: ";
    }

    /**
     * eine Funktion, die aus dem Interface CalculateBill übernommen und geändert wurde
     * @return gibt den double ergebnis zurück, welches den Wert aus getAmount erhalten hat.
     */
    @Override
    public double calculate() {
        double ergebnis = getAmount();
        return ergebnis;
    }

    /**
     * Überschriebene equals() aus der Superklasse
     * @param other erhält ein Objekt um den Inhalt zu vergleichen
     *                   instanceof Operator prüft, ob es eine Beziehung zu other und Transfer Objekt hat. Andernfalls liefert es false
     * @return erhält true (Im Falle, wenn der Inhalt der Attribute (this) und der Inhalt des Objekts gleich sind) oder false, wenn sie es nicht sind
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Transfer transfer) {
            return ((super.equals(transfer)) && (this.sender == transfer.sender) && (this.recipient == transfer.recipient));
        }else{
            return false;
        }
    }
}
