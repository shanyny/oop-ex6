package oop.ex6.blocks;

/**
 * Ifs and Whiles
 */
class ConditionalBlock extends Block {

    private static final String NAME_REGEX = "[a-zA-Z][a-zA-Z_0-9]*";
    public static final String CONDITIONAL_EMPTY_REGEX = "^(if|while)\\((%s(\\s?(\\|\\||&&)+\\s?%s\\s?)*)\\)\\{$";
    public final String CONDITIONAL_REGEX = String.format(CONDITIONAL_EMPTY_REGEX,NAME_REGEX,NAME_REGEX);



    public ConditionalBlock(String condition, Iterable<String> strings) {
        super(strings);
    }

    @Override
    public void validate() {

    }
}
