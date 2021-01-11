package oop.ex6.blocks;

import oop.ex6.blocks.exceptions.ParameterNameAlreadyExistsException;
import oop.ex6.variables.Variable;

import java.util.LinkedList;

/**
 * This class extends Block and represents a method. Each method name is registered under it's MainBlock and consists of
 * preinitialize parameters that are given when the method is called.
 */
public class MethodBlock extends Block {

    /* the method parameters that are automatically initialized on creation. */
    private final LinkedList<Variable> parameters = new LinkedList<>();

    /* the method name */
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
