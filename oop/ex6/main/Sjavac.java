package oop.ex6.main;

import oop.ex6.blocks.MainBlock;

import java.io.File;
import java.io.IOException;

public class Sjavac {
    public static void main(String[] args) {
        File file = new File(args[0]);
        Iterable<String> strings;
        try {
            strings = FileReader.getString(file);
        } catch (IOException e) {
            System.out.println(2);
            return;
        }

        try {
            MainBlock mainBlock = new MainBlock(strings);
            mainBlock.validate();
        } catch (Exception e) {
            System.err.println(1);
        }

    }
}
