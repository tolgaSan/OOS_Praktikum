package bank.exceptions;

public class TransactionAttributeException extends Exception
{
    public TransactionAttributeException(String error){
        super(error);
    }
}
