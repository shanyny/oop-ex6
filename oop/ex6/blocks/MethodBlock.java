package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.blocks.exceptions.ParameterNameAlreadyExistsException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.Variable;
import oop.ex6.variables.exceptions.VariableException;

import java.util.LinkedList;

public class MethodBlock extends Block {

    private final LinkedList<Variable> parameters = new LinkedList<>();
    private final String name;

    public MethodBlock(Block parent, Iterable<String> strings, String name) throws BlockException, VariableException, OneLinerException {
        super(parent, strings);
        this.name = name;
    }

    /**
     * This method returns the method's name.
     * @return the method's name.
     */
    public String getName() {
        return name;
    }


    /**
     * This method adds a parameter to the parameters linked list.
     */
    public void addParameter(Variable parameter) throws ParameterNameAlreadyExistsException {
        if (getVariable(parameter.getName(), true) != null) throw new ParameterNameAlreadyExistsException();
        else {
            parameters.add(parameter);
            addVariable(parameter);
        }
    }

    /**
     * This method returns the valueless parameters of the method as a linked list.
     * @return the parameters of the method.
     */
    public LinkedList<Variable> getParameters() {
        return parameters;
    }
}
