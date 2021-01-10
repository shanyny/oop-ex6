package oop.ex6.variables.exceptions;

/**
 * This exception is thrown if a method's parameters are illegal.
 * @author Shany Gindi and Roy Urbach
 */
public class IllegalMethodParameters extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Method's parameters are illegal.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
