package bank;
import java.util.List;
import java.util.Map;

public class PrivateBank {



    public String name;
    protected double incomingInterest;
    protected double outgoingInterest;
    protected Map<String, List<Transaction>> accountsToTransactions;

    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    public PrivateBank(PrivateBank privateBank){
        this(privateBank.name, privateBank.incomingInterest, privateBank.outgoingInterest);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PrivateBank privateBank) {
            return ((this.name == privateBank.getName()) && (this.incomingInterest == privateBank.getIncomingInterest()) && (this.outgoingInterest == privateBank.getOutgoingInterest()));
        }else{
            return false;
        }
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

}
