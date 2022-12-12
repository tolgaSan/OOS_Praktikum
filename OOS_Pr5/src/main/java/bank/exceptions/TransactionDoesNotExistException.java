package bank.exceptions;

public class TransactionDoesNotExistException extends Exception
{
    public TransactionDoesNotExistException(String error){
        super(error);
    }
}
