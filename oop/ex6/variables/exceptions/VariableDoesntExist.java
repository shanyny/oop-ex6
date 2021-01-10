package oop.ex6.variables.exceptions;

/**
 * An exception that is thrown whenever trying to assign a new value to a none-existent variable or
 * trying to assign a none-existent variable.
 * @author Shany Gindi and Roy Urbach
 */
public class VariableDoesntExist extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to assign a new value to a variable that doesn't exist " +
            "in scope.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
