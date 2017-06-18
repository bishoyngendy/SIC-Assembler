package operands;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import parser.PatternUtilities;
import tables.SymbolTable;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public class NormalSymbolOperand implements Operand, RegexValidator {

    private String symbol;
    private SymbolTable symbolTable;

    public NormalSymbolOperand(String symbol) {
        this.symbol = symbol;
        this.symbolTable = GlobalDataSingleton.getInstance().getSymbolTable();
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(symbolTable.isSymbolFound(symbol)) {
            String location = Integer.toHexString(symbolTable.getSymbolLocation(symbol));
            return (operationCodeInHexadecimal +
                    ("0000" + location).substring(location.length())).toUpperCase();
        } else {
            throw new InvalidFormatException("Invalid Symbol");
        }
    }

    @Override
    public String getValue() {
        return symbol;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.OPERAND) && !operation.matches("(?i)(end|equ|org)");
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new NormalSymbolOperand(operand);
    }
}