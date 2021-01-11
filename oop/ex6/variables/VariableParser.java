package oop.ex6.variables;

import oop.ex6.blocks.*;
import oop.ex6.blocks.exceptions.ParameterNameAlreadyExistsException;
import oop.ex6.variables.exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a abstract class with static methods that parse a line which initializes or sets a variable.
 * It also consists of a method devoted to creating parameter variables for a MethodBlock object.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class VariableParser {

    private static final String FINAL = "final";
    private static final String VALUE_FORMAT = "\\s*((?:[\"'].*[\"'])|\\S+)\\s*";

    private static final String SINGLE_VARIABLE_REGEX =
            String.format("(%s)\\s*(?:=%s)?",Variable.getRegex(), VALUE_FORMAT);
    private static final String FORMAT =
            String.format("^\\s*(%s\\s+)?(%s)\\s+((?:%s,\\s*)*(?:%s))\\s*;\\s*$",
                    FINAL, VariableType.getRegex(), SINGLE_VARIABLE_REGEX, SINGLE_VARIABLE_REGEX);
    private static final String SET_VARIABLE_REGEX =
            String.format("^\\s*(%s)\\s*=%s;\\s*$",  Variable.getRegex(), VALUE_FORMAT);

    private static final Pattern SINGLE_VARIABLE_PATTERN = Pattern.compile(SINGLE_VARIABLE_REGEX);
    private static final Pattern FULL_PATTERN = Pattern.compile(FORMAT);
    private static final Pattern SET_VARIABLE_PATTERN = Pattern.compile(SET_VARIABLE_REGEX);

    private static final String METHOD_PARAMETERS_REGEX =
            String.format("^\\s*(%s)\\s+(%s)\\s*$", VariableType.getRegex(), Variable.getRegex());
    private static final Pattern METHOD_PARAMETERS_PATTERN = Pattern.compile(METHOD_PARAMETERS_REGEX);

    private static final String SEPARATOR = "\\s*,\\s*";

    /**
     * This method receives a Block and a line (string), and updates the block's variable archives,
     * while checking for errors.
     * @param block - the block to update the variables of.
     * @param line - the line to parse.
     * @throws InvalidVariableLineException - if the line is not in format.
     * @throws TypeNotFoundException - if the given type is not valid.
     * @throws NewValueNotCompatibleException - if the value given to a variable is not compatible with its type.
     * @throws VariableIsFinalException - if trying to assign a value to a final variable.
     * @throws VariableDoesntExistException - if trying to assign a variable that doesn't exist to a variable.
     * @throws VariableNotInitializedException - if trying to assign a not-initialized variable to a variable.
     * @throws FinalVariableNotInitializedException - if trying to declare a final variable without initializing it.
     * @throws VariableAlreadyExistsInScopeException - if declaring a variable that already exists in scope.
     */
    public static void parseVariableLine(Block block, String line)
            throws InvalidVariableLineException, TypeNotFoundException, NewValueNotCompatibleException,
            VariableIsFinalException, VariableDoesntExistException, VariableNotInitializedException,
            FinalVariableNotInitializedException, VariableAlreadyExistsInScopeException {

        Matcher initializingMatcher = FULL_PATTERN.matcher(line);
        Matcher setVariableMatcher = SET_VARIABLE_PATTERN.matcher(line);

        // if an initialization line
        if (initializingMatcher.find()) initializeVariablesParser(block, initializingMatcher);

        // if assigning line
        else if (setVariableMatcher.find()) assignVariable(block, setVariableMatcher);

        // else error
        else throw new InvalidVariableLineException();
    }

    /**
     * This method initializes variables and adds them to the given block.
     * @param block - the block to add the variables to.
     * @param matcher - the matcher that holds the variable's strings.
     * @throws TypeNotFoundException - if the given type is not valid.
     * @throws NewValueNotCompatibleException - if the value given to a variable is not compatible with its type.
     * @throws VariableIsFinalException - if trying to assign a value to a final variable.
     * @throws VariableNotInitializedException - if trying to assign a not-initialized variable to a variable.
     * @throws FinalVariableNotInitializedException - if trying to declare a final variable without initializing it.
     * @throws VariableAlreadyExistsInScopeException - if declaring a variable that already exists in scope.
     */
    private static void initializeVariablesParser(Block block, Matcher matcher)
            throws TypeNotFoundException, NewValueNotCompatibleException, VariableIsFinalException,
            VariableNotInitializedException, FinalVariableNotInitializedException, VariableAlreadyExistsInScopeException {
        boolean isFinal = matcher.group(1) != null;  // are the variables declared final
        VariableType variableType = VariableType.getType(matcher.group(2));  // the variables' type.

        String variablesString = matcher.group(3);
        for (String singleVariable : variablesString.split(SEPARATOR, -1)) {  // initialize all variables in the line
            Matcher singleVariableMatcher = SINGLE_VARIABLE_PATTERN.matcher(singleVariable);
            singleVariableMatcher.find();
            String varName = singleVariableMatcher.group(1);  // the name of the variable

            // if the variable already exists in scope and is not global
            Variable variable = block.getVariable(varName, true);
            if (variable != null && !variable.isGlobal()) throw new VariableAlreadyExistsInScopeException();

            String valueStr = singleVariableMatcher.group(2);  // the value of the variable to assign
            createVariable(block, varName, variableType, valueStr, isFinal, false);
        }
    }

    /**
     * This method assigns a new value to a variable.
     * @param block - the block to add the variables to.
     * @param matcher - the matcher that holds the variable's strings.
     * @throws NewValueNotCompatibleException - if the value given to a variable is not compatible with its type.
     * @throws VariableIsFinalException - if trying to assign a value to a final variable.
     * @throws VariableNotInitializedException - if trying to assign a not-initialized variable to a variable.
     * @throws FinalVariableNotInitializedException - if trying to declare a final variable without initializing it.
     * @throws VariableDoesntExistException - if trying to assign a variable that doesn't exist to a variable.
     */
    private static void assignVariable(Block block, Matcher matcher)
            throws VariableDoesntExistException, VariableIsFinalException, NewValueNotCompatibleException, VariableNotInitializedException,
            FinalVariableNotInitializedException {
        String varName = matcher.group(1);

        Variable var = block.getVariable(varName, false);
        if (var == null) throw new VariableDoesntExistException();  // if the variable isn't in any parent scope

        String valueStr = matcher.group(2);

        // if the variable is global and the scope isn't, create local global variable
        if (var.isGlobal() && !block.isGlobal()) {
            createVariable(block, var.getName(), var.getType(), valueStr, false, true);
        }

        // set the value
        Variable valueVar = block.getVariable(valueStr, false);
        if (valueVar != null) var.setValue(valueVar);
        else var.setValue(valueStr);
    }

    /**
     * This method creates a new variable and adds it to the block given.
     * @param block - the block to add the variable to.
     * @param name - the name for the new variable.
     * @param type - the type of the new variable.
     * @param value - the value of the new variable (null if not initialized). Can be a variable's name.
     * @param isFinal - is the variable final.
     * @param isGlobal - if the variable global.
     * @throws NewValueNotCompatibleException - if the value given to a variable is not compatible with its type.
     * @throws VariableIsFinalException - if trying to assign a value to a final variable.
     * @throws FinalVariableNotInitializedException - if trying to declare a final variable without initializing it.
     * @throws VariableNotInitializedException - if trying to assign a not-initialized variable to a variable.
     */
    private static void createVariable(Block block, String name, VariableType type, String value,
                                       boolean isFinal, boolean isGlobal)
            throws NewValueNotCompatibleException, VariableIsFinalException,
            FinalVariableNotInitializedException, VariableNotInitializedException {

        if (value != null) {
            Variable valueVar = block.getVariable(value, false);
            if (valueVar != null) {
                // if the value is a variable, assign it as value for the new variable
                block.addVariable(new Variable(name, type, valueVar, isFinal, isGlobal));
                return;
            }
        }
        // if not, create the new variable with the string given
        block.addVariable(new Variable(name, type, value, isFinal, block.isGlobal()));
    }

    /**
     * This method adds to a method block its parameters based in the given line.
     * @param methodBlock - the MethodBlock to add parameters to.
     * @param line - the line that includes the parameters.
     * @throws IllegalMethodParametersException - if the line is not in format of "VariableType Variable, ..."
     * @throws TypeNotFoundException - if the type is not supported by SJava.
     */
    public static void createMethodParameters(MethodBlock methodBlock, String line)
            throws IllegalMethodParametersException, TypeNotFoundException, ParameterNameAlreadyExistsException {
        if (line.isEmpty()) return;
        for (String str : line.split(SEPARATOR)) {
            Matcher matcher = METHOD_PARAMETERS_PATTERN.matcher(str);
            if (!matcher.find()) throw new IllegalMethodParametersException();
            VariableType variableType = VariableType.getType(matcher.group(1));
            String variableName = matcher.group(2);

            Variable variable = new Variable(variableName, variableType);
            variable.initialize();

            methodBlock.addParameter(variable);
        }
    }
}
