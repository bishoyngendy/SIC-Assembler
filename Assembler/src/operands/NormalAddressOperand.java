package operands;

import com.sun.media.sound.InvalidFormatException;
import parser.PatternUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 05/05/2017.
 */
public class NormalAddressOperand implements Operand, RegexValidator {

    private String hexadecimal;

    public NormalAddressOperand(String hexadecimal)  {
        this.hexadecimal = hexadecimal;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(Integer.valueOf(hexadecimal, 16) > Math.pow(2, 15)) {
            throw new InvalidFormatException("Invalid address in SIC Machine !!");
        }
        return (operationCodeInHexadecimal +
                ("0000" + hexadecimal).substring(hexadecimal.length())).toUpperCase();
    }

    @Override
    public String getValue() {
        return hexadecimal;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.ADDRESS_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) throws InvalidFormatException {
        Pattern pattern = Pattern.compile(PatternUtilities.ADDRESS_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new NormalAddressOperand(matcher.group(2));
    }
}
