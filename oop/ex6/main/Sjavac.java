package oop.ex6.main;

import oop.ex6.blocks.MainBlock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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

//    /**
//     * This method receives a filename of a SJava script.
//     * If the code is valid - prints 0.
//     * If the code is illegal - prints 1, then an indicative error message.
//     * if there were problems reading the file - prints 2.
//     * @param args - an array where in its first place sits the filename of the script to check.
//     */
//    public static void main(String[] args) {
//        File file = new File(args[0]);
//        Iterable<String> sJavaScript;
//        try {sJavaScript = FileReader.getString(file);}
//        catch (IOException e) {
//            System.out.println(IO_PROBLEM);
//            System.err.println(IO_PROBLEM_MESSAGE);
//            return;
//        }
//
//        try {
//            MainBlock mainBlock = new MainBlock(sJavaScript);
//        } catch (IllegalSJavaCode e) {
//            System.out.println(CODE_IS_ILLEGAL);
//            System.err.println(e.getMessage());
//            return;
//        }
//
//        System.out.println(CODE_IS_LEGAL);
//    }

    /**
     * This method receives a filename of a SJava script.
     * If the code is valid - prints 0.
     * If the code is illegal - prints 1, then an indicative error message.
     * if there were problems reading the file - prints 2.
     * @param args - an array where in its first place sits the filename of the script to check.
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        File testResults = new File(args[1]);
        Scanner scanner = new Scanner(testResults);
        LinkedList<String> data = new LinkedList<>();
        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }
        int counter = 0;
        for (File test : file.listFiles()) {
            String result = runOnOneFile(test);
            Pattern p1 = Pattern.compile("test(\\d+)\\.sjava");
            Pattern p2 = Pattern.compile("test(\\d+)\\.sjava\\s(\\d)\\s.*");

            Matcher m2 = p2.matcher(data.get(counter));
            Matcher m1 = p1.matcher(test.getName());
            m1.matches();
            m2.matches();
            int filenameNum = Integer.parseInt(m1.group(1));
            int fileLineNum = Integer.parseInt(m2.group(1));
            while (filenameNum != fileLineNum) {
                counter++;
            }

            if (m2.group(2) == result) {
                System.out.println("pass");
            } else {
                System.out.println("fail");
            }

        }
    }
    public static String runOnOneFile(File test){

            Iterable<String> sJavaScript;
            try {
                sJavaScript = FileReader.getString(test);  // change to file
            } catch (IOException e) {
                System.out.println(IO_PROBLEM);
                System.out.println(IO_PROBLEM_MESSAGE);
                return IO_PROBLEM;
            }

            try {
                new MainBlock(sJavaScript);  // do the actual test
            } catch (IllegalSJavaCode e) {
                System.out.println(CODE_IS_ILLEGAL);
                System.out.println(e.getMessage());
                return CODE_IS_ILLEGAL;
            }

            return CODE_IS_LEGAL;
        }
}
