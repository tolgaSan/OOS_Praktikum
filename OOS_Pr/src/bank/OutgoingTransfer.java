package bank;
import java.util.*;

public class OutgoingTransfer extends Transfer{

    @Override
    public double calculate() {
        double balance = getAmount();
        balance -= getAmount();
        return balance;
    }

}
