package operands;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS num : course name
 * Lab num : systemsprogramming
 * Created by Marc Magdi on 4/14/17.
 */
public class DecimalDirectiveOperand implements Operand, RegexValidator {

    private String decimal;

    public DecimalDirectiveOperand(String decimal) {
        this.decimal = decimal;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "";
    }

    @Override
    public String getValue() {
        return decimal;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        try {
            Integer.parseInt(operand.trim());
            return !operation.equalsIgnoreCase("word");
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Operand getNewInstance(String operand) throws InvalidFormatException {
        try {
            Integer.parseInt(operand.trim());
            return new DecimalDirectiveOperand(operand);
        } catch(NumberFormatException e) {
            throw new InvalidFormatException("Invalid Decimal Number !!");
        }
    }
}
