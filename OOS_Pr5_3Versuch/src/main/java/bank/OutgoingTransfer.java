package bank;
import bank.exceptions.AttributeException;

import java.util.*;

/**
 * Die Klasse verwaltet ausgehende Transaktionen
 */
public class OutgoingTransfer extends Transfer{

    /**
     * Konstruktor um den Objekt für OutgoingTransfer zu erzeugen
     * @param Date beschreibt das Datum
     * @param Amount beschreibt den Betrag
     * @param Description beschreibt die Beschreibung
     * @throws AttributeException gibt eine Exception aus, wenn amount negativ ist.
     */
    public OutgoingTransfer(String Date, double Amount, String Description) throws AttributeException {
        super(Date, Amount, Description);
    }

    /**
     * Konstruktor um den Objekt für OutgoingTransfer zu erzeugen
     * @param Date beschreibt das Datum
     * @param Amount beschreibt den Betrag
     * @param Description beschreibt die Beschreibung
     * @param Sender beschreibt den Sender
     * @param Recipient beschreibt den Empfänger
     * @throws AttributeException gibt eine Exception aus, wenn amount negativ ist.
     */
    public OutgoingTransfer(String Date, double Amount, String Description, String Sender, String Recipient) throws AttributeException {
        super(Date, Amount, Description, Sender, Recipient);
    }

    /**
     * überschriebene Methode calculate()
     * @return gibt den Betrag in negativ zurück
     */
    @Override
    public double calculate() {
        double balance = getAmount();

        return (-balance);
    }
}
