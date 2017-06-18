package models;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 */
public class DirectiveLine extends ExecutableLine{

    /**
     * @param line the whole line as string
     */
    public DirectiveLine(String line) {
        super(line);
    }

    @Override
    public String getLineObjectCode() throws InvalidFormatException {
        return getOperand().getInstructionCode("");
    }
}
