package bank;
//Klasse Transaction stellt eine Transaktion dar und steht als Oberklasse der Klasse Transfer und Payment
public abstract class Transaction
    implements CalculateBill {
    //protected Attribut date(Datum), kann innerhalb des Packages benutzt werden.
    protected String date;
    //protected Attribut amount(Betrag), kann innerhalb des Packages benutzt werden.
    protected double amount;
    //protected Attribut description(Beschreibung), kann innerhalb des Packages benutzt werden.
    protected String description;

    //default Konstruktor
    public Transaction() {
    }

    /**
     * Konstruktor für die 3 Attribute, die jeweils gesettet werden
     * @param date Attribut, dass ein Datum ausgeben soll, an dem die Transaktion durchgeführt wurde
     * @param amount Attribut, dass den Betrag ausgibt
     * @param description Attribut, dass Beschreibung bzw. den Verwendungszweck angibt
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }


    /**
     * @return gibt das Attribut date zurück
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date settet das Attribut date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return gibt die Beschreibung zurück
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description settet Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return gibt den Betrag zurück
     */

    public double getAmount() {
        return amount;
    }

    /**
     * @param amount settet den Betrag
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * überschriebenes toString() aus der Superklasse
     * @return gibt die 3 Attribute date, amount, description zurück als String zurück, wobei beim 2. String die Funktion calculate() aufgerufen wird
     * um den Betrag zu errechnen und zuückzugeben. (Eigentlich nicht nötig bei Transaktion(?))
     */
    @Override
    public String toString() {
        return getDate() + " " + getAmount() + " " + getDescription();
    }

    /**
     * Überschriebene equals() aus der Superklasse
     * @param other erhält ein Objekt um den Inhalt zu vergleichen
     *              instanceof Operator prüft, ob es eine Beziehung zu other und transaction Objekt hat. Andernfalls liefert es false
     * @return erhält true (Im Falle, wenn der Inhalt der Attribute (this) und der Inhalt des Objekts gleich sind) oder false, wenn sie es nicht sind
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Transaction transaction) {
            if ((this.date == transaction.date) && (this.amount == transaction.amount) && (this.description == transaction.description)) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}
