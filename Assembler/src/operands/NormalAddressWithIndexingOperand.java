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
public class NormalAddressWithIndexingOperand implements Operand, RegexValidator {

    private String hexadecimal;

    public NormalAddressWithIndexingOperand(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(Integer.valueOf(hexadecimal, 16) > Math.pow(2, 15)) {
            throw new InvalidFormatException("Invalid address in SIC Machine !!");
        }
        String formatted = ("0000" + hexadecimal).substring(hexadecimal.length());
        return (operationCodeInHexadecimal + addIndexBit(formatted)).toUpperCase();
    }

    private String addIndexBit(String operationCodeInHexadecimal) {
        Integer temp = Integer.parseInt(operationCodeInHexadecimal, 16);
        temp += (int) Math.pow(2, 15);
        String hex = Integer.toHexString(temp);
        return ("0000" + hex).substring(hex.length());
    }

    @Override
    public String getValue() {
        return hexadecimal;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.ADDRESS_OPERAND_WITH_INDEX);

    }

    @Override
    public Operand getNewInstance(String operand) throws InvalidFormatException {
        Pattern pattern = Pattern.compile(PatternUtilities.ADDRESS_OPERAND_WITH_INDEX);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new NormalAddressOperand(matcher.group(2));
    }
}
