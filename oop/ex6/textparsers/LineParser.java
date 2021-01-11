package oop.ex6.textparsers;

import oop.ex6.blocks.*;
import oop.ex6.blocks.exceptions.BlockException;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

import java.util.Iterator;

/**
 * @author Shany Gindi and Roy Urbach
 */
public abstract class LineParser {


    public static void parse(Block block, Iterable<String> strings) throws BlockException, VariableException, OneLinerException {
        Iterator<String> currIterator = strings.iterator();
        while (currIterator.hasNext()) {
            String currLine = currIterator.next();
            classifyLine(block, currLine, currIterator);
        }
    }

    /**
     * This method is receiving a single line and deciding if its opening a method,
     * a conditional block, a comment, if it's empty or if its a single line Pattern.
     *
     * @param line String of the current line in file
     */
    private static void classifyLine(Block block, String line, Iterator<String> stringIterator) throws BlockException, VariableException, OneLinerException {

        for (LineType lineType : LineType.values()) {
            if (lineType.isMatching(line)) {
                lineType.parseLine(block, stringIterator, line);
                return;
            }
        }
        throw new Block.LineUnknownFormatException();
    }

}