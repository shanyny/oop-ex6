package oop.ex6.textparsers;

import oop.ex6.blocks.*;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    public static final String COMMENT_REGEX = "^//";
    public static final String ONE_LINE_REGEX = ".*;$";
    public static final String EMPTY_LINE = "^$";
    public static final String OPEN_BLOCK = ".*\\{$";
    public static final String CLOSE_BLOCK = "^}$";
    public static final String CONDITIONAL_REGEX = "(if|while)\([a-zA-Z0-9_\s|&]*\){$"

    public final Pattern oneLineOperation = Pattern.compile(ONE_LINE_REGEX);
    public final Pattern commentOperation = Pattern.compile(COMMENT_REGEX);
    public final Pattern methodOperation = Pattern.compile(METHOD_REGEX);
    public final Pattern blockOpenerOperation = Pattern.compile(".*\\{$");
    public final Pattern nameConvention = Pattern.compile(NAME_REGEX);
    public final Pattern emptyLine = Pattern.compile(EMPTY_LINE);
    public final Pattern conditionalOperation = Pattern.compile(CONDITIONAL_REGEX);


    private MethodBlock createMethodBlock(String line) throws BlockException {
        Matcher m = matchRegex(line,methodOperation);
        String methodName = m.group(1);
        String parameters = m.group(2);
        Iterable<String> blockLines = getBlockLines();
        methods.put(methodName,new MethodBlock(parameters,blockLines));
    }
    private ConditionalBlock createConditionBlock(String line) throws BlockException {
        Matcher m = matchRegex(line,conditionalOperation);
        String condition = m.group(1);
        Iterable<String> blockLines = getBlockLines();
        return new ConditionalBlock(condition,blockLines);
    }
    private MethodBlock createOneLiner() throws BlockException {
        Iterable<String> blockLines = getBlockLines();
        return new MethodBlock(line,blockLines);
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
     * a conditional block, a comment, if it's empty or if its a single line operation.
     * @param line String of the current line in file
     */
    private void classifyLine(String line) throws BlockException {

//            Matcher m;
        if (isEmptyLine(line)){
            return;
        }
        else if (isCommentLine(line)){
            return;
        }
        else if (isMethodLine(line)){
            createMethodBlock(line);
        }
        else if (isOneLine(line)){
//                Matcher m = matchRegex(line,oneLineOperation);
            createOneLiner(line);
        }
        else if (isConditionalLine(line)){
            createConditionBlock(line);
        }
        else{
            throw new LineUnknownFormatException();
        }

    }

    private boolean isEmptyLine(String line){
        return isMatchRegex(line,emptyLine);
    }

    private boolean isMethodLine(String line){
        return isMatchRegex(line,methodOperation);
    }

    private boolean isOneLine(String line){
        return isMatchRegex(line,oneLineOperation);
    }

    private boolean isCommentLine(String line){
        return isMatchRegex(line,commentOperation);
    }
    private boolean isConditionalLine(String line){
        return isMatchRegex(line,conditionalOperation);
    }

    private boolean isMethodCall(String line){
        Pattern methodCall = Pattern.compile(METHOD_CALL);
        return isMatchRegex(line,methodCall);
    }


    /**
     * This method is encapsulating a small repeated operation of defining a matcher to the given pattern.
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
     * @return LinkedList of lines in the block
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
}
