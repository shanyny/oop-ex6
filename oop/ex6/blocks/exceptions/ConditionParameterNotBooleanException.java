package oop.ex6.blocks.exceptions;

public class ConditionParameterNotBooleanException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "The given condition is not resulting in true or false values.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
