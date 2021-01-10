package oop.ex6.textparsers;

import oop.ex6.blocks.*;
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
    COMMENT(Pattern.compile("^//")),
    EMPTY(Pattern.compile("^\\s*$")),
    CONDITION(Pattern.compile("^(?:if|while)\\((.*)\\)\\{\\s*$")){
        @Override
        void parseLine(Block block, Iterator<String> strings,String line) throws BlockException, VariableException, OneLinerException {
            if (block.isGlobal()){throw new ConditionInMainBlockException();}
            String condition = getMatcher(line).group(1);
            LinkedList<String> blockLines = getBlockLines(strings);
            OneLineValidator.validateCondition(block, condition);
            new ConditionalBlock(block, blockLines);
        }
    },
    METHOD(Pattern.compile("^void\\s(.*)\\((.*)\\)\\{\\s*$")){
        @Override
        void parseLine(Block block, Iterator<String> strings,String line) throws VariableException, BlockException,OneLinerException {
            Matcher m = getMatcher(line);
            m.matches();
            String methodName = m.group(1);
            String methodParameters = m.group(2);
            LinkedList<String> blockLines = getBlockLines(strings);

            OneLineValidator.validateMethodDeclaration(block, blockLines, methodName);

            MethodBlock methodBlock = new MethodBlock(block, blockLines, methodName);
            block.addMethod(methodBlock);
            VariableParser.createMethodParameters(methodBlock, methodParameters);  // add parameters to method
        }
    },
    ONELINER(Pattern.compile(".*;\\s*$")){
        @Override
        void parseLine(Block block, Iterator<String> strings,String line) throws OneLinerException, VariableException {
            OneLineValidator.validateOneLiner(block,line);
        }
    },
    CLOSEBLOCK(Pattern.compile("^\\s*}\\s*$"));
//    RETURN(Pattern.compile("^\\s*return\\s*;\\s*$"));


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
     * @param block the parent block this line belongs to
     * @param strings the LineParser Iterator of this block
     * @param line string to be currently parsed
     * @throws BlockException
     * @throws VariableException
     * @throws OneLinerException
     */
    void parseLine(Block block, Iterator<String> strings,String line) throws BlockException, VariableException, OneLinerException {
    }


    /**
     * This method is validating a block structure and returns it's content
     * @param currIterator line iterator to run on
     * @return LinkedList of oop.ex6.lines in the block
     * @throws BlockBracketsException
     */
    static LinkedList<String> getBlockLines(Iterator<String> currIterator) throws BlockException {
        LinkedList<String> blockStrings = new LinkedList<>();
        int bracketCounter = 1;
        String currLine;
        while(currIterator.hasNext() && bracketCounter > 0){
            currLine = currIterator.next();
            blockStrings.add(currLine);
            if(currLine.endsWith("{")){
                bracketCounter++;
            }
            else if(CLOSEBLOCK.isMatching(currLine)){
                bracketCounter--;
            }
        }
        if(bracketCounter>0){
            throw new BlockBracketsException();
        }
        return blockStrings;
    }
}
