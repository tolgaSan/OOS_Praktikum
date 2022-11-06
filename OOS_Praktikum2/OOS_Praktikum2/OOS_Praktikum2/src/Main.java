import bank.Payment;
import bank.Transaction;
import bank.Transfer;

public class Main {
        public static void main(String[] args) {

            Transfer tra = new Transfer("02.05.", 30, "Transfer", "sender", "empfänger");
            Transfer tracopy = new Transfer(tra);
            tra.toString();


            System.out.println(tra.equals(tracopy));

            tra.setAmount(500);

            System.out.println(tra.equals(tracopy));


            Payment pay1 = new Payment("05.03", -500, "payment", 0.05, 0.5);
            System.out.println(pay1.toString());

            Transfer trans1 = new Transfer("05.05.", 50, "Nescjreobuing", "sender", "emoffmsdf");
            System.out.println(trans1.toString());



        }


}