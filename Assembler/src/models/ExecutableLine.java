package models;

import com.sun.media.sound.InvalidFormatException;
import operands.*;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 * This is the base class for any executable line, ex: Instruction, Directive
 */
public abstract class ExecutableLine extends ProgramLine {

    private int location;
    private String label = "";
    private String operation = "";
    private Operand operand;
    private String comment = "";
    private int lengthInBytes;

    /**
     * @param line the whole line as string
     */
    ExecutableLine(String line) {
        super(line);
    }

    @Override
    public boolean isComment() {
        return false;
    }

    /**.
     * @return the location as decimal
     */
    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
        if(operation.equalsIgnoreCase("RESW")) {
            this.setLengthInBytes(getLengthInBytes() * 3);
        }
    }

    public Operand getOperand() {
        return operand;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
        if(operand instanceof HexadecimalOperand) {
            this.setLengthInBytes(0);
        } else if(operand instanceof DecimalDirectiveOperand) {
            this.setLengthInBytes(Integer.valueOf(operand.getValue()));
        } else if(operand instanceof ByteCharacterOperand) {
            this.setLengthInBytes(operand.getValue().length());
        } else if(operand instanceof ByteHexadecimalOperand) {
            this.setLengthInBytes((int) Math.ceil((double)operand.getValue().length()/2));
        } else if(operand instanceof WordOperand) {
            this.setLengthInBytes(3);
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean hasLabel() {
        return this.label != null && !this.label.equalsIgnoreCase("");
    }

    public int getLengthInBytes() {
        return lengthInBytes;
    }

    public void setLengthInBytes(int lengthInBytes) {
        this.lengthInBytes = lengthInBytes;
    }

    public abstract String getLineObjectCode() throws InvalidFormatException;

}
