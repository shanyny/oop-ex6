package oop.ex6.textparsers.exceptions.methodcall;

import oop.ex6.textparsers.exceptions.methodcall.MethodCallException;

/**
 * This exception is thrown whenever trying to call a method which doesn't exist.
 * @author Shany Gindi and Roy Urbach
 */
public class CalledUnknownMethodException extends MethodCallException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to call a method which doesn't exist.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
