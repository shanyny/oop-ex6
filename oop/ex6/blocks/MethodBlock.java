package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.ParameterNameAlreadyExistsException;
import oop.ex6.variables.Variable;

import java.util.LinkedList;

/**
 * This class extends Block and represents a method. Each method name is registered under its MainBlock
 * and consists of pre-initialize parameters that are given when the method is called.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodBlock extends Block {

    /* the method's parameters that are automatically initialized on creation. */
    private LinkedList<Variable> parameters;

    /* the method's name. */
    private final String name;

    /**
     * A simple constructor that uses it's super constructor and assigning a name to this method.
     * @param parent block object of upper scope block
     * @param strings iterable of the textual lines in the block
     * @param name the string name identifying the specific method
     */
    public MethodBlock(Block parent, Iterable<String> strings, String name) {
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
        if (parameters == null) parameters = new LinkedList<>();
        if (getVariable(parameter.getName(), true) != null) {
            throw new ParameterNameAlreadyExistsException();
        }
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
