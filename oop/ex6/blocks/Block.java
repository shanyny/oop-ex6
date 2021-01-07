package oop.ex6.blocks;
import oop.ex6.variables.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Block {

    protected final HashMap<String, Variable> variables = new HashMap<>();
    protected final HashMap<String, oop.ex6.blocks.MethodBlock> methods = new HashMap<>();
    protected final LinkedList<oop.ex6.blocks.ConditionalBlock> conditionals = new LinkedList<>();
    public final Iterable<String> strings;
    public Iterator<String> currIterator;



    public Block(Iterable<String> strings) {
        this.strings = strings;
    }

    public abstract void validate();

    public boolean isGlobal(){
        return false;
    }



}
