package oop.ex6.variables;

import oop.ex6.variables.exceptions.*;

/**
 * This class represents a SJava variable, which can be final, global, uninitialized, and holds a value
 * and the name of the variable.
 * The variable types are: int, char, boolean, String and double.
 * @author Shany Gindi and Roy Urbach
 */
public class Variable {

    /* A Regex format of variable names. */
    private static final String NAME_FORMAT = "(?:[a-zA-Z]|(?:_\\w))\\w*";

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
     * Simple constructor for a variable, indicating its name and type.
     * @param name - the name of the variable.
     * @param type - the variable's type.
     */
    public Variable(String name, VariableType type)  {
        setVariable(name, type);
        this.isGlobal = false;
        this.isFinal = false;
    }

    /**
     * Constructor for a variable, indicating whether it is final and global.
     * @param name - the name of the variable.
     * @param type - the variable's type.
     * @param value - value to initialize the variable with.
     * @param isFinal - is the variable final.
     * @param isGlobal - is the variable global.
     */
    public Variable(String name, VariableType type, String value,
                    boolean isFinal, boolean isGlobal)
            throws NewValueNotCompatibleException, VariableIsFinalException, FinalVariableNotInitializedException {
        setVariable(name, type);
        this.isGlobal = isGlobal;
        setValue(value);
        this.isFinal = isFinal;
        if (isFinal && !isInitialized()) throw new FinalVariableNotInitializedException();
    }

    /**
     * Constructor for a variable, indicating whether it is final and global.
     * @param name - the name of the variable.
     * @param type - the variable's type.
     * @param value - variable to initialize the variable with.
     * @param isFinal - is the variable final.
     * @param isGlobal - is the variable global.
     */
    public Variable(String name, VariableType type, Variable value,
                    boolean isFinal, boolean isGlobal)
            throws NewValueNotCompatibleException, VariableIsFinalException, VariableNotInitializedException,
            FinalVariableNotInitializedException {

        setVariable(name, type);
        this.isGlobal = isGlobal;
        setValue(value);
        this.isFinal = isFinal;
        if (isFinal && !isInitialized()) throw new FinalVariableNotInitializedException();
    }

    /**
     * This method sets the variable and acts as a shared setting up method for the constructors.
     * @param name - the name of the variable.
     * @param type - a string representation of the variable's type.
     */
    private void setVariable(String name, VariableType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * This method sets the variable's value to the given variable's value.
     * Because the wanted program doesn't check for runtime errors, it is not actually changing a value,
     * but it is checking if the types are compatible.
     * @param variable - the variable to change the value to.
     * @throws VariableNotInitializedException - if the variable to change to is not initialized.
     * @throws VariableIsFinalException - if this variable is final (so it cannot be changed).
     * @throws NewValueNotCompatibleException - if the values types are not compatible.
     */
    public void setValue(Variable variable) throws VariableNotInitializedException, VariableIsFinalException,
            NewValueNotCompatibleException {
        if (variable != null) {
            if (isFinal) throw new VariableIsFinalException();
            else if (!variable.isInitialized()) throw new VariableNotInitializedException();
            else if (getType().canSetTo(variable)) initialize();
            else throw new NewValueNotCompatibleException();
        }
    }

    /**
     * This method sets this variable's value to a given value in string form.
     * Because the wanted program doesn't check for runtime errors, it is not actually changing a value,
     * but it is checking if the types are compatible.
     * @param newValue - the value to change this variable's value to, in string form.
     * @throws VariableIsFinalException - if this variable is final (so it cannot be changed).
     * @throws NewValueNotCompatibleException - if the values types are not compatible.
     */
    public void setValue(String newValue) throws NewValueNotCompatibleException, VariableIsFinalException {
        if (newValue != null) {
            if (isFinal) throw new VariableIsFinalException();
            else if (getType().canSetTo(newValue)) initialize();
            else throw new NewValueNotCompatibleException();
        }
    }

    /**
     * This method is called after changing the value of the variable.
     * If the variable is marked as not initialized, it is changing it to initialized.
     */
    protected void initialize(){
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

    /**
     * This method returns a Regex representation of variables.
     * @return a Regex representation of variables.
     */
    public static String getRegex() {
        return NAME_FORMAT;
    }
}
