package parser;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 * This is a utility class that is used by the RegexHelper
 */
public class PatternUtilities {
    /**.
     * A private constructor as this is a static utility class
     */
    private PatternUtilities() {}

    private static String VALID_LABEL = "\\A(([a-zA-Z][a-zA-Z0-9]{0,7}| {0,8}) +)";
    private static String VALID_OPERATION = "(([a-zA-Z]{1,6}) *)";
    private static String BASIC_OPERAND = "(.{0,18} *)";
    private static String VALID_COMMENT = "([\\.\\*](.{1,31}))?";

    static String VALID_LINE = VALID_LABEL
            + VALID_OPERATION
            + BASIC_OPERAND
            + VALID_COMMENT;

    static String IS_DIRECTIVE = "(?i)(START|END|RESW|RESB|BYTE|WORD|ORG|EQU|LTORG)";
    static String IS_COMMENT = "^(\\.|\\*).*";

    public static String OPERAND_WITH_INDEXING = "^(?i)(([a-zA-Z]+[a-zA-Z0-9]*)\\s*,\\s*x)$";
    public static String OPERAND = "^([a-zA-Z]+[a-zA-Z0-9]*)$";
    public static String VALID_HEXADECIMAL = "([a-fA-F0-9]+)";
    public static String LITERAL_HEXADECIMAL_OPERAND = "^(?i)(=x['’]" + VALID_HEXADECIMAL + "['’] *)$";
    public static String LITERAL_CHARACTERS_OPERAND = "^(?i)(=c['’]([a-zA-Z]+)['’] *)$";
    public static String LITERAL_VALUE_OPERAND = "^(?i)(=([0-9]+ *))$";
    public static String BYTE_CHARACTER_OPERAND = "^(?i)(c['’]([a-zA-Z0-9 ]+)['’] *)$";
    public static String BYTE_HEXADECIMAL_OPERAND = "^(?i)(x['’]" + VALID_HEXADECIMAL +"['’] *)$";
    public static String ADDRESS_OPERAND = "^(?i)0x(" + VALID_HEXADECIMAL +" *)$";
    public static String ADDRESS_OPERAND_WITH_INDEX = "^(?i)0x(" + VALID_HEXADECIMAL +"\\s?,x *)$";

//    static int LABEL_START_INDEX = 0;
//    static int LABEL_LAST_INDEX = 7;
//    static int OPERATION_START_INDEX = 9;
//    static int OPERATION_END_INDEX = 14;
//    static int OPERAND_START_INDEX = 17;
//    static int OPERAND_END_INDEX = 34;
//    static int COMMENT_START_INDEX = 35;
//    static int COMMENT_END_INDEX = 65;

    static int LABEL_GROUP = 2;
    static int LABEL_GROUP_WITH_SPACE = 1;
    static int OPERATION_GROUP = 4;
    static int OPERATION_GROUP_WITH_SPACE = 3;
    static int OPERAND_GROUP = 5;
    static int COMMENT_GROUP = 7;
}
