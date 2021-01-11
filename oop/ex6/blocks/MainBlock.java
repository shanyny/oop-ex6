package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

/**
 * This class extends Block and is representing the global scope of a Sjava code file.
 * Every file is assigned exactly one MainBlock object. all methods are in the main block scope.
 * @author Shany Gindi and Roy Urbach
 */
public class MainBlock extends Block {

    /**
     * This Constructor is using it's super constructor to assign a null parent to this object.
     * Using validate() immediately to validate all methods in the code.
     * @param strings iterable of the textual lines in the block
     * @throws BlockException - general exception regarding block errors
     * @throws VariableException - general exception regarding variable errors
     * @throws OneLinerException - general exception regarding one liner command errors
     */
    public MainBlock(Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        super(null, strings);
        validate();
    }

    /**
     * This method identifies this class as the global scope of the program.
     * @return always true for MainBlock objects.
     */
    @Override
    public boolean isGlobal() {
        return true;
    }
}
