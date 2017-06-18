package operands;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/28/17.
 * This interface helps to determine if a given operand satisfy
 * any predefined operand and help to get a new instance of it.
 */
public interface RegexValidator {
    boolean isValid(String operand, String operation);

    Operand getNewInstance(String operand) throws InvalidFormatException;
}
