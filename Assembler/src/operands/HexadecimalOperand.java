package operands;

import parser.PatternUtilities;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS num : course name
 * Lab num : systemsprogramming
 * Created by Marc Magdi on 4/14/17.
 */
public class HexadecimalOperand implements Operand, RegexValidator {

    private String hexadecimal;

    public HexadecimalOperand(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "";
    }

    @Override
    public String getValue() {
        return hexadecimal;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operation.equalsIgnoreCase("START") && operand.matches(PatternUtilities.VALID_HEXADECIMAL);
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new HexadecimalOperand(operand);
    }
}
