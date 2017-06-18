package operands;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS num : course name
 * Lab num : systemsprogramming
 * Created by Marc Magdi on 4/14/17.
 * As null design pattern, this is a null
 */
public class NullOperand implements Operand, RegexValidator {
    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "";
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.equalsIgnoreCase("") && !operation.equalsIgnoreCase("RSUB");
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new NullOperand();
    }
}
