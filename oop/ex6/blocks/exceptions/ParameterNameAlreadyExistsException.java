package oop.ex6.blocks.exceptions;

public class ParameterNameAlreadyExistsException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to add a parameter to a method which already has this parameter name.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
