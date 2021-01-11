package oop.ex6.textparsers;

import oop.ex6.blocks.*;
import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.LineUnknownFormatException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

import java.util.Iterator;

/**
 * This class is an abstract parser validating each line in a block and
 * checks if it's type is following the sjava restrictions. all methods are static.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class LineParser {


    /**
     * This static method is iterating over all
     * @param block block object of the current scope
     * @param strings iterable of the textual lines in the current block
     * @throws BlockException - general exception regarding block errors
     * @throws VariableException - general exception regarding variable errors
     * @throws OneLinerException - general exception regarding one liner command errors
     */
    public static void parse(Block block, Iterable<String> strings)
            throws BlockException, VariableException, OneLinerException {
        Iterator<String> currIterator = strings.iterator();
        while (currIterator.hasNext()) {
            String currLine = currIterator.next();
            classifyLine(block, currLine, currIterator);
        }
    }

    /**
     * This method is receiving a single line and deciding if its opening a method,
     * a conditional block, a comment, if it's empty or if its a single line by using LineType enum.
     * @param line String of the current line in file
     * @throws LineUnknownFormatException - if the line doesn't match any known form of sjava line
     */
    private static void classifyLine(Block block, String line, Iterator<String> stringIterator)
            throws BlockException, VariableException, OneLinerException {

        for (LineType lineType : LineType.values()) {
            if (lineType.isMatching(line)) {
                lineType.parseLine(block, stringIterator, line);
                return;
            }
        }
        throw new LineUnknownFormatException();
    }

}