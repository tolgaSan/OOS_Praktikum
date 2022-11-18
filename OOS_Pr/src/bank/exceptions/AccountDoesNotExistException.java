package bank.exceptions;

public class AccountDoesNotExistException extends Exception
{
    public AccountDoesNotExistException(String error){
        super(error);
    }
}
