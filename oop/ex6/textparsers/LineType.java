package oop.ex6.textparsers;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import oop.ex6.variables.exceptions.TypeNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enum represents the different types of lines available.
 * @author Shany Gindi
 */

enum LineType {
    COMMENT(Pattern.compile("^//")),
    EMPTY(Pattern.compile("^$")),
    CONDITION(Pattern.compile("(if|while)\\(.*\\)\\{$")),
    METHOD(Pattern.compile(".*\\(.*\\)\\{$")),
    ONELINER(Pattern.compile(".*;$")),
    METHODCALL(Pattern.compile(".*\\(.*\\)\\;$")),
    CLOSEBLOCK(Pattern.compile("^}$")),
    OPENBLOCK(Pattern.compile("^.*\\{$"));



    /* A pattern indicative of the value available for the type. */
    private final Pattern pattern;

    /**
     * Constructor for the enum.
     * @param p - A pattern indicative of the value available for the type.
     */
    LineType(Pattern p) {
        this.pattern = p;
    }


    boolean isMatching(String line){
        return getMatcher(line).matches();
    }


    Matcher getMatcher(String line){
        return pattern.matcher(line);
    }

    /**
     * Adding all catched groups to a String array and returns the values.
     * @param line
     * @return
     */
    String[] getGroups(String line){
        Matcher matcher = getMatcher(line);
        int num_of_groups = matcher.groupCount();
        String[] groups = new String[num_of_groups];
        for (int i = 0 ;i < num_of_groups;i++){
            groups[i] = matcher.group(i);
        }
        return groups;
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
