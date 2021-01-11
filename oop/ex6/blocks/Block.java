package oop.ex6.blocks;
import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.LineParser;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.*;
import oop.ex6.variables.exceptions.VariableException;

import java.util.HashMap;

/**
 * This class is an abstract form of a general block of code in sjava files. this block consists of few
 * essentials that blocks have in java like variables and methods that belong to this block's memory space.
 * each block has a validate method and add/get variables/methods methods.
 */
public abstract class Block {

    /* HashMap containing this block's variables */
    private final HashMap<String, Variable> variables = new HashMap<>();

    /* HashMap containing this block's methods, initialized only if main block. */
    private HashMap<String, MethodBlock> methods;

    /* An Iterable of Strings containing this block's textual code. */
    public final Iterable<String> strings;

    /* Block object of the upper scope */
    private final Block parent;

    /**
     * A simple constructor initializing this block's parent block and the string literals.
     * @param parent block object of upper scope block
     * @param strings iterable of the textual lines in the block
     */
    public Block(Block parent, Iterable<String> strings) {
        this.parent = parent;
        this.strings = strings;
    }

    /**
     * This method is using LineParser to validate each line in the textual context of the current block
     * and all method blocks in this block's scope.
     * @throws BlockException - general exception regarding block errors
     * @throws VariableException - general exception regarding variable errors
     * @throws OneLinerException - general exception regarding one liner command errors
     */
    protected void validate() throws BlockException, VariableException, OneLinerException {
        LineParser.parse(this, strings);
        if (methods != null) {
            for (MethodBlock methodBlock : methods.values()) {
                methodBlock.validate();
            }
        }
    }

    public boolean isGlobal(){
        return false;
    }

    /**
     * This method returns a Variable with the same name as the argument given.
     * It can search in this Block's scope, or also its ancestors' scopes.
     * @param varName - the name of the Variable wanted.
     * @param blockScope - true if the search is only in this block's scope, false if the search includes
     *                   ancestors' scopes.
     * @return a MethodBlock in the block's ancestors scope with the same name as the argument given.
     */
    public Variable getVariable(String varName, boolean blockScope) {
        Variable variable = variables.get(varName);
        if (variable != null) return variable;
        else if (!blockScope && !isGlobal()) return parent.getVariable(varName, false);
        else return null;
    }

    /**
     * This method adds a Variable to the block's scope.
     * @param variable - the Variable to add.
     */
    public void addVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    /**
     * This method returns a MethodBlock with the same name as the argument given.
     * @param methodName - the name of the MethodBlock wanted.
     * @return a MethodBlock in the block's ancestors scope with the same name as the argument given.
     */
    public MethodBlock getMethod(String methodName) {
        if (methods != null) {
            MethodBlock method = methods.get(methodName);
            if (method != null) return method;
            else if (!isGlobal()) return parent.getMethod(methodName);
        }
        return null;
    }

    /**
     * This method adds a MethodBlock to the block's scope.
     * @param methodBlock - the MethodBlock to add.
     */
    public void addMethod(MethodBlock methodBlock) {
        if (methods == null) methods = new HashMap<>();
        methods.put(methodBlock.getName(), methodBlock);
    }

}