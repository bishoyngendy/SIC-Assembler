package parser;

import com.sun.media.sound.InvalidFormatException;
import models.*;
import operands.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on Tuesday 11/04/2017.
 */
public class ParserImp implements Parser {

    @Override
    public ProgramLine parse(String line) throws InvalidFormatException {
        return parse(line, -1);
    }

    @Override
    public ProgramLine parse(String line, int lineNum) throws InvalidFormatException {
        // TODO check if the line is shorter than 15 character

        if (isComment(line)) {
            return new CommentLine(line);
        }

        ExecutableLine executableLine;
        if (isDirective(line)) {
            executableLine = new DirectiveLine(line);
            executableLine.setOperand(getDirectiveOperand(line));
        } else {
            executableLine = new InstructionLine(line);
            executableLine.setOperand(getInstructionOperand(line));
        }

        executableLine.setLabel(getLabel(line));
        executableLine.setOperation(getOperation(line));
//        if (line.length() > 17)
//            executableLine.setOperand(getOperands(line));

        if (line.length() > 35) {
            executableLine.setComment(getComment(line));
        }

        return executableLine;
    }

    /**.
     * @param line the line to check if it's a comment
     * @return true if the given line is a comment
     */
    private boolean isComment(String line) {
        return line.charAt(0) == '.' || line.charAt(0) == '*';
    }

    /**.
     * @param line the line to check
     * @return true if the given line is a directive
     */
    private boolean isDirective(String line) {
        Pattern pattern = Pattern.compile("(?i)(start|end|RESW|RESB|BYTE|WORD|ORG|EQU)");
        Matcher matcher = pattern.matcher(getOperation(line));
        return matcher.find();
    }

    private String getLabel(String line) {
        return line.substring(0, 8).trim();
    }

    private String getOperation(String line) {
        int operationEndIndex = line.length() > 15 ? 15 : line.length();

        return line.substring(9, operationEndIndex).trim();
    }

    private String getComment(String line) {
        return line.substring(35, Math.min(66, line.length())).trim();
    }

    private Operand getInstructionOperand(String line) {
        if (!containOperand(line)) return new ReturnOperand();
        String operand = line.substring(17, getOperandLastIndex(line)).trim();
        if (operand.contains(",")) { // indexing
            String label = operand.split(",")[0].trim();
            return new NormalSymbolWithIndexOperand(label);
        } else { // no indexing
            if (operand.charAt(0) == '=') {
                if (Character.toLowerCase(operand.charAt(1)) == 'c') {
                    return new LiteralCharactersOperand(operand.substring(3, operand.length()-1));
                } else if (Character.toLowerCase(operand.charAt(1)) == 'x') {
                    return new LiteralHexadecimalOperand(operand.substring(3, operand.length()-1));
                } else {
                    return new LiteralValueOperand(operand.substring(0));
                }
            }
            return new NormalSymbolOperand(operand.split(",")[0].trim());
        }
    }

    private Operand getDirectiveOperand(String line) throws InvalidFormatException {
        if (!containOperand(line)) return new NullOperand();

        String operand = line.substring(17, getOperandLastIndex(line)).trim();
        String operation = getOperation(line);
        if (operation.equalsIgnoreCase("start")) return new HexadecimalOperand(operand);
        else if (operation.equalsIgnoreCase("end")) {
            try {
                Integer.parseInt(operand);
                return new DecimalDirectiveOperand(operand);
            } catch(NumberFormatException e) {
                return new EndSymbolOperand(operand);
            }
        }
        else if (operation.equalsIgnoreCase("RESW") || operation.equalsIgnoreCase("RESB"))
            return new DecimalDirectiveOperand(operand);
        else if (operation.equalsIgnoreCase("BYTE")) {
            if (Character.toLowerCase(operand.charAt(0)) == 'c') { // character
                String value = operand.substring(2, operand.length() - 1);
                return new ByteCharacterOperand(value);
            } else if (Character.toLowerCase(operand.charAt(0)) == 'x') { // hexadecimal
                String value = operand.substring(2, operand.length() - 1);
                return new ByteHexadecimalOperand(value);
            } else {
                throw new InvalidFormatException("Invalid Byte Type");
            }
        } else if (operation.equalsIgnoreCase("WORD")) {
            return new WordOperand(operand);
        } else if (operation.equalsIgnoreCase("ORG") ||
                operation.equalsIgnoreCase("EQU")) {
            return new ExpressionOperand(operand.substring(0, operand.length()));
        }

        throw new InvalidFormatException("Invalid Directive" + line);
    }

    private int getOperandLastIndex(String line) {
        return line.length() > 35 ? 35 : line.length();
    }

    private boolean containOperand(String line) {
        return line.length() > 17;
    }
}