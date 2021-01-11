package oop.ex6.main;

import oop.ex6.blocks.MainBlock;
import java.io.File;
import java.io.IOException;

/**
 * This class is an abstract class with a single method (main), which receives a filename and declares it
 * a valid/invalid SJava script.
 * If the code is valid - prints 0.
 * If the code is illegal - prints 1, then an indicative error message.
 * if there were problems reading the file - prints 2, then an indicative error message.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class Sjavac {

    private static final String CODE_IS_LEGAL = "" + 0;
    private static final String CODE_IS_ILLEGAL = "" + 1;
    private static final String IO_PROBLEM = "" + 2;
    private static final String IO_PROBLEM_MESSAGE = "IOException: Problem reading the file.";

    /**
     * This method receives a filename of a SJava script.
     * If the code is valid - prints 0.
     * If the code is illegal - prints 1, then an indicative error message.
     * if there were problems reading the file - prints 2.
     *
     * @param args - an array where in its first place sits the filename of the script to check.
     */
    public static void main(String[] args) {
        File file = new File(args[0]);
        Iterable<String> sJavaScript;
        try {
            sJavaScript = FileReader.getString(file);
        } catch (IOException e) {
            System.out.println(IO_PROBLEM);
            System.err.println(IO_PROBLEM_MESSAGE);
            return;
        }

        try { new MainBlock(sJavaScript);
        } catch (IllegalSJavaCodeException e) {
            System.out.println(CODE_IS_ILLEGAL);
            System.err.println(e.getMessage());
            return;
        }

        System.out.println(CODE_IS_LEGAL);
    }
}