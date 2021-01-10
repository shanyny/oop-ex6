package oop.ex6.blocks;
import oop.ex6.textparsers.LineParser;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.*;
import oop.ex6.variables.exceptions.VariableException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Block {

    private final HashMap<String, Variable> variables = new HashMap<>();
    private final HashMap<String, Block> subBlocks = new HashMap<>();
//    private final LinkedList<oop.ex6.blocks.ConditionalBlock> conditionals = new LinkedList<>();
    private final LineParser lineParser;
    public final Iterable<String> strings;
    private final Block parent;




    public Block(Block parent, Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        this.parent = parent;
        this.strings = strings;
        lineParser = new LineParser(this,strings);
        lineParser.parse();
    }

    protected void validate() throws ConditionParameterNotBoolean{
//        for (Block block: )
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
        MethodBlock method = methods.get(methodName);
        if (method != null) return method;
        else if (!isGlobal()) return parent.getMethod(methodName);
        else return null;
    }

    /**
     * This method adds a MethodBlock to the block's scope.
     * @param methodBlock - the MethodBlock to add.
     */
    public void addMethod(MethodBlock methodBlock) {
        methods.put(methodBlock.getName(), methodBlock);
    }
}