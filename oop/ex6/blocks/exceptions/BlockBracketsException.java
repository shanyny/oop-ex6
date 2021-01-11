package oop.ex6.blocks.exceptions;

public class BlockBracketsException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "a block brackets don't fit the requirments  ";
    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
