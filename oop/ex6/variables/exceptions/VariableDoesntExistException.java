package oop.ex6.variables.exceptions;

/**
 * An exception that is thrown whenever trying to call a variable which doesn't exist in scope.
 * @author Shany Gindi and Roy Urbach
 */
public class VariableDoesntExistException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to assign a new value to a variable that doesn't exist " +
            "in scope.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
