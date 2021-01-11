package oop.ex6.variables.exceptions;

/**
 * An exception that is thrown whenever trying to assign to a variable a value which is not compatible
 * with its type.
 * It extends VariableException.
 * @author Shany Gindi and Roy Urbach
 */
public class NewValueNotCompatibleException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Trying to assign to a variable a value which is not compatible " +
            "with its type.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
