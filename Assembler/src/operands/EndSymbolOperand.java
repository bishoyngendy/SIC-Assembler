package operands;

import parser.PatternUtilities;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 28/04/2017.
 */
public class EndSymbolOperand implements Operand, RegexValidator {

    private String symbol;

    public EndSymbolOperand(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "";
    }

    @Override
    public String getValue() {
        return symbol;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operation.equalsIgnoreCase("END")
                && (operand.matches(PatternUtilities.OPERAND));
    }

    @Override
    public Operand getNewInstance(String operand) {
        if (operand.equalsIgnoreCase("")) return new NullOperand();
        return new EndSymbolOperand(operand);
    }
}
