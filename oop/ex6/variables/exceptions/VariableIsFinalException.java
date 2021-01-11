package oop.ex6.variables.exceptions;

/**
 * An exception that is thrown whenever trying to assign a new value to a final variable.
 * It extends VariableException.
 * @author Shany Gindi and Roy Urbach
 */
public class VariableIsFinalException extends VariableException{
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Final variable cannot be assigned after initialization.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
