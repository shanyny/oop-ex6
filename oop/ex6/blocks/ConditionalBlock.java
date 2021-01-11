package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

/**
 * This class represents a block opened under an If or While statement. It extends block class and validates
 * itself automatically.
 * @author Shany Gindi and Roy Urbach
 */
public class ConditionalBlock extends Block {

    /**
     * This constructor is using it's super constructor and using validate() to immediately
     * validate the block on creation. Sjava don't allow methods inside methods.
     * @param parent the upper scope block object
     * @param strings iterable of the textual lines in the block
     * @throws BlockException - general exception regarding block errors
     * @throws VariableException - general exception regarding variable errors
     * @throws OneLinerException - general exception regarding one liner command errors
     */
    public ConditionalBlock(Block parent, Iterable<String> strings)
            throws BlockException, VariableException, OneLinerException {
        super(parent, strings);
        validate();
    }

}
