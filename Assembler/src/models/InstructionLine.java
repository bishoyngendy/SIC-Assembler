package models;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import tables.OperationTable;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 */
public class InstructionLine extends ExecutableLine {

    /**.
     * String representing the operation code in hexadecimal
     */
    private String operationCodeInHexadecimal;

    /**.
     * @param line the whole line as string
     */
    public InstructionLine(String line) {
        super(line);
        this.setLengthInBytes(3);
    }

    @Override
    public String getLineObjectCode() throws InvalidFormatException {
        OperationTable operationTable = GlobalDataSingleton.getInstance().getOperationTable();
        if(operationTable.isValidOperation(getOperation())) {
            return getOperand().getInstructionCode(operationTable.getOperationCode(getOperation()));
        } else {
            throw new InvalidFormatException("Invalid Instruction");
        }
    }

}
