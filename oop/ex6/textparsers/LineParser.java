package oop.ex6.textparsers;

import oop.ex6.blocks.*;
import oop.ex6.lines.Line;
import oop.ex6.textparsers.exceptions.MethodCallException;
import oop.ex6.variables.MethodParameterParser;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableParser;
import oop.ex6.variables.exceptions.IllegalMethodParameters;
import oop.ex6.variables.exceptions.TypeNotFoundException;
import oop.ex6.variables.exceptions.VariableException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LineParser {

    private static final String COMMENT_REGEX = "^//";
    private static final String ONE_LINE_REGEX = ".*;$";
    private static final String EMPTY_LINE = "^$";
    private static final String CLOSE_BLOCK = "^}$";
    private static final String OPEN_BLOCK = ".*\\{$";
    private static final String CONDITIONAL_REGEX = "(if|while)\\(.*\\)\\{$";
    private static final String METHOD_REGEX = "(.*)\\(.*\\)\\{$";
    private static final String METHOD_CALL_REGEX = "(.*)\\(.*\\)\\;$";


    public final Pattern oneLinePattern = Pattern.compile(ONE_LINE_REGEX);
    public final Pattern commentPattern = Pattern.compile(COMMENT_REGEX);
    public final Pattern methodPattern = Pattern.compile(METHOD_REGEX);
    public final Pattern emptyPattern = Pattern.compile(EMPTY_LINE);
    public final Pattern conditionalPattern = Pattern.compile(CONDITIONAL_REGEX);
    public final Pattern closeBlockPattern = Pattern.compile(CLOSE_BLOCK);

    protected final HashMap<String, Variable> variables = new HashMap<>();
    protected final HashMap<String, oop.ex6.blocks.MethodBlock> methods = new HashMap<>();
    protected final LinkedList<oop.ex6.blocks.ConditionalBlock> conditionals = new LinkedList<>();
    public final Iterable<String> strings;
    public Iterator<String> currIterator;

    public LineParser(Block block, Iterable<String> strings){
        this.strings = strings;
    }

    private void createMethodBlock(Block block, String line) throws BlockException, IllegalMethodParameters, TypeNotFoundException {
        Matcher m = matchRegex(line,methodPattern);
        String methodName = m.group(1);
        String methodParameters = m.group(2);
        Iterable<String> blockLines = getBlockLines();
        if (block.getMethod(methodName) != null) {throw new MethodAlreadyExists();}
        MethodBlock methodBlock = new MethodBlock(block, blockLines, methodName, line);
        MethodParameterParser.createMethodParameters(methodBlock, methodParameters);
    }

    private void createConditionBlock(Block block, String line) throws BlockException {
        Matcher m = matchRegex(line,conditionalPattern);
        String condition = m.group(1);
        Iterable<String> blockLines = getBlockLines();
        new ConditionalBlock(block, blockLines, condition);
    }

    private void createOneLiner(Block block, String line) throws BlockException, MethodCallException, VariableException {
        ValidateOneLiner.validate(block, line);
    }

    private void iterateOnAllLines(Iterable<String> strings) throws BlockException{
        currIterator = strings.iterator();
        while (currIterator.hasNext()) {
            String currLine = currIterator.next();
            classifyLine(currLine);
        }
    }

    /**
     * This method is receiving a single line and deciding if its opening a method,
     * a conditional block, a comment, if it's empty or if its a single line Pattern.
     * @param line String of the current line in file
     */
    private void classifyLine(Block block, String line) throws BlockException {

        for (LineType lineType : LineType.values()) {
            if (lineType.isMatching(line)) {
                for (SubEnum subenum : lineType.getRelevantEnums(line);
            }
        }


        if (LineType.EMPTYLINE.isMatching(line)){
            return;
        }
        else if (LineType.COMMENTLINE.isMatching(line)){
            return;
        }
        else if (LineType.OPENBLOCKLINE.isMatching(line)){
            if (LineType.METHODLINE.isMatching(line)){
                createMethodBlock(block, line);
            }
            else if (LineType.CONDITIONLINE.isMatching(line)){
                createConditionBlock(block, line);
            }
        else if (LineType.ONELINE.isMatching(line)){
            if (LineType.METHODCALLLINE.isMatching(line)){
                validateMethod(block, line);
            }
            else{
                VariableParser.parseVariableLine(block, line);
            }
        }

        else{
            throw new LineUnknownFormatException();
        }

    }

    private void validateMethod(String line) {
    }

//    private boolean isEmptyLine(String line){
//        return isMatchRegex(line,emptyPatten);
//    }
//
//    private boolean isMethodLine(String line){
//        return isMatchRegex(line,methodPattern);
//    }
//
//    private boolean isOneLine(String line){
//        return isMatchRegex(line,oneLinePattern);
//    }
//
//    private boolean isCommentLine(String line){
//        return isMatchRegex(line,commentPattern);
//    }
//    private boolean isConditionalLine(String line){
//        return isMatchRegex(line,conditionalPattern);
//    }
//    private boolean isMethodCall(String line){
//        Pattern methodCall = Pattern.compile(METHOD_CALL_REGEX);
//        return isMatchRegex(line,methodCall);
//    }


    /**
     * This method is encapsulating a small repeated Pattern of defining a matcher to the given pattern.
     * performs matches and returns result.
     * @param line the string to match regex on
     * @param pattern the pattern to match by
     * @return true if matches, false if not
     */
    private Matcher matchRegex(String line,Pattern pattern){
        Matcher currLine = pattern.matcher(line);
        return currLine;
    }

    private boolean isMatchRegex(String line,Pattern pattern){
        return matchRegex(line,pattern).matches();
    }

    /**
     * This method is validating a block structure and returns it's content
     * @param currIterator line iterator to run on
     * @return LinkedList of oop.ex6.lines in the block
     * @throws BlockBracketsException
     */
    private LinkedList<String> getBlockLines() throws BlockException{
        Pattern openBlock = Pattern.compile(OPEN_BLOCK);
        Pattern closeBlock = Pattern.compile(CLOSE_BLOCK);

//            Iterator<String> currIterator = strings.iterator();
        LinkedList<String> blockStrings = new LinkedList<>();
        int bracketCounter = 1;
        String currLine;
        while(currIterator.hasNext() && bracketCounter > 0){
            currLine = currIterator.next();
            blockStrings.add(currLine);
            if(isMatchRegex(currLine,openBlock)){
                bracketCounter++;
            }
            else if(isMatchRegex(currLine,closeBlock)){
                bracketCounter--;
            }
        }
        if(bracketCounter>0){
            throw new BlockBracketsException();
        }
        return blockStrings;
    }

    private void validateOneLiner(){

    }
}
