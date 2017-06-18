package engine;

import com.sun.media.sound.InvalidFormatException;
import tables.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 26/04/2017.
 */
public class ExpressionEvaluatorImp implements ExpressionEvaluator {

    /**
     * The symbol table.
     */
    private SymbolTable symbolTable;

    /**
     * list to carry all operands in series.
     */
    private List<String> operands;

    /**
     * list to carry signs of each operands.
     */
    private List<Integer> signs;

    /**
     * if the last read character was a '+' or a '-'
     */
    private boolean lastIsSign = false;

    /**
     * last location to return when empty expression is passed
     */
    private int lastSavedLocation = Integer.MIN_VALUE;

    /**
     * The number of positive symbols - number of negative symbols.
     */
    private int symbolSignsDifference;

    public ExpressionEvaluatorImp() {
        initialize();
    }

    private void initialize() {
        this.symbolTable = GlobalDataSingleton.getInstance().getSymbolTable();
        this.operands = new ArrayList<>();
        this.signs = new ArrayList<>();
        this.symbolSignsDifference = 0;
    }

    @Override
    public int getExpressionLocation(String expression, String operation) throws InvalidFormatException {
        if(expression.trim().equalsIgnoreCase("") && operation.trim().equalsIgnoreCase("ORG")) {
            if(lastSavedLocation == Integer.MIN_VALUE) {
                throw new InvalidFormatException("First Expression Cannot be Empty. Expression: " + expression);
            } else {
                return lastSavedLocation;
            }
        }
        lastSavedLocation = GlobalDataSingleton.getInstance().getCurrentLocationCounter();
        initialize();
        parseExpression(expression);
        validate();
        int loc = getOverallLocation();
        if(loc >= 0) {
            return loc;
        } else {
            throw new InvalidFormatException("Location is Negative. Expression: " + expression);
        }
    }

    private void validate() throws InvalidFormatException {
        if (operands.size() != signs.size()) {
            throw new InvalidFormatException("Invalid expression");
        }
        if (symbolSignsDifference != 0 && symbolSignsDifference != 1) {
            throw new InvalidFormatException("Difference between +ve and -ve symbols must be 0 or 1");
        }
    }

    private int getOverallLocation() throws InvalidFormatException {
        int loc = 0;
        for (int i = 0; i < operands.size(); i++) {
            loc += signs.get(i) * getOperandLocation(operands.get(i));
        }
        return loc;
    }

    private void parseExpression(String expression) throws InvalidFormatException {
        int index = 0;
        while (index < expression.length()) {
            char c = expression.charAt(index);
            if (Character.isAlphabetic(c)) {
                index = handleSymbol(expression, index);
            } else if (Character.isDigit(c)) {
                if(c == '0' && index < expression.length() - 1 &&
                        expression.charAt(index + 1) == 'x') {
                    index = handleHexadecimalNum(expression, index);
                } else {
                    index = handleNumber(expression, index);
                }
            } else if (c == '-') {
                index = handleSign(index, -1);
            } else if (c == '+') {
                index = handleSign(index, 1);
            } else if (c == '*') {
                index = handleCurrentLocationCounter(index);
            } else if (c == ' ') {
                index++;
            } else {
                throw new InvalidFormatException("Invalid Character in Expression !!. Expression: " + expression);
            }
        }
    }

    private int handleHexadecimalNum(String expression, int index) {
        if(!lastIsSign) {
            signs.add(1);
        }
        operands.add(readHexadecimalAsString(expression, index + 2));
        index = getLastHexadecimalIndex(expression, index + 2);
        lastIsSign = false;
        return index;
    }

    private int handleSign(int index, int signToAdd) throws InvalidFormatException {
        if (!lastIsSign) {
            lastIsSign = true;
            signs.add(signToAdd);
        } else {
            throw new InvalidFormatException("Multiple signs after each other !!");
        }
        index++;
        return index;
    }

    private int handleCurrentLocationCounter(int index) throws InvalidFormatException {
        if (!lastIsSign && index == 0) {
            signs.add(1);
            operands.add("*");
        } else if (!lastIsSign) {
            throw new InvalidFormatException("* must be the first char or come after a '+'");
        } else {
            if (signs.get(signs.size() - 1) == 1) {
                signs.add(1);
                operands.add("*");
            } else {
                throw new InvalidFormatException("* cannot come after '-'");
            }
        }
        index++;
        lastIsSign = false;
        return index;
    }

    private int handleNumber(String expression, int index) {
        if(!lastIsSign) {
            signs.add(1);
        }
        operands.add(readIntAsString(expression, index));
        index = getLastIntIndex(expression, index);
        lastIsSign = false;
        return index;
    }

    private int handleSymbol(String expression, int index) throws InvalidFormatException {
        if (!lastIsSign && index == 0) {
            signs.add(1);
            symbolSignsDifference++;
        } else if (!lastIsSign) {
            throw new InvalidFormatException("Literals must Come first of expression or after a '+' or '-'." +
                    "Expression: " + expression );
        } else {
            switch (signs.get(signs.size() - 1)) {
                case 1:
                    symbolSignsDifference++;
                    break;
                case -1:
                    symbolSignsDifference--;
                    break;
                default:
                    throw new InvalidFormatException("Unreachable Case. Expression: " + expression);
            }
        }
        operands.add(readSymbol(expression, index));
        index = getLastSymbolIndex(expression, index);
        lastIsSign = false;
        return index;
    }

    private int getOperandLocation(String operand) throws InvalidFormatException {
        if(operand.equalsIgnoreCase("*")) {
            return GlobalDataSingleton.getInstance().getCurrentLocationCounter();
        } else if(isInteger(operand)) {
            return Integer.valueOf(operand);
        } else if(symbolTable.isSymbolFound(operand)) {
            return symbolTable.getSymbolLocation(operand);
        } else {
            throw new InvalidFormatException("Invalid expression May be Symbol is not found");
        }
    }

    private boolean isInteger(String operand) {
        try {
            Integer.parseInt(operand);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String readIntAsString(String str, int index) {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(str.charAt(index));
            index++;
        } while (index < str.length() && Character.isDigit(str.charAt(index)));
        return builder.toString();
    }

    private int getLastIntIndex(String str, int index) {
        do {
            index++;
        } while (index < str.length() && Character.isDigit(str.charAt(index)));
        return index;
    }

    private String readHexadecimalAsString(String str, int index) {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(str.charAt(index));
            index++;
        } while (index < str.length() && (Character.isDigit(str.charAt(index)) ||
                Character.toLowerCase(str.charAt(index)) == 'a' || Character.toLowerCase(str.charAt(index)) == 'b' ||
                Character.toLowerCase(str.charAt(index)) == 'c' || Character.toLowerCase(str.charAt(index)) == 'd' ||
                Character.toLowerCase(str.charAt(index)) == 'e' || Character.toLowerCase(str.charAt(index)) == 'f'));
        return (Integer.valueOf(builder.toString(), 16) + "");
    }

    private int getLastHexadecimalIndex(String str, int index) {
        do {
            index++;
        } while (index < str.length() && (Character.isDigit(str.charAt(index)) ||
                Character.toLowerCase(str.charAt(index)) == 'a' || Character.toLowerCase(str.charAt(index)) == 'b' ||
                Character.toLowerCase(str.charAt(index)) == 'c' || Character.toLowerCase(str.charAt(index)) == 'd' ||
                Character.toLowerCase(str.charAt(index)) == 'e' || Character.toLowerCase(str.charAt(index)) == 'f'));
        return index;
    }

    private String readSymbol(String str, int index) {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(str.charAt(index));
            index++;
        } while (index < str.length() && (Character.isDigit(str.charAt(index)) ||
                Character.isAlphabetic(str.charAt(index))));
        return builder.toString();
    }

    private int getLastSymbolIndex(String str, int index) {
        do {
            index++;
        } while (index < str.length() && (Character.isDigit(str.charAt(index)) ||
                Character.isAlphabetic(str.charAt(index))));
        return index;
    }

}
