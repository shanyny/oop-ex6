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
    EMPTY(Pattern.compile("^\\s*$")),
    CONDITION(Pattern.compile("(if|while)\\(.*\\)\\{$")){
        boolean parseLine(String line){

        }
    },
    METHOD(Pattern.compile(".*\\(.*\\)\\{$")),
    ONELINER(Pattern.compile(".*;$")),
    CLOSEBLOCK(Pattern.compile("^}$"));




    /* A pattern indicative of the value available for the type. */
    private final Pattern pattern;

    /**
     * Constructor for the enum.
     * @param p - A pattern indicative of the value available for the type.
     */
    LineType(Pattern p) {
        this.pattern = p;
    }

//    abstract boolean isMatching(String line);

    boolean isMatching(String line){
        return getMatcher(line).matches();
    }


    Matcher getMatcher(String line){
        return pattern.matcher(line);
    }

    void parseLine(String line){
        return;
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

}
