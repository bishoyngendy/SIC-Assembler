package operands;

import models.DirectiveLine;
import parser.PatternUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/24/17.
 * This class represents literal direct value. ex: =7
 */
public class LiteralValueOperand extends Literal implements Operand, RegexValidator {

    private int value;

    public LiteralValueOperand(String value) {
        super(value);
        this.value = Integer.parseInt(value);
    }

    @Override
    public String getHexValue() {
        return Integer.toHexString(value);
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.LITERAL_VALUE_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.LITERAL_VALUE_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new LiteralHexadecimalOperand(matcher.group(2));
    }

    @Override
    public DirectiveLine getDirective() {
        String string = "*        =" + value;
        DirectiveLine line = new DirectiveLine(string);
        line.setLabel("*");
        line.setOperation("WORD");
        line.setOperand(new DecimalDirectiveOperand(Integer.toString(value)));
        return line;
    }
}
