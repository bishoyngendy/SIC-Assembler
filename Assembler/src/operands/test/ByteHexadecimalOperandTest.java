package operands.test;

import com.sun.media.sound.InvalidFormatException;
import operands.ByteHexadecimalOperand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
class ByteHexadecimalOperandTest {

    private ByteHexadecimalOperand byteHexadecimalOperand;

    @BeforeEach
    void setUp() {
        byteHexadecimalOperand = new ByteHexadecimalOperand("F1");
    }

    @Test
    void getInstructionCode() throws InvalidFormatException {
        assertEquals("F1", byteHexadecimalOperand.getInstructionCode(null));
    }

}