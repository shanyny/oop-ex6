package oop.ex6.main;

import oop.ex6.blocks.MainBlock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//********DELETE THESE IMPORTS**********
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        LinkedList<String> failed = new LinkedList<>();
        File[] sorted = file.listFiles();
        Arrays.sort(sorted);
        Pattern p1 = Pattern.compile("^test(\\d+)\\.sjava$");

        for (File test : sorted) {
            Matcher m1 = p1.matcher(test.getName());
            m1.matches();
//            int filenameNum = Integer.parseInt(m1.group(1));

            Pattern p2 = Pattern.compile(String.format("^test%s\\.sjava\\s(\\d)\\s(.*)$",m1.group(1)));

            Matcher m2 = p2.matcher(data.get(counter));

            while (!m2.matches() && counter<data.size()-1){
                counter++;
                m2 = p2.matcher(data.get(counter));
            }
//            int fileLineNum = Integer.parseInt(m2.group(1));

//            boolean found = true;

//            while (filenameNum != fileLineNum) {
//                found = false;
//                counter++;
//                if (counter >= data.size()) break;
//                m2 = p2.matcher(data.get(counter));
//                found = m2.matches();
//                if (found) {
//                    fileLineNum = Integer.parseInt(m2.group(1));
//                }
//            }
            if (m2.matches()) {
                System.out.println(test.getName());

                int result = runOnOneFile(test);

//                System.out.println(" test file line catched: "+m1.group(1)+" file name: filenameNum");
//                System.out.println(filenameNum);

                if (Integer.parseInt(m2.group(1)) == result) {
                    System.out.println("pass");
                    System.out.println("Their excuse: "+m2.group(2));

                } else {
                    String message = "fail. expected: "+m2.group(1)+" , actual: " +result;
                    System.out.println(message);
                    System.out.println(m2.group(2));
                    failed.add(test.getName() + "\n"+ message + "\n" + m2.group(2));

                }
                System.out.println();
            }else{
                counter = 0;
            }

        }

        for (int i=0; i<10; i++) System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");


        for (String str : failed) {
            System.out.println(str+"\n");
        }
    }
    public static int runOnOneFile(File test){

            Iterable<String> sJavaScript;
            try {
                sJavaScript = FileReader.getString(test);  // change to file
            } catch (IOException e) {
//                System.out.println(IO_PROBLEM);
                System.out.println(IO_PROBLEM_MESSAGE);
                return 2;
            }

            try {
                new MainBlock(sJavaScript);  // do the actual test
            } catch (IllegalSJavaCodeException e) {
//                System.out.println(CODE_IS_ILLEGAL);
                System.out.println(e.getMessage());
                return 1;
            }

            return 0;
        }
}
