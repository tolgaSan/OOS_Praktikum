package bank;
//Klasse Payment ist eine Klasse für Überweisungen
public class Payment extends Transaction
        implements CalculateBill
{
    //Attribut incomingInterest ist für eingehende Zahlungen, die verzinst werden
    private double incomingInterest;
    //Attribut outgoingInterest ist für ausgehende Zahlungen, die verzwinst werden
    private double outgoingInterest;

    /**
     *
     */
    public Payment(){
    }

    /**
     *
     * @param date Gibt das datum an
     * @param amount gibt die Menge an
     * @param description gibt die Beschreibung an
     */
    public Payment(String date, double amount, String description){
        super(date, amount, description);
    }

    /**
     *
     * @param date
     * @param amount
     * @param description
     * @param incomingInterest
     * @param outgoingInterest
     */
    public Payment(String date, double amount, String description ,double incomingInterest, double outgoingInterest){
        super(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     *
     * @param payment Paramtere Payment payment kopiert einfach aus dem Konstruktor Payment die Attribute
     */
    public Payment(Payment payment){
        this(payment.date, payment.amount, payment.description, payment.incomingInterest, payment.outgoingInterest);
    }

    /**
     *
     * @return
     */
    public double getIncomingInterest(){
        return incomingInterest;
    }
    /**
     *
     * @param incomingInterest
     */
    public void setIncomingInterest(double incomingInterest){
        calculate();
        if(incomingInterest >= 0 && incomingInterest <= 1){
            this.incomingInterest = incomingInterest;
        }else{
            System.out.println("Die Eingabe ist nicht korrekt.");
        }
    }
    /**
     *
     * @return
     */
    public double getOutgoingInterest(){
        return outgoingInterest;
    }
    /**
     *
     * @param outgoingInterest
     */
    public void setOutgoingInterest(double outgoingInterest){
        if(outgoingInterest >= 0 && outgoingInterest <= 1){
            this.outgoingInterest = outgoingInterest;
        }else{
            System.out.println("Die Eingabe ist nicht korrekt.");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return super.toString() + this.incomingInterest + " " + this.outgoingInterest;
    }
    /**
     *
     *
     * @return
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
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Payment payment) {
            if ((super.equals(payment)) && (this.incomingInterest == payment.incomingInterest) && (this.outgoingInterest == payment.outgoingInterest)) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}

