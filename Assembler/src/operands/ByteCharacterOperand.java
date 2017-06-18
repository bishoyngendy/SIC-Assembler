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
public class ByteCharacterOperand implements Operand, RegexValidator {

    private String characters;
    private StringBuilder builder;

    public ByteCharacterOperand(String characters) {
        this.characters = characters;
        builder = new StringBuilder();
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(builder.toString().trim().equalsIgnoreCase("")) {
            if (characters.length() <= 15) {
                for (int i = 0; i < characters.length(); i++) {
                    String num = Integer.toHexString((int) characters.charAt(i));
                    builder.append(("00" + num).substring(num.length()));
                }
                return builder.toString().toUpperCase();
            } else {
                throw new InvalidFormatException("Maximum of 15 characters");
            }
        } else {
            return builder.toString();
        }
    }

    @Override
    public String getValue() {
        return characters;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.BYTE_CHARACTER_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.BYTE_CHARACTER_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new ByteCharacterOperand(matcher.group(2));
    }
}
