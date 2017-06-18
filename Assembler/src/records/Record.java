package records;

import com.sun.media.sound.InvalidFormatException;
import models.ExecutableLine;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public interface Record {

    boolean doesInstructionFit(ExecutableLine executableLine) throws InvalidFormatException;

    void addInstruction(ExecutableLine executableLine) throws InvalidFormatException;

    String getRecord();

    Boolean isInitialized();
}
