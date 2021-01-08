package oop.ex6.textparsers;

import oop.ex6.blocks.Block;
import oop.ex6.blocks.MethodBlock;
import oop.ex6.textparsers.exceptions.*;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableParser;
import oop.ex6.variables.exceptions.VariableException;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class has a single public method that receives a line from the SJava script and validates it.
 * That is - adds/changes variables or validate method calls.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class ValidateOneLiner {

    private final static String METHOD_CALL_FORMAT = "^(\\w+)\\((.*)\\);$";
    private final static Pattern METHOD_CALL_PATTERN = Pattern.compile(METHOD_CALL_FORMAT);

    private static final String SEPARATOR = " *, *";


    /**
     * This method checks if a single line is alright.
     * If it is a method call - tries to call.
     * If it is a variable initialization or assignment - does it and change the block accordingly.
     * @param scope - the scope to look at and update.
     * @param line - the line to validate.
     * @throws MethodCallException - problem with calling a method.
     * @throws VariableException - problem with initializing or assigning variables.
     */
    public static void validate(Block scope, String line) throws MethodCallException, VariableException {
        Matcher methodCallMatcher = METHOD_CALL_PATTERN.matcher(line);
        if (methodCallMatcher.find()) {
            MethodBlock methodBlock = scope.getMethod(methodCallMatcher.group(1));
            if (methodBlock == null) throw new CalledUnknownMethod();
            else checkCallMethod(scope, methodBlock, methodCallMatcher.group(2));
        } else VariableParser.parseVariableLine(scope, line);
    }

    /**
     * This method checks if a call to a method with given arguments is valid.
     * @param scope - the scope to look at.
     * @param method - the method to check.
     * @param argumentStr - the arguments in one string.
     * @throws MethodCallParametersNotCompatible - if the the arguments don't fit the method's parameters.
     * @throws TooLittleArguments - if there are too little arguments.
     * @throws TooManyArguments - if there are too many arguments.
     */
    private static void checkCallMethod(Block scope, MethodBlock method, String argumentStr)
            throws MethodCallParametersNotCompatible, TooLittleArguments, TooManyArguments {
        LinkedList<Variable> parameters = method.getParameters();
        String[] arguments =  argumentStr.split(SEPARATOR);
        int i = 0;
        try {
            for (Variable parameter : parameters) {
                String argument = arguments[i++];
                Variable argVar = scope.getVariable(argument, false);
                if (argVar != null) parameter.setValue(argVar);
                else parameter.setValue(argument);
            }
        } catch (VariableException e) {throw new MethodCallParametersNotCompatible();}
        catch (ArrayIndexOutOfBoundsException e) {throw new TooLittleArguments();}
        if (i != arguments.length) throw new TooManyArguments();
    }
}
