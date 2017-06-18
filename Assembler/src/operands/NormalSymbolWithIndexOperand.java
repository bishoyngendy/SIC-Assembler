package operands;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import parser.PatternUtilities;
import tables.SymbolTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public class NormalSymbolWithIndexOperand implements Operand, RegexValidator {

    private String symbol;
    private SymbolTable symbolTable;

    public NormalSymbolWithIndexOperand(String symbol) {
        this.symbol = symbol;
        this.symbolTable = GlobalDataSingleton.getInstance().getSymbolTable();
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        String location = Integer.toHexString(symbolTable.getSymbolLocation(symbol));
        String formatted = ("0000" + location).substring(location.length());
        if(symbolTable.isSymbolFound(symbol)) {
            return (operationCodeInHexadecimal + addIndexBit(formatted)).toUpperCase();
        } else {
            throw new InvalidFormatException("Invalid Symbol");
        }
    }

    @Override
    public String getValue() {
        return symbol;
    }

    private String addIndexBit(String operationCodeInHexadecimal) {
        Integer temp = Integer.parseInt(operationCodeInHexadecimal, 16);
        temp += (int) Math.pow(2, 15);
        String hex = Integer.toHexString(temp);
        return ("0000" + hex).substring(hex.length());
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.OPERAND_WITH_INDEXING);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.OPERAND_WITH_INDEXING);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new NormalSymbolWithIndexOperand(matcher.group(2));
    }
}
