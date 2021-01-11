package oop.ex6.textparsers;

import oop.ex6.blocks.*;
import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.*;
import oop.ex6.variables.VariableParser;
import oop.ex6.variables.exceptions.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enum represents the different types of lines available.
 * @author Shany Gindi and Roy Urbach
 */
enum LineType {

    COMMENT(Pattern.compile("^/{2}.*$")),
    EMPTY(Pattern.compile("^\\s*$")),

    CONDITION(Pattern.compile("^\\s*(?:if|while)\\s*\\(\\s*(.*\\S)\\s*\\)\\s*\\{\\s*$")){
        /** This method is validating a condition using OneLineValidator and creates a ConditionalBlock Object. */
        @Override
        void parseLine(Block block, Iterator<String> strings,String line)
                throws BlockException, VariableException, OneLinerException {
            if (block.isGlobal()){throw new ConditionInMainBlockException();}
            Matcher matcher = getMatcher(line);
            matcher.matches();
            String condition = matcher.group(1);
            LinkedList<String> blockLines = LineParser.getBlockLines(strings);
            OneLineValidator.validateCondition(block, condition);
            new ConditionalBlock(block, blockLines);
        }
    },
    METHOD(Pattern.compile("^\\s*void\\s*(\\S+)\\s*\\((.*)\\)\\s*\\{\\s*$")){
        /** This method is validating a block using OneLineValidator and creates a MethodBlock Object. */
        @Override
        void parseLine(Block block, Iterator<String> strings,String line)
                throws VariableException, BlockException,OneLinerException {
            Matcher m = getMatcher(line);
            m.matches();
            String methodName = m.group(1);
            String methodParameters = m.group(2);
            LinkedList<String> blockLines = LineParser.getBlockLines(strings);

            OneLineValidator.validateMethodDeclaration(block, blockLines, methodName);

            MethodBlock methodBlock = new MethodBlock(block, blockLines, methodName);
            block.addMethod(methodBlock);
            VariableParser.createMethodParameters(methodBlock, methodParameters);  // add parameters to method
        }
    },
    ONELINER(Pattern.compile("^(?!(?:/|\\s*return)).*\\s*;\\s*$")){
        /** This method is validating a line of code using OneLineValidator.*/
        @Override
        void parseLine(Block block, Iterator<String> strings,String line)
                throws OneLinerException, VariableException {
            OneLineValidator.validateOneLiner(block,line);
        }
    },
    CLOSEBLOCK(Pattern.compile("^\\s*}\\s*$")){
        /** This method is validating a close block line is not in a global scope.*/
        @Override
        void parseLine(Block block, Iterator<String> strings,String line) throws OneLinerException {
            if (block.isGlobal()) throw new CloseBracketOutsideBlockException();
        }
    },
    RETURN(Pattern.compile("^\\s*return\\s*;\\s*$")){
        /** This method is validating a return statement line is not in a global scope.*/
        @Override
        void parseLine(Block block, Iterator<String> strings,String line) throws OneLinerException {
            if (block.isGlobal()) throw new ReturnOutsideMethodBlockException();
        }
    };

    /* A pattern indicative of the value available for the type. */
    private final Pattern pattern;


    /**
     * Constructor for the enum.
     * @param p - A pattern indicative of the value available for the type.
     */
    LineType(Pattern p) {
        this.pattern = p;
    }

    /**
     * @param line string to create matcher with
     * @return true if matching regex, false otherwise.
     */
    boolean isMatching(String line){
        return getMatcher(line).matches();
    }


    /**
     * @param line string to create matcher with
     * @return the matcher of the pattern.
     */
    Matcher getMatcher(String line){
        return pattern.matcher(line);
    }

    /**
     * This method is overridden for some enums. the general case ignores a valid line.
     * Other cases uses OneLineValidator to validate the format of the line and creates an object if needed.
     * @param block the parent block this line belongs to
     * @param strings the LineParser Iterator of this block
     * @param line string to be currently parsed
     * @throws BlockException - general exception regarding block errors
     * @throws VariableException - general exception regarding variable errors
     * @throws OneLinerException - general exception regarding one liner command errors
     */
    void parseLine(Block block, Iterator<String> strings,String line)
            throws BlockException, VariableException, OneLinerException {
    }

}
