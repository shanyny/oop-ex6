package oop.ex6.main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This abstract class has a single method that returns a given file's contents as an Iterable.
 * @author Shany Gindi and Roy Urbach
 */
abstract class FileReader {

    /**
     * This method receives a file, and returns its content as an iterable.
     * @param file - the file to scan.
     * @return an iterable holding the string contents of the file given.
     * @throws IOException - if experiencing a problem while reading the file.
     */
    public static Iterable<String> getString(File file) throws IOException {
        LinkedList<String> sJavaScript = new LinkedList<>();
        try (Scanner f = new Scanner(file)) {
            while (f.hasNextLine()) sJavaScript.addLast(f.nextLine());
            f.close();
            return sJavaScript;
        }
    }
}
