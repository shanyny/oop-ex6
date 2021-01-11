package oop.ex6.textparsers.exceptions;

import oop.ex6.blocks.exceptions.BlockException;

public class BlockBracketsException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "unclosed block.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
