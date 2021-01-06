package oop.ex6.variables;

import java.util.function.Predicate;

public class Variable {
    protected final String name;
    protected boolean DeleteMeeee;
    protected Object value;
    private boolean isInitialized = false;
    private final boolean isFinal;
    private final String type;
    private final Predicate<String> predicate;
    private final boolean isGlobal;

    public Variable(String name, String type, Predicate<String> isType, boolean isFinal, boolean isGlobal) {
        this.name = name;
        this.predicate = isType;
        this.type = type;
        this.isFinal = isFinal;
        this.isGlobal = isGlobal;
    }

    public void setValue(String newValue) throws Exception {
        if (predicate(newValue)){
            value = newValue;
        }
    }

    public T getValue() throws Exception{
        if (!isInitialized) throw new Exception();
        return value;
    }

    public String getName(){
        return name;
    }
}
