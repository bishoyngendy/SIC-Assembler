package operands.test;

import com.sun.media.sound.InvalidFormatException;
import tables.SymbolTable;
import tables.SymbolTableSingleton;
import operands.NormalSymbolOperand;
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
class NormalSymbolOperandTest {

    private SymbolTable symbolTable;
    private NormalSymbolOperand normalSymbolOperand;

    @BeforeEach
    void setUp() {
        symbolTable = SymbolTableSingleton.getInstance();
        symbolTable.addSymbol("INPUT", Integer.parseInt("205D", 16));
        normalSymbolOperand = new NormalSymbolOperand("INPUT");
    }

    @Test
    void getInstructionCode() throws InvalidFormatException {
        assertEquals(normalSymbolOperand.getInstructionCode("E0"), "E0205D");
    }

}