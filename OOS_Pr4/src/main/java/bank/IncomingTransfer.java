package bank;
import bank.exceptions.AttributeException;

import java.util.*;

/**
 * Die Klasse dient dazu, um Objekte der IncomingTransfer zu erzeugen
 */
public class IncomingTransfer extends Transfer{

    /**
     * Konstruktor, um den Objekt für IncomingTransfer zu erzeugen
     * @param Date beschreibt das Datum
     * @param Amount beschreibt den Betrag
     * @param Description beschreibt die Beschreibung
     * @throws AttributeException wirft eine exception im Falle eines Fehler
     */
    public IncomingTransfer(String Date, double Amount, String Description) throws AttributeException {
        super(Date, Amount, Description);
    }

    /**
     * Konstruktor, um den Objekt für IncomingTransfer zu erzeugen
     * @param Date beschreibt das Datum
     * @param Amount beschreibt den Betrag
     * @param Description beschreibt die Beschreibung
     * @param Sender beschreibt den Sender
     * @param Recipient beschreibt den Empfänger
     * @throws AttributeException wirft eine exception im Falle eines Fehler
     */
    public IncomingTransfer(String Date, double Amount, String Description, String Sender, String Recipient) throws AttributeException {
        super(Date, Amount, Description, Sender, Recipient);
    }

    /**
     * überschriebene Methode calculate
     * @return gibt den errechneten Betrag zurück
     */
    @Override
    public double calculate() {
        double balance = getAmount();

        return balance;
    }
}
