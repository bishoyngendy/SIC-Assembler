package engine.test;

import engine.GlobalDataSingleton;
import models.DirectiveLine;
import operands.HexadecimalOperand;
import tables.SymbolTable;
import models.ProgramLinesCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 14/04/2017.
 */
class SecondPasserTest {

    SymbolTable symbolTable;

    @BeforeEach
    void setUp() {
        initializeSymbolTable();
        GlobalDataSingleton.getInstance().setProgramLengthInDecimal(Integer.parseInt("20", 16));
        ProgramLinesCollection collection = new ProgramLinesCollection();
        DirectiveLine d = new DirectiveLine("START");
        d.setLengthInBytes(0);
        d.setLocation(4096);
        d.setLabel("COPY");
        d.setOperand(new HexadecimalOperand("1000"));

    }

    private void initializeSymbolTable() {
        symbolTable = GlobalDataSingleton.getInstance().getSymbolTable();
        symbolTable.addSymbol("TEST", 4096);
        symbolTable.addSymbol("TWO", Integer.parseInt("1009", 16));
        symbolTable.addSymbol("BUFFER", Integer.parseInt("100C", 16));
        symbolTable.addSymbol("BLANK", Integer.parseInt("1016", 16));
        symbolTable.addSymbol("CHAR", Integer.parseInt("1017", 16));
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void secondPass() {

    }

}