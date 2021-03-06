package oop.ex6.variables.exceptions;

/**
 * An exception that is thrown whenever trying to assign a value of an un-initialized variable to a
 * variable.
 * It extends VariableException.
 * @author Shany Gindi and Roy Urbach
 */
public class VariableNotInitializedException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to assign an un-initialized variable to a variable.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
