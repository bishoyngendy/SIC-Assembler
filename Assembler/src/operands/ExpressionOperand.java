package operands;

import engine.ExpressionEvaluator;
import engine.ExpressionEvaluatorImp;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 27/04/2017.
 */
public class ExpressionOperand implements Operand, RegexValidator {

    private String expression;

    public ExpressionOperand(String expression) {
        this.expression = expression;
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) {
        return "      ";
    }

    @Override
    public String getValue() {
        return expression;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operation.equalsIgnoreCase("org") || operation.equalsIgnoreCase("equ");
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new ExpressionOperand(operand);
    }
}
