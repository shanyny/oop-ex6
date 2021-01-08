package oop.ex6.variables;

import oop.ex6.variables.exceptions.NewValueNotCompatible;

import java.util.LinkedList;

/**
 * This class represents method parameters, which are not final, global or initialized.
 * @author Shany Gindi and Roy Urbach
 */
public abstract class MethodParameterParser {

    /**
     * Constructor for
     * @param name
     * @param type
     */
    public static void createMethodParameters(MethodBlock methodBlock, String line) {

        for (String str : line) {
            // manipulation
            methodBlock.addParameter(new Variable());
        }

    }
}
