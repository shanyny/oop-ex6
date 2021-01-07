package oop.ex6.blocks;

public class MainBlock extends Block {

    public MainBlock(Iterable<String> strings){
        super(strings);
    }

    @Override
    public void validate() {

    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
