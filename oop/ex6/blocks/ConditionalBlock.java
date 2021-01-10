package oop.ex6.blocks;

import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableType;
import oop.ex6.variables.exceptions.VariableException;

/**
 * Ifs and Whiles
 */
public class ConditionalBlock extends Block {

    private static final String NAME_REGEX = "[a-zA-Z][a-zA-Z_0-9]*";
    public static final String CONDITIONAL_EMPTY_REGEX = "^(if|while)\\((%s(\\s?(\\|\\||&&)+\\s?%s\\s?)*)\\){$";
    public final String CONDITIONAL_REGEX = String.format(CONDITIONAL_EMPTY_REGEX,NAME_REGEX,NAME_REGEX);
    private final String SEPARATOR = " *\\|{2}|$$ *";
    private final String condition;


    public ConditionalBlock(Block parent, Iterable<String> strings, String condition) {
        super(parent, strings);
        this.condition = condition;
    }

    @Override
    public void validate() throws ConditionParameterNotBoolean {
        validateCondition();
        super.validate();
    }

    /**
     * This method validates the condition of the ConditionalBlock.
     */
    private void validateCondition() throws ConditionParameterNotBoolean {
        try {
            Variable checkBoolean = new Variable("checkBoolean", VariableType.BOOLEAN);
            for (String str : condition.split(SEPARATOR)) {
                Variable variable = getVariable(str, false);
                if (variable == null) checkBoolean.setValue(str);
                else checkBoolean.setValue(variable);
            }
        } catch (VariableException e) {throw new ConditionParameterNotBoolean();}
    }
}
