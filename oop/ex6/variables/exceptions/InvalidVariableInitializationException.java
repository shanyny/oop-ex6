package oop.ex6.variables.exceptions;

/**
 * This exception is thrown if a line that is supposed to initialize oop.ex6.variables is not valid.
 * @author Shany Gindi and Roy Urbach
 */
public class InvalidVariableInitializationException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Illegal variable assignment or initialization line.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
