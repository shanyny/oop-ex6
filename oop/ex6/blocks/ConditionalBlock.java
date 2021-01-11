package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

/**
 * Ifs and Whiles
 */
public class ConditionalBlock extends Block {

    public ConditionalBlock(Block parent, Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        super(parent, strings);
        validate();
    }

}
