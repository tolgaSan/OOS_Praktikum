package bank;
//Klasse Transaction stellt eine Transaktion dar und steht als Oberklasse der Klasse Transfer und Payment
public abstract class Transaction
    implements CalculateBill {
    //
    protected String date;
    //
    protected double amount;
    //
    protected String description;

    //
    public Transaction() {
    }

    /**
     * @param date
     * @param amount
     * @param description
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }


    /**
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */

    public double getAmount() {
        return amount;
    }

    //der Attribut amount darf nicht negativ werden (schon in Transfer geregelt), daher soll mit if abgefragt werden. Falls unwahr, dann fehler ausgeben

    /**
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return getDate() + " " + calculate() + " " + getDescription();
    }

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
