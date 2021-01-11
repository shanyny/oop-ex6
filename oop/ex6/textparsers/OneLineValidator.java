package oop.ex6.textparsers;

import oop.ex6.blocks.Block;
import oop.ex6.blocks.exceptions.ConditionParameterNotBooleanException;
import oop.ex6.blocks.MethodBlock;
import oop.ex6.textparsers.exceptions.*;
import oop.ex6.textparsers.exceptions.methodcall.*;
import oop.ex6.textparsers.exceptions.methodcreation.*;
import oop.ex6.variables.*;
import oop.ex6.variables.exceptions.VariableException;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class has a single public method that receives a line from the SJava script and validates it.
 * That is - adds/changes variables or validate method calls.
 * @author Shany Gindi and Roy Urbach
 */
abstract class OneLineValidator {

    /* A regex string representing a simple method name. */
    private final static String METHOD_NAME = "[a-zA-Z]\\w*";

    /* A pattern for a method name. */
    private final static Pattern METHOD_NAME_PATTERN = Pattern.compile(METHOD_NAME);

    /* A regex string representing a simple method call*/
    private final static String METHOD_CALL_FORMAT ="^\\s*(\\w+)\\s*\\(\\s*((?:.*\\S)|.*)?\\s*\\)\\s*;\\s*$";

    /* A pattern for a method call */
    private final static Pattern METHOD_CALL_PATTERN = Pattern.compile(METHOD_CALL_FORMAT);

    /* A regex string representing a simple return that must be found at the end of a method.*/
    public static final String METHOD_RETURN_FORMAT = "^\\s*return\\s*;\\s*$";

    /* A pattern for a return statement */
    private final static Pattern METHOD_RETURN_PATTERN = Pattern.compile(METHOD_RETURN_FORMAT);

    /* A regex for the separator of parameters given to a method*/
    private static final String CALL_METHOD_SEPARATOR = "\\s*,\\s*";

    /* A regex for the separator of parameters given to a method*/
    private static final String CONDITION_SEPARATOR = "\\s*(?:\\|{2}|&{2})\\s*";

    /* An int used for accessing the second line from the end of a block */
    public static final int SECOND_LINE_FROM_THE_END_OF_BLOCK = 2;


    /**
     * This method checks if a single line is alright.
     * If it is a method call - tries to call.
     * If it is a variable initialization or assignment - does it and change the block accordingly.
     * @param scope - the scope to look at and update.
     * @param line - the line to validate.
     * @throws MethodCallException - problem with calling a method.
     * @throws VariableException - problem with initializing or assigning variables.
     */
    public static void validateOneLiner(Block scope, String line)
            throws MethodCallException, VariableException, ReturnOutsideMethodBlockException {
        Matcher methodCallMatcher = METHOD_CALL_PATTERN.matcher(line);
        Matcher methodReturnMatcher = METHOD_RETURN_PATTERN.matcher(line);

        if (methodCallMatcher.find()) {
            if (scope.isGlobal()) throw new MethodCallInMainBlockException();
            MethodBlock methodBlock = scope.getMethod(methodCallMatcher.group(1));
            if (methodBlock == null) throw new CalledUnknownMethodException();
            else checkCallMethod(scope, methodBlock, methodCallMatcher.group(2));
        } else if (methodReturnMatcher.matches() && (scope.isGlobal())){
            throw new ReturnOutsideMethodBlockException();
        }else VariableParser.parseVariableLine(scope, line);

    }

    /**
     * This method checks if a call to a method with given arguments is valid.
     * @param scope - the scope to look at.
     * @param method - the method to check.
     * @param argumentStr - the arguments in one string.
     * @throws MethodCallParametersNotCompatibleException - if the the arguments don't fit the method's parameters.
     * @throws TooLittleArgumentsException - if there are too little arguments.
     * @throws TooManyArgumentsException - if there are too many arguments.
     */
    private static void checkCallMethod(Block scope, MethodBlock method, String argumentStr)
            throws MethodCallParametersNotCompatibleException, TooLittleArgumentsException,
            TooManyArgumentsException {
        LinkedList<Variable> parameters = method.getParameters();
        String[] arguments =  argumentStr.split(CALL_METHOD_SEPARATOR);
        if (parameters == null) {
            if (arguments[0].isEmpty()) return;
            else throw new TooManyArgumentsException();
        }
        int i = 0;
        try {
            for (Variable parameter : parameters) {
                String argument = arguments[i++];
                Variable argVar = scope.getVariable(argument, false);
                if (argVar != null) parameter.setValue(argVar);
                else parameter.setValue(argument);
            }
        } catch (VariableException e) {throw new MethodCallParametersNotCompatibleException();}
        catch (ArrayIndexOutOfBoundsException e) {throw new TooLittleArgumentsException();}
        if (i != arguments.length) throw new TooManyArgumentsException();
    }

    /**
     * This method checks if a method declaration is alright.
     * @param block - the scope to look at and update.
     * @throws MethodCreationException - problem with calling a method.
     */
    public static void validateMethodDeclaration(Block block,
                                                 LinkedList<String> blockLines, String methodName)
            throws MethodCreationException {
        if (!block.isGlobal()) throw new MethodNotInMainBlockException();
        if (!METHOD_NAME_PATTERN.matcher(methodName).matches()) throw new BadMethodNameException();
        if (block.getMethod(methodName) != null) throw new MethodAlreadyExistsException();
        String lastLine = blockLines.get(blockLines.size()- SECOND_LINE_FROM_THE_END_OF_BLOCK);
        if (!METHOD_RETURN_PATTERN.matcher(lastLine).matches()) throw new MethodDoesntEndInReturnException();
    }

    /**
     * This method validates the condition of the ConditionalBlock.
     * @throws ConditionParameterNotBooleanException - if parameters are not boolean.
     */
    public static void validateCondition(Block scope, String condition)
            throws ConditionParameterNotBooleanException {
        try {
            Variable checkBoolean = new Variable("checkBoolean", VariableType.BOOLEAN);
            for (String str : condition.split(CONDITION_SEPARATOR)) {
                Variable variable = scope.getVariable(str, false);
                if (variable == null) checkBoolean.setValue(str);
                else checkBoolean.setValue(variable);
            }
        } catch (VariableException e) {throw new ConditionParameterNotBooleanException();}
    }
}
