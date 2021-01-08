package oop.ex6.blocks;

import oop.ex6.variables.Variable;

import java.util.LinkedList;

public class MethodBlock extends Block {

    private static final String NAME_REGEX = "[a-zA-Z][a-zA-Z_0-9]*";
    public static final String PARAMETER_EMPTY_REGEX = "\\((\\s?(final)?\\s%s\\s%s(,\\s?(final)?\\s%s\\s%s)*)\\)";
    private static final String PARAMETERS_REGEX = String.format(PARAMETER_EMPTY_REGEX,NAME_REGEX,NAME_REGEX,NAME_REGEX,NAME_REGEX);
    public static final String METHOD_EMPTY_REGEX = "^void\\s(%s)\\s\\((%s)\\)\\s?{$";
    public static final String METHOD_REGEX = String.format(METHOD_EMPTY_REGEX,NAME_REGEX,PARAMETERS_REGEX);
    private final LinkedList<Variable> parameters = new LinkedList<Variable>();


    public MethodBlock(String declaration, Iterable<String> strings) {
        super(strings);
    }

    @Override
    public void validate() {

    }

    /**
     * This method adds a parameter to the parameters linked list.
     */
    public void addParameter(Variable parameter) {
        if (getVariable(parameter.getName(), true) != null) throw new Exception();
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
