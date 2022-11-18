package bank;
import java.util.*;

public class IncomingTransfer extends Transfer{

    public IncomingTransfer(Transfer transfer){
        super(transfer);
    }

    @Override
    public double calculate() {
        double balance = getAmount();
        balance += getAmount();
        return balance;
    }
}
