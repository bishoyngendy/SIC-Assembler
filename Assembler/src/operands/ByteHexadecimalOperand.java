package operands;

import com.sun.media.sound.InvalidFormatException;
import parser.PatternUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public class ByteHexadecimalOperand implements Operand, RegexValidator {

    private String hexadecimal;

    public ByteHexadecimalOperand(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(hexadecimal.length() <= 14) {
            return hexadecimal.toUpperCase();
        } else {
            throw new InvalidFormatException("Maximum of 14 Hexadecimal digits");
        }
    }

    @Override
    public String getValue() {
        return hexadecimal;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.BYTE_HEXADECIMAL_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.BYTE_HEXADECIMAL_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new ByteHexadecimalOperand(matcher.group(2));
    }
}
