package operands;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public interface Operand {

    String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException;

    String getValue();
}
