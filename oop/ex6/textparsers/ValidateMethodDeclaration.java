package oop.ex6.textparsers;

import oop.ex6.blocks.Block;
import oop.ex6.blocks.MethodBlock;
import oop.ex6.textparsers.exceptions.*;
import oop.ex6.variables.MethodParameterParser;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableParser;
import oop.ex6.variables.exceptions.VariableException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class has a single public method that receives a line from the SJava script and validates it.
 * That is - adds/changes variables or validate method calls.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class ValidateMethodDeclaration {

    /**
     * This method checks if a method declaration is alright.
     * @param scope - the scope to look at and update.
     * @param line - the line to validate.
     * @throws MethodCallException - problem with calling a method.
     * @throws VariableException - problem with initializing or assigning variables.
     */
    public static void validate(Block block, LinkedList<String> blockLines, String methodName,String methodParameters, String line) throws MethodCreationException, VariableException {
        if (!block.isGlobal()){throw new MethodNotInMainBlockException();}
        if (block.getMethod(methodName) != null) {throw new MethodAlreadyExistsException();}
        if (!blockLines.get(blockLines.size()-2).equals("return;")){throw new MethodDoesntEndInReturnException();}
        MethodBlock methodBlock = new MethodBlock(block, blockLines, methodName, line);
        block.addMethod(methodBlock);
        MethodParameterParser.createMethodParameters(methodBlock, methodParameters);
    }
}
