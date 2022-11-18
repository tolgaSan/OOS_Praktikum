package bank;
import java.util.*;

public class OutgoingTransfer extends Transfer{

    public OutgoingTransfer(Transfer transfer){
        super(transfer);
    }

    @Override
    public double calculate() {
        double balance = getAmount();
        balance += getAmount();
        return (-balance);
    }
}
