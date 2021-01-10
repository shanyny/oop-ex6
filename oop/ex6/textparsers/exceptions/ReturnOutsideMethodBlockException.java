package oop.ex6.textparsers.exceptions;

/**
 * This exception is abstract and represents all method calls exceptions.
 * @author Shany Gindi and Roy Urbach
 */
public class ReturnOutsideMethodBlockException extends OneLinerException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to return outside of a method block.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
