package engine;

import com.sun.media.sound.InvalidFormatException;
import models.ExecutableLine;
import models.ExecutableLinesCollection;
import operands.DecimalDirectiveOperand;
import operands.EndSymbolOperand;
import operands.NullOperand;
import operands.Operand;
import records.EndRecord;
import records.HeaderRecord;
import records.Record;
import records.RecordImp;
import tables.SymbolTable;

import java.util.Iterator;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 26/04/2017.
 */
class ObjectFileGetter {

    private String programName = "";
    private String startLocation = "0000";
    private ExecutableLinesCollection executableLines;
    private int programLengthInDecimal;
    private StringBuilder objectProgramBuilder;

    ObjectFileGetter(ExecutableLinesCollection executableLines) {
        this.executableLines = executableLines;
        this.programLengthInDecimal = GlobalDataSingleton.getInstance().getProgramLengthInDecimal();
        this.objectProgramBuilder = new StringBuilder();
    }

    String getObjectProgram() throws InvalidFormatException {
        int lineNum = 1;
        try {
            Iterator<ExecutableLine> iterator = executableLines.iterator();
            ExecutableLine executableLine = iterator.next();
            executableLine = manageHeaderRecord(iterator, executableLine);
            lineNum = executableLine.getLineNum();
            Record record;
            while ((iterator.hasNext()) && isDirective(executableLine)) {
                executableLine = iterator.next();
            }
            if (!isDirective(executableLine)) {
                record = new RecordImp(executableLine);
            } else {
                return objectProgramBuilder.toString();
            }
            ExecutableLine endLine = null;
            while (iterator.hasNext()) {
                executableLine = iterator.next();
                lineNum = executableLine.getLineNum();
                if (executableLine.getOperation().equalsIgnoreCase("END")) {
                    endLine = executableLine;
                    continue;
                }
                if (!isDirective(executableLine) && record.doesInstructionFit(executableLine)) {
                    if (!executableLine.getOperation().matches("(?i)(LTORG|EQU)"))
                        record.addInstruction(executableLine);
                } else if(!record.isInitialized()) {
                    continue;
                } else {
                    objectProgramBuilder.append(record.getRecord()).append("\n");
                    if(!isDirective(executableLine)) {
                        record = new RecordImp(executableLine);
                    } else {
                        record = new RecordImp();
                    }
                }
            }
            handleEndStatement(endLine, record);
            return objectProgramBuilder.toString();
        } catch (InvalidFormatException e) {
            throw new InvalidFormatException(e.getMessage() + " at line " + lineNum);
        }
    }

    private ExecutableLine manageHeaderRecord(Iterator<ExecutableLine> iterator, ExecutableLine executableLine) throws InvalidFormatException {
        if (executableLine.getOperation().equalsIgnoreCase("START")) {
            parseStartInstruction(executableLine);
            executableLine = iterator.next();
        }
        writeHeaderRecord();
        return executableLine;
    }

    private void handleEndStatement(ExecutableLine executableLine, Record record) throws InvalidFormatException {
        if(executableLine.getOperation().equalsIgnoreCase("END")) {
            if(record.isInitialized()) {
                objectProgramBuilder.append(record.getRecord());
            } else {
                objectProgramBuilder.deleteCharAt(objectProgramBuilder.length() - 1);
            }
            objectProgramBuilder.append(System.getProperty("line.separator"));
            writeEndRecord(executableLine);
        } else {
            throw new InvalidFormatException("No END Statement expecting END at last line");
        }
    }

    private boolean isDirective(ExecutableLine executableLine) {
        String IS_SPECIAL = "(?i)(START|RESW|RESB|ORG|END)";
        return executableLine.getOperation().matches(IS_SPECIAL);
    }

    private boolean startNewLine(ExecutableLine executableLine) {
        String IS_SPECIAL = "(?i)(START|RESW|RESB|ORG|END)";
        return executableLine.getOperation().matches(IS_SPECIAL);
    }

    private void parseStartInstruction(ExecutableLine startInstruction) {
        this.programName = startInstruction.getLabel();
        this.startLocation = startInstruction.getOperand().getValue();
    }

    private void writeHeaderRecord() throws InvalidFormatException {
        HeaderRecord headerRecord = new HeaderRecord(Integer.parseInt(startLocation, 16),
                programName, programLengthInDecimal);
        objectProgramBuilder.append(headerRecord.getRecord());
        objectProgramBuilder.append(System.getProperty("line.separator"));
    }

    private void writeEndRecord(ExecutableLine executableLine) throws InvalidFormatException {
        Operand operand = executableLine.getOperand();
        int startLocationFromEndInstruction = getStartLocationFromEndInstruction(executableLine, operand);
        validateEndRecord(startLocationFromEndInstruction);
    }

    private int getStartLocationFromEndInstruction(ExecutableLine executableLine, Operand operand) throws InvalidFormatException {
        int startLocationFromEndInstruction;
        if(operand instanceof DecimalDirectiveOperand) {
            startLocationFromEndInstruction = Integer.valueOf(executableLine.getOperand().getValue());
        } else if(operand instanceof EndSymbolOperand) {
            String endName = executableLine.getOperand().getValue().trim();
            SymbolTable symbolTable = GlobalDataSingleton.getInstance().getSymbolTable();
            if(symbolTable.isSymbolFound(endName)
                    && symbolTable.getSymbolLocation(endName) == Integer.valueOf(startLocation, 16)) {
                startLocationFromEndInstruction = Integer.valueOf(startLocation, 16);
            } else if(endName.equalsIgnoreCase(programName)) {
                startLocationFromEndInstruction = Integer.valueOf(startLocation, 16);
            } else {
                throw new InvalidFormatException("Symbol in End Statement must match Name in Start Statement");
            }
        } else if(operand instanceof NullOperand) {
            startLocationFromEndInstruction = Integer.valueOf(startLocation, 16);
        } else {
            throw new InvalidFormatException("Invalid end instruction");
        }
        return startLocationFromEndInstruction;
    }

    private void validateEndRecord(int startLocationFromEndInstruction) throws InvalidFormatException {
        if(startLocationFromEndInstruction == Integer.parseInt(startLocation, 16)) {
            EndRecord endRecord = new EndRecord(startLocationFromEndInstruction);
            objectProgramBuilder.append(endRecord.getRecord());
        } else {
            throw new InvalidFormatException("END record must have the same address as start address");
        }
    }
}
