package operands;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 28/04/2017.
 */
public class ReturnOperand implements Operand, RegexValidator {

    public ReturnOperand() {

    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "4C0000";
    }

    @Override
    public String getValue() {
        return "000000";
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operation.equalsIgnoreCase("RSUB");
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new ReturnOperand();
    }
}
