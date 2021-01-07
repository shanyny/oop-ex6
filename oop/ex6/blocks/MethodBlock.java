package oop.ex6.blocks;

class MethodBlock extends Block {

    private static final String NAME_REGEX = "[a-zA-Z][a-zA-Z_0-9]*";
    public static final String PARAMETER_EMPTY_REGEX = "\\((\\s?(final)?\\s%s\\s%s(,\\s?(final)?\\s%s\\s%s)*)\\)";
    private static final String PARAMETERS_REGEX = String.format(PARAMETER_EMPTY_REGEX,NAME_REGEX,NAME_REGEX,NAME_REGEX,NAME_REGEX);
    public static final String METHOD_EMPTY_REGEX = "^void\\s(%s)\\s\\((%s)\\)\\s?{$";
    public static final String METHOD_REGEX = String.format(METHOD_EMPTY_REGEX,NAME_REGEX,PARAMETERS_REGEX);


    public MethodBlock(String declaration, Iterable<String> strings) {
        super(strings);
    }

    @Override
    public void validate() {

    }
}
