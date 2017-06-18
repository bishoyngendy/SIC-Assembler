package operands;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import models.DirectiveLine;
import parser.PatternUtilities;
import tables.LiteralTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/24/17.
 * This class represents literal hexadecimal. ex: =X'0FA'
 */
public class LiteralHexadecimalOperand extends Literal implements Operand, RegexValidator {

    private String hexadecimal;
    private LiteralTable literalTable;
    private ByteHexadecimalOperand byteHexadecimalOperand;

    public LiteralHexadecimalOperand(String hexadecimal) {
        super(hexadecimal);
        this.hexadecimal = hexadecimal;
        this.literalTable = GlobalDataSingleton.getInstance().getLiteralTable();
        this.byteHexadecimalOperand = new ByteHexadecimalOperand(hexadecimal);
    }

    @Override
    public String getHexValue() throws InvalidFormatException {
        return byteHexadecimalOperand.getInstructionCode("");
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.LITERAL_HEXADECIMAL_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.LITERAL_HEXADECIMAL_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new LiteralHexadecimalOperand(matcher.group(2));
    }

    @Override
    public DirectiveLine getDirective() {
        String string = "*        =X'" + byteHexadecimalOperand.getValue() + "'";
        DirectiveLine line = new DirectiveLine(string);
        line.setLabel("*");
        line.setOperation("BYTE");
        line.setOperand(byteHexadecimalOperand);
        return line;
    }
}
