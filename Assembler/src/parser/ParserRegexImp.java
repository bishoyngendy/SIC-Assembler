package parser;

import com.sun.media.sound.InvalidFormatException;
import models.*;
import operands.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 */
public class ParserRegexImp implements Parser{

    private RegexHelper regexHelper;

    private List<RegexValidator> operandsValidator;

    public ParserRegexImp() {
        regexHelper = new RegexHelper();
        operandsValidator = new LinkedList<>();
        initOperandsValidator();
    }

    private void initOperandsValidator() {
        operandsValidator.add(new ReturnOperand());
        operandsValidator.add(new NullOperand());
        operandsValidator.add(new ExpressionOperand(""));
        operandsValidator.add(new NormalSymbolWithIndexOperand(""));
        operandsValidator.add(new NormalSymbolOperand(""));
        operandsValidator.add(new NormalAddressOperand(""));
        operandsValidator.add(new NormalAddressWithIndexingOperand(""));
        operandsValidator.add(new LiteralCharactersOperand(""));
        operandsValidator.add(new LiteralHexadecimalOperand(""));
        operandsValidator.add(new LiteralValueOperand("1"));
        operandsValidator.add(new HexadecimalOperand(""));
        operandsValidator.add(new DecimalDirectiveOperand(""));
        operandsValidator.add(new ByteCharacterOperand(""));
        operandsValidator.add(new ByteHexadecimalOperand(""));
        operandsValidator.add(new EndSymbolOperand(""));
        operandsValidator.add(new WordOperand());
    }

    @Override
    public ProgramLine parse(String line) throws InvalidFormatException {
        return parse(line, -1);
    }

    @Override
    public ProgramLine parse(String line, int lineNum) throws InvalidFormatException {
        if (regexHelper.isComment(line)) {
            return new CommentLine(line);
        }
        System.out.println(line); // to print the program in the CLI
        RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line);
        ExecutableLine executableLine;

        if(regexHelper.isDirective(partitionedData.operation)) {
            executableLine = new DirectiveLine(line);
        } else {
            executableLine = new InstructionLine(line);
        }
        executableLine.setLineNum(lineNum);
        executableLine.setOperand(getOperand(partitionedData.operand,
                partitionedData.operation));

        executableLine.setLabel(partitionedData.label);
        executableLine.setOperation(partitionedData.operation);
        if (partitionedData.comment != null) {
            executableLine.setComment(partitionedData.comment.trim());
        }
        return executableLine;
    }

    private Operand getOperand(String operand, String operation)
                    throws InvalidFormatException {

        for (RegexValidator regexValidator : operandsValidator) {
            if (regexValidator.isValid(operand, operation)) {
                return regexValidator.getNewInstance(operand);
            }
        }

        throw new InvalidFormatException("Invalid Operand Format!");
    }
}
