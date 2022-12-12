import bank.Payment;
import bank.Transaction;
import bank.Transfer;

//Klasse Main, um Systemausgabe, testings o.ä. durchzuführen
public class Main {
        public static void main(String[] args) {

            Transfer tra = new Transfer("02.05.", 30, "Transfer", "sender", "empfänger");
            Transfer tracopy = new Transfer(tra);
            System.out.println(tra.toString());

            System.out.println(tra.equals(tracopy));

            tra.setAmount(500);

            System.out.println(tra.equals(tracopy));

            Transfer trans1 = new Transfer("05.05.", 50, "Nescjreobuing", "sender", "emoffmsdf");
            System.out.println(trans1.toString());

            Payment calcu0 = new Payment("08.05", -500, "Auszahlung", 0.05, 0.1);
            Payment calcu0copy = new Payment(calcu0);
            System.out.println(calcu0.toString());

            Payment calcu1 = new Payment("09.05", 100, "Einzahlung", 0.05, 0.1);
            System.out.println(calcu1.toString());

            Payment calcu2 = new Payment("10.05", 0, "nullbetragstest", 0.05, 0.1);
            System.out.println(calcu2.toString());

            System.out.println(calcu0.equals(calcu0copy));
            calcu0.setAmount(30);
            calcu0.setIncomingInterest(0.9);
            System.out.println(calcu0);
            System.out.println(calcu0.equals(calcu0copy));




        }


}