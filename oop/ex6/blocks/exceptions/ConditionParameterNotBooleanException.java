package oop.ex6.blocks.exceptions;

public class ConditionParameterNotBooleanException extends BlockException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Tried to open a condition block in the main block of the program.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
