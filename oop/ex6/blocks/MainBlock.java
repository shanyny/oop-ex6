package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

public class MainBlock extends Block {

    public MainBlock(Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        super(null, strings);
        validate();
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
