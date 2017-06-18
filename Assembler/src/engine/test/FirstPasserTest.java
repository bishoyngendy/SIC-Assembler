package engine.test;

import com.sun.media.sound.InvalidFormatException;
import engine.FirstPasser;
import models.*;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserImp;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/18/17.
 */
class FirstPasserTest {

    private ProgramLinesCollection instructions;

    private void setupMockup() throws InvalidFormatException {
        instructions = new ProgramLinesCollection();
        Parser parser = new ParserImp();
        DirectiveLine start = new DirectiveLine("TEST     START   1000");
        DirectiveLine load = new DirectiveLine("         LDA     TWO");
        DirectiveLine store = new DirectiveLine("         STX     TWO");
        DirectiveLine load2 = new DirectiveLine("         LDA     BUFFER,X");
        DirectiveLine word = new DirectiveLine("TWO      WORD    2");
        DirectiveLine resw = new DirectiveLine("BUFFER   RESW    3");
        DirectiveLine bytee = new DirectiveLine("BLANK    BYTE    X'F2'");
        DirectiveLine bytee2 = new DirectiveLine("CHAR     BYTE    C'I am here'");
        DirectiveLine end = new DirectiveLine("         END     TEST");

        ExecutableLine parsedStart = (ExecutableLine) parser.parse(start.getLine());
        ExecutableLine parsedLoad = (ExecutableLine) parser.parse(load.getLine());
        ExecutableLine parsedStore = (ExecutableLine) parser.parse(store.getLine());
        ExecutableLine parsedLoad2 = (ExecutableLine) parser.parse(load2.getLine());
        ExecutableLine parsedWord = (ExecutableLine) parser.parse(word.getLine());
        ExecutableLine parsedResw = (ExecutableLine) parser.parse(resw.getLine());
        ExecutableLine parsedBytee = (ExecutableLine) parser.parse(bytee.getLine());
        ExecutableLine parsedBytee2 = (ExecutableLine) parser.parse(bytee2.getLine());
        ExecutableLine parsedEnd = (ExecutableLine) parser.parse(end.getLine());


        instructions.add(parsedStart);
        instructions.add(parsedLoad);
        instructions.add(parsedStore);
        instructions.add(parsedLoad2);
        instructions.add(parsedWord);
        instructions.add(parsedResw);
        instructions.add(parsedBytee);
        instructions.add(parsedBytee2);
        instructions.add(parsedEnd);
    }

    @Test
    void firstPassTest() throws InvalidFormatException {
        setupMockup();

        FirstPasser passer = new FirstPasser(instructions);
        passer.FirstPass();
        Iterator<ProgramLine> iterator = instructions.iterator();
        ExecutableLine line = (ExecutableLine)iterator.next();
        assertEquals(4096, line.getLocation(), "Start");
        line = (ExecutableLine)iterator.next();

        assertEquals(4096, line.getLocation(), "Load");
        line = (ExecutableLine)iterator.next();

        assertEquals(4099, line.getLocation(), "Store");
        line = (ExecutableLine)iterator.next();

        assertEquals(4102, line.getLocation(), "Load 2");
        line = (ExecutableLine)iterator.next();

        assertEquals(4105, line.getLocation(), "Word");
        line = (ExecutableLine)iterator.next();

        assertEquals(4108, line.getLocation(), "Resw");
        line = (ExecutableLine)iterator.next();

        assertEquals(4117, line.getLocation(), "Byte");
        line = (ExecutableLine)iterator.next();
        assertEquals(4118, line.getLocation(), "Byte2");
//        line = (ExecutableLine)iterator.next();

//        assertEquals(1000, line.getLocation(), "End");

    }
}
