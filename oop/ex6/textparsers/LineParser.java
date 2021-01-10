package oop.ex6.textparsers;

import oop.ex6.blocks.*;
import oop.ex6.textparsers.exceptions.OneLinerException;
import oop.ex6.variables.exceptions.VariableException;

import java.util.Iterator;

/**
 * @author Shany Gindi and Roy Urbach
 */
public class LineParser {

    private final Iterable<String> strings;
    private Iterator<String> currIterator;
    private Block block;

    public LineParser(Block block, Iterable<String> strings) {
        this.strings = strings;
        this.block = block;
    }

    public void parse() throws BlockException, VariableException, OneLinerException {
        currIterator = strings.iterator();
        while (currIterator.hasNext()) {
            String currLine = currIterator.next();
            classifyLine(currLine);
        }
    }

    /**
     * This method is receiving a single line and deciding if its opening a method,
     * a conditional block, a comment, if it's empty or if its a single line Pattern.
     *
     * @param line String of the current line in file
     */
    private void classifyLine(String line) throws BlockException, VariableException, OneLinerException {

        for (LineType lineType : LineType.values()) {
            if (lineType.isMatching(line)) {
                lineType.parseLine(block, currIterator, line);
            }
        }
        throw new LineUnknownFormatException();
    }

}