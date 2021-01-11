package oop.ex6.textparsers.exceptions;

import oop.ex6.blocks.exceptions.BlockException;

/**
 * This exception is thrown if a block's line doesn't match any known format of sjava lines.
 * @author Shany Gindi and Roy Urbach
 */
public class LineUnknownFormatException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "A line that doesn't match any known format of Sjava lines.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
