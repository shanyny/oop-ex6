package oop.ex6.variables;

import oop.ex6.variables.exceptions.*;

import java.util.regex.Pattern;

/**
 * This class represents a SJava variable, which can be final, global, uninitialized, and holds a value
 * and the name of the variable.
 * The variable types are: int, char, boolean, String and double.
 * @author Shany Gindi and Roy Urbach
 */
public class Variable {

    /* The name of the variable. */
    private String name;

    /* A boolean indicating if the variable was initialized or not. */
    private boolean isInitialized = false;

    /* A boolean indicating if the variable is final or not. */
    private final boolean isFinal;

    /* A boolean indicating if the variable is global or not. */
    private final boolean isGlobal;

    /* A VariableType (enum) type indicating what is the type of the variable. */
    private VariableType type;

    /**
     * Constructor for a variable, indicating whether it is final and global.
     * @param name - the name of the variable.
     * @param type - a string representation of the variable's type.
     * @param value - value to initialize the variable with.
     * @param isFinal - is the variable final.
     * @param isGlobal - is the variable global.
     * @throws TypeNotFoundException - if SJava doesn't support this type.
     * @throws NullPointerException - if the variable name is empty or null.
     */
    public Variable(String name, VariableType type, String value,
                    boolean isFinal, boolean isGlobal) throws TypeNotFoundException, NewValueNotCompatible,
            VariableIsFinal, FinalVariableNotInitialized {
        setVariable(name, type);
        this.isGlobal = isGlobal;
        setValue(value);
        this.isFinal = isFinal;
        if (isFinal && !isInitialized()) throw new FinalVariableNotInitialized();
    }

    /**
     * Constructor for a variable, indicating whether it is final and global.
     * @param name - the name of the variable.
     * @param type - a string representation of the variable's type.
     * @param value - variable to initialize the variable with.
     * @param isFinal - is the variable final.
     * @param isGlobal - is the variable global.
     * @throws TypeNotFoundException - if SJava doesn't support this type.
     * @throws NullPointerException - if the variable name is empty or null.
     */
    public Variable(String name, VariableType type, Variable value,
                    boolean isFinal, boolean isGlobal)
            throws TypeNotFoundException, NewValueNotCompatible,
            VariableIsFinal, VariableNotInitialized, FinalVariableNotInitialized {
        setVariable(name, type);
        this.isGlobal = isGlobal;
        setValue(value);
        this.isFinal = isFinal;
        if (isFinal && !isInitialized()) throw new FinalVariableNotInitialized();
    }

    /**
     * This method sets the variable and acts as a shared setting up method for the constructors.
     * @param name - the name of the variable.
     * @param type - a string representation of the variable's type.
     * @throws TypeNotFoundException - if SJava doesn't support this type.
     */
    private void setVariable(String name, VariableType type) throws TypeNotFoundException {
        this.name = name;
        if (type == null) throw new TypeNotFoundException();
        this.type = type;
    }

    /**
     * This method sets the variable's value to the given variable's value.
     * Because the wanted program doesn't check for runtime errors, it is not actually changing a value,
     * but it is checking if the types are compatible.
     * @param variable - the variable to change the value to.
     * @throws VariableNotInitialized - if the variable to change to is not initialized.
     * @throws VariableIsFinal - if this variable is final (so it cannot be changed).
     * @throws NewValueNotCompatible - if the values types are not compatible.
     */
    public void setValue(Variable variable) throws VariableNotInitialized, VariableIsFinal,
                                                    NewValueNotCompatible {
        if (variable != null) {
            if (isFinal) throw new VariableIsFinal();
            else if (!variable.isInitialized()) throw new VariableNotInitialized();
            else if (type.canSetTo(variable)) initialize();
            else throw new NewValueNotCompatible();
        }
    }

    /**
     * This method sets this variable's value to a given value in string form.
     * Because the wanted program doesn't check for runtime errors, it is not actually changing a value,
     * but it is checking if the types are compatible.
     * @param newValue - the value to change this variable's value to, in string form.
     * @throws VariableIsFinal - if this variable is final (so it cannot be changed).
     * @throws NewValueNotCompatible - if the values types are not compatible.
     */
    public void setValue(String newValue) throws NewValueNotCompatible, VariableIsFinal {
        if (newValue != null) {
            if (isFinal) throw new VariableIsFinal();
            else if (type.canSetTo(newValue)) initialize();
            else throw new NewValueNotCompatible();
        }
    }

    /**
     * This method is called after changing the value of the variable.
     * If the variable is marked as not initialized, it is changing it to initialized.
     */
    private void initialize(){
        if (!isInitialized) isInitialized = true;
    }

    /**
     * This method returns the variable's name.
     * @return this variable's name.
     */
    public String getName(){
        return name;
    }

    /**
     * This method returns this variable's type (VariableType).
     * @return this variable's type.
     */
    public VariableType getType(){
        return type;
    }

    /**
     * @return true if this variable is global, false otherwise.
     */
    public boolean isGlobal(){
        return isGlobal;
    }

    /**
     * @return true if this variable was initialized, false otherwise.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

//    public static void main(String[] args) {
//        String[] goodVariablenames = new String[]{"g2", "b_3", "__", "_a", "____b", "_0", "a_"};
//        String[] badVariablenames = new String[]{"2g", "_", "2__", "54_a", "3_3_3__b"};
//        Variable variable;
//        System.out.println("GOOD");
//        for (String good : goodVariablenames) {
//            System.out.println(NAME_PATTERN.matcher(good).matches());
//        }
//        System.out.println("BAD");
//        for (String bad : badVariablenames) {
//            System.out.println(NAME_PATTERN.matcher(bad).matches());
//        }


//        try {
//            Variable stringVariable = new Variable("stringVar", VariableType.STRING, "\"bloop\"", false, false);
//            Variable intVariable = new Variable("intVar", VariableType.INT, "3", );
//            Variable doubleVariable = new Variable("doubleVar", "double");
//            Variable booleanVariable = new Variable("booleanVar", "boolean");
//            Variable charVariable = new Variable("var", "char");
//
//            String str = null;
//
//            System.out.println("Good Variable Names");
//            for (String name : goodVariablenames) {
//                System.out.println(name);
//                try {
//                    variable = new Variable(name, VariableType.STRING, str, false, false);
//                } catch (IllegalVariableName | TypeNotFoundException e) {
//                    System.out.println(e);
//                }
//            }
//
//            System.out.println("Bad Variable Names");
//            for (String name : badVariablenames) {
//                System.out.println(name);
//                try {
//                    variable = new Variable(name, "String");
//                } catch (IllegalVariableName | TypeNotFoundException e) {
//                    System.out.println(e.toString());
//                }
//            }
//
//            System.out.println("\nint Check!");
//
//            Variable otherIntVariable = new Variable("other", "int");
//            try {
//                otherIntVariable.setValue(intVariable);
//                System.out.println("should raise error");
//            } catch (VariableNotInitialized e) {System.out.println("fine");}
//
//            intVariable.setValue("-3425");
//            try {
//                otherIntVariable.setValue(intVariable);
//                System.out.println("fine");
//            } catch (VariableNotInitialized e) {System.out.println(e.toString());}
//
//            System.out.println("\nDouble Check!");
//
//            Variable otherVariable = new Variable("other", "double");
//            try {
//                otherVariable.setValue(doubleVariable);
//                System.out.println("should raise error");
//            } catch (VariableNotInitialized e) {System.out.println("fine");}
//            for (String doubleStr : new String[]{"3.4", "32153", "1", "-531", "0.53245", "-514.4665", "-352465"}){
//                System.out.println(doubleStr);
//                doubleVariable.setValue(doubleStr);
//                try {
//                    otherVariable.setValue(doubleVariable);
//                } catch (VariableNotInitialized e) {System.out.println(e.toString());}
//            }
//            try {
//                doubleVariable.setValue(intVariable);
//            } catch (NewValueNotCompatible e) {System.out.println("Problem casting int to double");};
//
//
//            System.out.println("\nBoolean Check!");
//            Variable otherBooleanVariable = new Variable("other", "boolean");
//            try {
//                otherBooleanVariable.setValue(booleanVariable);
//                System.out.println("should raise error");
//            } catch (VariableNotInitialized e) {System.out.println("fine");}
//            for (String doubleStr : new String[]{"true", "false", "3.4", "32153", "1", "-531", "0.53245", "-514.4665", "-352465"}){
//                System.out.println(doubleStr);
//                booleanVariable.setValue(doubleStr);
//                try {
//                    otherBooleanVariable.setValue(booleanVariable);
//                } catch (VariableNotInitialized e) {System.out.println(e.toString());}
//            }
//            otherBooleanVariable.setValue(intVariable);
//            otherBooleanVariable.setValue(doubleVariable);
//
//            System.out.println("\nchar Check!");
//            Variable otherCharVariable = new Variable("other", "char");
//            try {
//                otherCharVariable.setValue(charVariable);
//                System.out.println("should raise error");
//            } catch (VariableNotInitialized e) {System.out.println("fine");}
//            for (String doubleStr : new String[]{"'a'", "'b'", "'3'", "'@'", "' '", "'$'"}){
//                System.out.println(doubleStr);
//                charVariable.setValue(doubleStr);
//            }
//            otherCharVariable.setValue(charVariable);
//
//            for (Variable var : new Variable[]{booleanVariable, intVariable, charVariable, doubleVariable}) {
//                if (trySet(stringVariable, var)) System.out.println(stringVariable.getName() + "error in:" + var.getName());
//            }
//            for (String value : new String[]{"_\"sfsdga\"", "\"sfsdga", "sda", "3", "54.2", "\"fdag\" \"gafg\""}) {
//                if (trySet(stringVariable, value)) System.out.println(stringVariable.getName() + "error in:" + value);
//            }
//            for (String value : new String[]{"\"a3.\"", "\"4$\"", "\"5 .2\"", "\"32saf1.2\"", "\"fd%ga\"", "\"asda\"", "\"3\"", "\"false\"", "\"ttrue\""}) {
//                if (!trySet(stringVariable, value)) System.out.println(stringVariable.getName() + "error in:" + value);
//            }
//
//            for (Variable var : new Variable[]{booleanVariable, stringVariable, charVariable, doubleVariable}) {
//                if (trySet(intVariable, var)) System.out.println(intVariable.getName() + "error in:" + var.getName());
//            }
//            for (String value : new String[]{"3.", "321.2", "fdga", "\"asda\"", "\"3\""}) {
//                if (trySet(intVariable, value)) System.out.println(intVariable.getName() + "error in:" + value);
//            }
//
//            for (Variable var : new Variable[]{booleanVariable, stringVariable, charVariable}) {
//                if (trySet(doubleVariable, var)) System.out.println(doubleVariable.getName() + "error in:" + var.getName());
//            }
//            for (String value : new String[]{"a3.", "4$", "\"5.2\"", "32saf1.2", "fd%ga", "\"asda\"", "\"3\""}) {
//                if (trySet(doubleVariable, value)) System.out.println(doubleVariable.getName() + "error in:" + value);
//            }
//
//            for (Variable var : new Variable[]{stringVariable, charVariable}) {
//                if (trySet(booleanVariable, var)) System.out.println(booleanVariable.getName() + "error in:" + var.getName());
//            }
//            for (String value : new String[]{"a3.", "4$", "\"5.2\"", "32saf1.2", "fd%ga", "\"asda\"", "\"3\"", "falsee", "ttrue", " false", "true "}) {
//                if (trySet(booleanVariable, value)) System.out.println(booleanVariable.getName() + "error in:" + value);
//            }
//
//            for (Variable var : new Variable[]{booleanVariable, intVariable, stringVariable, doubleVariable}) {
//                if (trySet(charVariable, var)) System.out.println(charVariable.getName() + "error in:" + var.getName());
//            }
//            for (String value : new String[]{"_\"sfsdga\"", "\"sfsdga", "sda", "3", "54.2", "\"fdag\" \"gafg\""}) {
//                if (trySet(charVariable, value)) System.out.println(charVariable.getName() + "error in:" + value);
//            }
//
//            Variable finalVariable = new Variable("finalVar", "String", "\"value\"", true, true);
//            System.out.println("final test");
//            if ((trySet(finalVariable, stringVariable))) System.out.println("changed final!!!");
//            if (trySet(finalVariable, "\"adgf\"")) System.out.println("changed final!!!");
//
//            Variable finalIntVariable = new Variable("finalIntVar", "int", "3", true, false);
//            if (trySet(finalIntVariable, stringVariable)) System.out.println("changed final!!!");
//            if (trySet(finalIntVariable, "4")) System.out.println("changed final!!!");
//
//
//        } catch (Exception e) {System.out.println(e.toString());}
//    }
//
//    private static boolean trySet(Variable var1, Variable var2) {
//        try {
//            var1.setValue(var2);
//            return true;
//        } catch (NewValueNotCompatible | VariableNotInitialized | VariableIsFinal e) {return false;}
//    }
//
//    private static boolean trySet(Variable var, String value) {
//        try {
//            var.setValue(value);
//            return true;
//        } catch (NewValueNotCompatible | VariableIsFinal e) {return false;}
//    }
}
