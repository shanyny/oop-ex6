package oop.ex6.variables;

import oop.ex6.blocks.*;
import oop.ex6.variables.exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableParser {

    private static final String FINAL = "final";

    private static final String VALUE_FORMAT = "[\\w\"'\\-\\. ]+";
    private static final String NAME_FORMAT = "[a-zA-Z|_][\\w]*";
    private static final String FORMAT =
            String.format("^(%s +)?([a-zA-Z]+) +((?:%s *(?:=? *%s)?, *)*" +
                    "(?:%s *(?:=? *%s)?)) *;$", FINAL, NAME_FORMAT, VALUE_FORMAT, NAME_FORMAT, VALUE_FORMAT);
    private static final Pattern PATTERN = Pattern.compile(FORMAT);


    private static final String SINGLE_VARIABLE_REGEX =
            String.format("(%s) *(?:= *(%s))?",NAME_FORMAT, VALUE_FORMAT);

    private static final Pattern SINGLE_VARIABLE_PATTERN =
            Pattern.compile(SINGLE_VARIABLE_REGEX);

    private static final String SET_VARIABLE_REGEX =
            String.format("^ *(%s) *= *(%s) *;$",  NAME_FORMAT, VALUE_FORMAT);
    private static final Pattern SET_VARIABLE_PATTERN = Pattern.compile(SET_VARIABLE_REGEX);

    public static void parseVariableLine(Block block, String line)
            throws InvalidVariableInitialization, TypeNotFoundException, NewValueNotCompatible,
            IllegalVariableName, VariableIsFinal, VariableDoesntExist, VariableNotInitialized,
            FinalVariableNotInitialized, VariableAlreadyInitialized {
        Matcher initializingMatcher = PATTERN.matcher(line);
        Matcher setVariableMatcher = SET_VARIABLE_PATTERN.matcher(line);
        if (initializingMatcher.find()) initializeVariablesParser(block, initializingMatcher);
        else if (setVariableMatcher.find()) setVariable(block, setVariableMatcher);
        else throw new InvalidVariableInitialization();
    }

    private static void initializeVariablesParser(Block block, Matcher matcher)
            throws TypeNotFoundException, NewValueNotCompatible, VariableIsFinal,
            IllegalVariableName, VariableNotInitialized, FinalVariableNotInitialized, VariableAlreadyInitialized {
        boolean isFinal = matcher.group(1) != null;
        VariableType variableType = VariableType.getType(matcher.group(2));
        String str = matcher.group(3);
        Matcher singleVariableMatcher = SINGLE_VARIABLE_PATTERN.matcher(str);
        while (singleVariableMatcher.find()) {
            String varStr = singleVariableMatcher.group(1);
            if (block.getVariable(varStr, true) != null) throw new VariableAlreadyInitialized();
            String valueStr = singleVariableMatcher.group(2);
            Variable valueVar = block.getVariable(valueStr, false);
            if (valueVar == null) createVariable(block, varStr, variableType, valueStr, isFinal);
            else createVariable(block, varStr, variableType, valueVar, isFinal);
        }
    }

    private static void setVariable(Block block, Matcher matcher)
            throws VariableDoesntExist, VariableIsFinal, NewValueNotCompatible, VariableNotInitialized,
            IllegalVariableName, TypeNotFoundException, FinalVariableNotInitialized {
        String varStr = matcher.group(1);
        String valueStr = matcher.group(2);
        Variable var = block.getVariable(varStr, false);
        Variable valVar = block.getVariable(valueStr, false) ;
        if (var == null) throw new VariableDoesntExist();
        if (var.isGlobal() && !block.isGlobal() && !var.isInitialized()) {
            if (valVar != null) createVariable(block, var.getName(), var.getType(), valVar, false);
            else createVariable(block, var.getName(), var.getType(), valueStr, false);
        } else if (valVar != null) var.setValue(valVar);
        else var.setValue(valueStr);
    }

    private static void createVariable(Block block, String name, VariableType type, Variable value,
                                       boolean isFinal)
            throws NewValueNotCompatible, IllegalVariableName, VariableIsFinal, TypeNotFoundException,
            VariableNotInitialized, FinalVariableNotInitialized {
        block.addVariable(new Variable(name, type, value,isFinal, block.isGlobal()));
    }

    private static void createVariable(Block block, String name, VariableType type, String value, boolean isFinal)
            throws NewValueNotCompatible, IllegalVariableName, VariableIsFinal,
            TypeNotFoundException, FinalVariableNotInitialized {
        block.addVariable(new Variable(name, type, value,isFinal, block.isGlobal()));
    }
}
