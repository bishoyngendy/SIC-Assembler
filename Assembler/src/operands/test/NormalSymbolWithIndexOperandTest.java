package operands.test;

import com.sun.media.sound.InvalidFormatException;
import tables.SymbolTable;
import tables.SymbolTableSingleton;
import operands.NormalSymbolWithIndexOperand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
class NormalSymbolWithIndexOperandTest {

    private SymbolTable symbolTable;

    private NormalSymbolWithIndexOperand normalSymbolWithIndexOperand;

    @BeforeEach
    void setUp() {
        symbolTable = SymbolTableSingleton.getInstance();
        symbolTable.addSymbol("BUFFER", Integer.parseInt("1039", 16));
        normalSymbolWithIndexOperand = new NormalSymbolWithIndexOperand("BUFFER");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getInstructionCode() throws InvalidFormatException {
        assertEquals(normalSymbolWithIndexOperand.getInstructionCode("54"), "549039");
    }

}