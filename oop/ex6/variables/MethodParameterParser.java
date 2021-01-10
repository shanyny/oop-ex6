package oop.ex6.variables;

import oop.ex6.blocks.MethodBlock;
import oop.ex6.variables.exceptions.IllegalMethodParameters;
import oop.ex6.variables.exceptions.TypeNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents method parameters, which are not final, global or initialized.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class MethodParameterParser {

    private static final String PARAMETERS_REGEX =
            String.format("^(%s) +(%s)$", VariableType.getRegex(), Variable.getRegex());
    private static final Pattern PARAMETERS_PATTERN = Pattern.compile(PARAMETERS_REGEX);

    private static final String SEPARATOR = " *, *";


    /**
     * This method adds to a method block its parameters based in the given line.
     * @param methodBlock - the MethodBlock to add parameters to.
     * @param line - the line that includes the parameters.
     * @throws IllegalMethodParameters - if the line is not in format of "VariableType Variable, ..."
     * @throws TypeNotFoundException - if the type is not supported by SJava.
     */
    public static void createMethodParameters(MethodBlock methodBlock, String line)
            throws IllegalMethodParameters, TypeNotFoundException {

        for (String str : line.split(SEPARATOR)) {
            Matcher matcher = PARAMETERS_PATTERN.matcher(str);
            if (!matcher.find()) throw new IllegalMethodParameters();
            String variableName = matcher.group(1);
            VariableType variableType = VariableType.getType(matcher.group(2));

            Variable variable = new Variable(variableName, variableType);
            variable.initialize();

            methodBlock.addParameter(variable);
        }
    }

}
