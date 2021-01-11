package oop.ex6.variables.exceptions;

/**
 * This exception is thrown if a variable in current scope is being declared for the second time.
 * @author Shany Gindi and Roy Urbach
 */
public class VariableAlreadyExistsInScopeException extends VariableException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to declare for the second time a variable that already" +
            " exists in scope.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
