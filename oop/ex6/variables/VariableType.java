package oop.ex6.variables;

import oop.ex6.variables.exceptions.TypeNotFoundException;

import java.util.regex.Pattern;

/**
 * This enum represents the different types available.
 * @author Roy Urbach
 */
enum VariableType {
    STRING("String", Pattern.compile("^\"([^\"]*)\"$")),
    INT("int", Pattern.compile("-?\\d+")),
    DOUBLE("double", Pattern.compile("-?\\d+\\.?\\d*")),
    BOOLEAN("boolean", Pattern.compile("true|false|-?\\d+\\.?\\d*")),
    CHAR("char", Pattern.compile("'.'"));

    /* A string indicative of the type of the variable. */
    private final String typeStr;

    /* A pattern indicative of the value available for the type. */
    private final Pattern p;

    /**
     * Constructor for the enum.
     * @param typeStr - A string indicative of the type of the variable.
     * @param p - A pattern indicative of the value available for the type.
     */
    VariableType(String typeStr, Pattern p) {
        this.typeStr = typeStr;
        this.p = p;
    }

    /**
     * This method returns true if a string indicative of a variable's type belongs to the enum's type.
     * @param str - A string indicative of the type of the variable.
     * @return true if compatible, false if not.
     */
    boolean isType(String str){
        return str.equals(typeStr);
    }

    /**
     * This method receives a string indicative of a variable's type and returns the enum it belongs to.
     * @param str - A string indicative of the type of the variable.
     * @return the VariableType responding to the given string.
     * @throws TypeNotFoundException - if the string is not indicative of any of the available types.
     */
    static VariableType getType(String str) throws TypeNotFoundException {
        for (VariableType variableType : VariableType.values()){
            if (variableType.isType(str)) return variableType;
        } throw new TypeNotFoundException();
    }

    /**
     * This methods returns true if this variable type can change its value to the given variable's type.
     * @param variable - the variable to change the value to.
     * @return true if compatible, false if not.
     */
    boolean canSetTo(Variable variable) {
        VariableType type = variable.getType();
        switch (this) {
            case STRING: return type == STRING;
            case INT: return type == INT;
            case DOUBLE: return type == DOUBLE || type == INT;
            case BOOLEAN: return type == BOOLEAN || type == INT || type == DOUBLE;
            case CHAR: return type == CHAR;
        } return false;
    }

    /**
     * This methods returns true if this variable type can change its value to the given string's value.
     * @param str - the value to change the variable's value to - represented as a string.
     * @return true if compatible, false if not.
     */
    boolean canSetTo(String str) {
        if (str == null) return true;
        else return this.p.matcher(str).matches();
    }
}
