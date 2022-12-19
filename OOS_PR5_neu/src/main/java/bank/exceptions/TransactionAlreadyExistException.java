package bank.exceptions;

public class TransactionAlreadyExistException extends Exception
{
    public TransactionAlreadyExistException(String error){
        super(error);
    }
}
