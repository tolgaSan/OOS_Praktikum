package bank;


import bank.exceptions.AttributeException;
import bank.exceptions.IncomingInterestException;
import bank.exceptions.OutgoingInterestException;

/**
 *
 *@author Tolga Sanli
 */
public class Payment extends Transaction
{
    //Attribut incomingInterest ist für eingehende Zahlungen, die verzinst werden
    private double incomingInterest;
    //Attribut outgoingInterest ist für ausgehende Zahlungen, die verzwinst werden
    private double outgoingInterest;

    /**
     *Default Konstruktor
     */
    public Payment(){
    }

    /**
     * Konstruktor, welches Attribute aus der Superklasse übernimmt
     * @param date Attribut, dass Datum angibt, allerdings aus der Superklasse übernommen
     * @param amount Attribut, dass Betrag angibt, allerdings aus der Superklasse übernommen
     * @param description Attribut, dass Beschreibung angibt, allerdings aus der Superklasse übernommen
     */
    public Payment(String date, double amount, String description) throws AttributeException {
        super(date, amount, description);
    }

    /**
     * Konstruktor, welches Attribute aus der Superklasse übernimmt und 2 zusätzliche Attribute settet
     * @param date Attribut, dass Datum angibt, allerdings aus der Superklasse übernommen
     * @param amount Attribut, dass Betrag angibt, allerdings aus der Superklasse übernommen
     * @param description Attribut, dass Beschreibung angibt, allerdings aus der Superklasse übernommen
     * @param incomingInterest Attribut incomingInterest wird gesettet (zusätzlich beim setten programmatisch überprüft, ob die Eingabe richtig ist)
     * @param outgoingInterest Attribut outgoingInterest wird gesettet (zusätzlich beim setten programmatisch überprüft, ob die Eingabe richtig ist)
     */
    public Payment(String date, double amount, String description ,double incomingInterest, double outgoingInterest) throws AttributeException, IncomingInterestException, OutgoingInterestException {
        super(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy-Konstruktor, der identisch mit dem normalen Konstruktor ist
     * @param payment wird an den Copy-Konstruktor gegeben. Somit übernimmt der CopyKonstruktor die Attribute(den Objekt)
     */
    public Payment(Payment payment) throws AttributeException, IncomingInterestException, OutgoingInterestException {
        this(payment.date, payment.amount, payment.description, payment.incomingInterest, payment.outgoingInterest);
    }

    /**
     * Getter für incomingInterest
     * @return gibt incomingInterest zurück
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }
    /**
     * settet IncomingInterest. Hierbei wird überprüft, ob IncomingInterest zwischen 0 und 1 liegt
     * @param incomingInterest wird geprüft, ob das Parameter zwischen 0 und 1 liegt. Wenn ja, dann setten, falls nein, gebe eine Warnung aus.
     */
    public void setIncomingInterest(double incomingInterest) throws IncomingInterestException {
        calculate();
        if(incomingInterest >= 0 && incomingInterest <= 1){
            this.incomingInterest = incomingInterest;
        }else{
            throw new IncomingInterestException("Gebührbetrag kann nur von 0 bis 1 sein");
        }
    }
    /**
     * Getter für outgoingInterest
     * @return gibt outgoingInterest zurück
     */
    public double getOutgoingInterest(){
        return outgoingInterest;
    }
    /**
     * settet outgoingInterest. Hierbei wird überprüft, ob outgoingInterest zwischen 0 und 1 liegt
     * @param outgoingInterest wird geprüft, ob das Parameter zwischen 0 und 1 liegt. Wenn ja, dann setten, falls nein, gebe eine Warnung aus.
     */
    public void setOutgoingInterest(double outgoingInterest) throws OutgoingInterestException {
        if(outgoingInterest >= 0 && outgoingInterest <= 1){
            this.outgoingInterest = outgoingInterest;
        }else{
            throw new OutgoingInterestException("Gebührbetrag kann nur von 0 bis 1 sein");
        }
    }

    /**
     * Die Superklasse toString wird überschrieben
     * @return gibt die Attribute aus der Superklasse Transaction zurück. Zuästzlich gibt die Methode incoming und outgoing als String zurück.
     */
    @Override
    public String toString(){
        return super.toString() + " " + this.incomingInterest + " " + this.outgoingInterest;
    }
    /**
     * prüft, ob getAmount größer 0 oder kleiner 0 ist. (Ob Ein- oder Auszahlung)
     * Falls Amount größer als 0 ist, dann ziehe den Zins prozentual vom incomingInterest ab
     * Falls Amount kleiner als 0 ist, dann rechne den Zins prozentual vom outgoingInterest an.
     * Ansonsten soll eine Warnung ausgegeben werden z.B. bei einer Eingabe von 0.
     * @return gibt berechnetes ergebnis zurück.
     */
    @Override
    public double calculate() {
        double ergebnis = 0;
        if(getAmount() > 0){
            double einzahlung = getAmount();
            double zinsenEinzahlung = getIncomingInterest();

            ergebnis = einzahlung - (einzahlung * zinsenEinzahlung);
            return ergebnis;
        }else if(getAmount() < 0){
            double auszahlung = getAmount();
            double zinsenAuszahlung = getOutgoingInterest();

            ergebnis = auszahlung + (auszahlung*zinsenAuszahlung);
            return ergebnis;
        }else{
            System.out.println("Kunde hat einen ungültigen Wert eingegeben.");
        }
        return ergebnis;
    }

    /**
     * Überschriebene equals() aus der Superklasse
     * @param other erhält ein Objekt um den Inhalt zu vergleichen
     *              instanceof Operator prüft, ob es eine Beziehung zu other und Payment Objekt hat. Andernfalls liefert es false
     * @return erhält true (Im Falle, wenn der Inhalt der Attribute (this) und der Inhalt des Objekts gleich sind) oder false, wenn sie es nicht sind
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Payment payment) {
            return ((super.equals(payment)) && (this.incomingInterest == payment.incomingInterest) && (this.outgoingInterest == payment.outgoingInterest));
        }else{
            return false;
        }
    }
}

