package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

/**
 * This class represents a block opened under an If or While statement. It extends block class and
 */
public class ConditionalBlock extends Block {

    /**
     * This constructor is using it's super constructor and using validate() to immidiatly
     * validate the block on creation. sjava don't allow methods inside methods.
     * @param parent the upper scope block object
     * @param strings iterable of the textual lines in the block
     * @throws BlockException
     * @throws VariableException
     * @throws OneLinerException
     */
    public ConditionalBlock(Block parent, Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        super(parent, strings);
        validate();
    }

}
