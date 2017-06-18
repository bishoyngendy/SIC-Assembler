package engine;

import com.sun.media.sound.InvalidFormatException;
import models.DirectiveLine;
import models.ExecutableLine;
import models.ProgramLine;
import models.ProgramLinesCollection;
import operands.ByteCharacterOperand;
import operands.ByteHexadecimalOperand;
import operands.Literal;
import tables.LiteralTable;
import tables.LiteralTableSingleton.LiteralData;
import tables.OperationTable;
import tables.SymbolTable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on Wednesday 12/04/2017.
 */
public class FirstPasser {

    private ProgramLinesCollection instructions;
    private OperationTable opTable;
    private SymbolTable symbolTable;
    private LiteralTable literalTable;
    private ExpressionEvaluator expressionEvaluator;
    private GlobalDataSingleton globalData;
    private Queue<LiteralData> literalsWithoutAddresses;
    private Queue<DirectiveLine> literalDirectives;
    private int currentIndex;
    /**.
     * @param instructions collection of the instructions to run the first pass on.
     */
    public FirstPasser(ProgramLinesCollection instructions) {
        this.currentIndex = 0;
        this.instructions = instructions;
        this.literalDirectives = new LinkedList<>();
        this.globalData = GlobalDataSingleton.getInstance();
        this.opTable = globalData.getOperationTable();
        this.symbolTable = globalData.getSymbolTable();
        this.literalTable = globalData.getLiteralTable();
        this.literalsWithoutAddresses = new LinkedList<>();
        this.expressionEvaluator = new ExpressionEvaluatorImp();
    }

    public void FirstPass() throws InvalidFormatException {
        int startingAddress = 0, locationCounter = 0, programLength;
        Iterator<ProgramLine> iterator = instructions.iterator();
        ProgramLine instruction = iterator.next();

        while (instruction.isComment() && iterator.hasNext()) {
            instruction = iterator.next();
        }

        if (((ExecutableLine)instruction).getOperation().equalsIgnoreCase("start")) {
            startingAddress = Integer.valueOf(((ExecutableLine)instruction).getOperand().getValue(), 16);
            locationCounter = startingAddress;
            ((ExecutableLine) instruction).setLocation(locationCounter);
        } else {
            iterator = instructions.iterator();
        }


        while (iterator.hasNext()) {
            instruction = iterator.next();
            instruction.setIndex(currentIndex++);
            if (!instruction.isComment()) {
                ExecutableLine execInst = (ExecutableLine) instruction;
                execInst.setLocation(locationCounter);
                if (execInst.getOperation().equalsIgnoreCase("end")) {
                    locationCounter = setAddressesForLiterals(locationCounter);
                    break;
                }

                addNewLabelIfExist(execInst, locationCounter);
                String opCode = execInst.getOperation();
                boolean validOperation = opTable.isValidOperation(opCode);
                globalData.setCurrentLocationCounter(locationCounter);
                if (validOperation) {
                    locationCounter += 3; // instruction length
                    // may contain literal
                    if (execInst.getOperand() instanceof Literal) {
                        String literal = execInst.getOperand().getValue();
                        if (!literalTable.exists(literal)) {
                            LiteralData literalData = literalTable.addLiteral(literal, ((Literal) execInst.getOperand()).getHexValue());
                            literalData.setLiteral((Literal)execInst.getOperand());
                            literalsWithoutAddresses.add(literalData);
                        }
                    }
                } else if (opCode.equalsIgnoreCase("WORD")) {
                    locationCounter += 3; // word size
                } else if (opCode.equalsIgnoreCase("RESW")) {
                    int wordSize = Integer.valueOf(execInst.getOperand().getValue());
                    locationCounter += (3 * wordSize);
                } else if (opCode.equalsIgnoreCase("RESB")) {
                    int wordSize = Integer.valueOf(execInst.getOperand().getValue());
                    locationCounter += wordSize;
                } else if (opCode.equalsIgnoreCase("BYTE")) {
                    if (execInst.getOperand() instanceof ByteHexadecimalOperand) {
                        locationCounter += Math.ceil(execInst.getOperand().getValue().length()/2);
                    } else if (execInst.getOperand() instanceof ByteCharacterOperand) {
                        locationCounter += execInst.getOperand().getValue().length();
                    } else {
                        throw new InvalidFormatException("Invalid Byte at line: " + execInst.getLineNum());
                    }
                } else if (opCode.equalsIgnoreCase("LTORG")) {
                    locationCounter = setAddressesForLiterals(locationCounter);
                } else if (opCode.equalsIgnoreCase("ORG")) {
                    // change location counter by the operand value
                    locationCounter = expressionEvaluator.getExpressionLocation(execInst.getOperand().getValue(), "ORG");
                } else if (opCode.equalsIgnoreCase("EQU")) {
                    // change location counter by the operand value
                    int loc = expressionEvaluator
                            .getExpressionLocation(execInst.getOperand().getValue(), "EQU");
                    symbolTable.addSymbol(execInst.getLabel(), loc);
                } else {
                    throw new InvalidFormatException("Invalid Operation Code: " + opCode + " at line " + execInst.getLineNum());
                }

            }
        }
        addLiteralDirectives();

        programLength = locationCounter - startingAddress;
        GlobalDataSingleton.getInstance().setProgramLengthInDecimal(programLength);
        instructions.order();
    }

    private void addLiteralDirectives() {
        for (DirectiveLine directiveLine : literalDirectives) {
            instructions.add(directiveLine);
        }
    }
    /**.
     * @param instruction the instruction to get the label from
     * @param locationCounter the current location counter
     */
    private void addNewLabelIfExist(ExecutableLine instruction, int locationCounter) throws InvalidFormatException {
        if(instruction.hasLabel()) {
            if (symbolTable.isSymbolFound(instruction.getLabel())) {
                throw new InvalidFormatException("Error Duplicate Symbol at line " + instruction.getLineNum());
            } else {
                symbolTable.addSymbol(instruction.getLabel(), locationCounter);
            }
        }
    }

    /**.
     * Set addresses for all unassigned literals and remove them from unassigned queue.
     * @param currentLocationCounter current location of the location counter.
     * @return the final location of the location counter.
     */
    private int setAddressesForLiterals(int currentLocationCounter) {
        for (LiteralData literalData : literalsWithoutAddresses) {
            literalData.setLocation(currentLocationCounter);
            DirectiveLine directiveLine = literalData.getLiteral().getDirective();
            directiveLine.setIndex(currentIndex++);
            directiveLine.setLocation(currentLocationCounter);
            literalDirectives.add(directiveLine);
            currentLocationCounter += literalData.getLength();
        }

        literalsWithoutAddresses.clear();
        return currentLocationCounter;
    }
}
