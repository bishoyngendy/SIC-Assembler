package records;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import models.ExecutableLine;
import tables.OperationTable;

import static engine.GlobalDataSingleton.SEPARATOR;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public class RecordImp implements Record {

    private int startLocation;
    private int lastLocation;
    private StringBuilder mainRecordBuilder;
    private StringBuilder secondaryRecordBuilder;
    private OperationTable operationTable;
    private int characterCounter;

    public RecordImp(ExecutableLine executableLine) throws InvalidFormatException {
        initializeRecord(executableLine);
    }

    public RecordImp() { }

    private void initializeRecord(ExecutableLine executableLine) throws InvalidFormatException {
        this.startLocation = executableLine.getLocation();
        this.mainRecordBuilder = new StringBuilder();
        this.secondaryRecordBuilder = new StringBuilder();
        String recordStartLocation = Integer.toHexString(startLocation);
        mainRecordBuilder.append("T" + SEPARATOR);
        mainRecordBuilder.append(("000000" + recordStartLocation).substring(recordStartLocation.length()));
        mainRecordBuilder.append(SEPARATOR);
        this.operationTable = GlobalDataSingleton.getInstance().getOperationTable();
        this.characterCounter = 9;
        addInstruction(executableLine);
    }

    @Override
    public boolean doesInstructionFit(ExecutableLine executableLine) throws InvalidFormatException {
        return (characterCounter + executableLine.getLineObjectCode().length()) <= 69;
    }

    @Override
    public void addInstruction(ExecutableLine executableLine) throws InvalidFormatException {
        if(mainRecordBuilder == null) {
            initializeRecord(executableLine);
        } else {
            secondaryRecordBuilder.append(SEPARATOR);
            secondaryRecordBuilder.append(executableLine.getOperand()
                    .getInstructionCode(operationTable.getOperationCode(executableLine.getOperation())));
            this.lastLocation = executableLine.getLocation() + executableLine.getLengthInBytes();
            this.characterCounter += 6;
        }
    }

    @Override
    public String getRecord() {
        int programLengthInDecimal = lastLocation - startLocation;
        String programLengthInHex = Integer.toHexString(programLengthInDecimal);
        mainRecordBuilder.append(("00" + programLengthInHex).substring(programLengthInHex.length()));
        mainRecordBuilder.append(secondaryRecordBuilder);
        return mainRecordBuilder.toString().toUpperCase();
    }

    @Override
    public Boolean isInitialized() {
        return mainRecordBuilder != null;
    }
}
