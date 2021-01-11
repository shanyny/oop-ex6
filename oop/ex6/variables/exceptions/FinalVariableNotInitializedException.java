package oop.ex6.variables.exceptions;

/**
 * This exception is thrown if a variable is declared final but is not initialized.
 * @author Shany Gindi and Roy Urbach
 */
public class FinalVariableNotInitializedException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Variable is declared final but is not initialized.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
