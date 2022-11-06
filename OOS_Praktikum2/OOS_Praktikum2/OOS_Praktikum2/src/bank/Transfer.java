package bank;

public class Transfer extends Transaction
        implements CalculateBill{
    //
    private String sender;
    //
    private String recipient;

    /**
     *
     */
    public Transfer(){}

    /**
     *
     * @param date
     * @param amount
     * @param description
     */
    public Transfer(String date, double amount, String description){
        super(date, amount, description);
    }

    /**
     *
     * @param date
     * @param amount
     * @param description
     * @param sender
     * @param recipient
     */
    public Transfer(String date, double amount, String description, String sender, String recipient){
        super(date, amount, description);
        setAmount(amount);
        setSender(sender);
        setRecipient(recipient);
    }

    /**
     *
     * @param transfer
     */
    public Transfer(Transfer transfer){
        this(transfer.date, transfer.amount, transfer.description, transfer.sender, transfer.recipient);
    }

    /**
     *
     * @param amount
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
     *
     * @return
     */
    public String getSender(){
        return sender;
    }

    /**
     *
     * @param sender
     */
    public void setSender(String sender){
        this.sender = sender;
    }

    /**
     *
     * @return
     */
    public String getRecipient(){
        return recipient;
    }

    /**
     *
     * @param recipient
     */
    public void setRecipient(String recipient){
        this.recipient = recipient;
    }


    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return super.toString() + " " + getSender() + " " + getRecipient();
    }

    /**
     *
     * @return
     */
    @Override
    public double calculate() {
        double ergebnis = getAmount();
        return ergebnis;
    }

    /**
     *
     * @param other
     * @return gibt zurück, ob es falsch oder richtig ist (true oder false)
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Transfer transfer) {
            if ((super.equals(transfer)) && (this.sender == transfer.sender) && (this.recipient == transfer.recipient)) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}
