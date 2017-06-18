package engine.test;

import com.sun.media.sound.InvalidFormatException;
import engine.FirstPasser;
import engine.SecondPasser;
import models.ProgramLinesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 27/04/2017.
 */
class SecondPasserTest4 {

    List<String> lines;
    ProgramLinesCollection collection;

    @BeforeEach
    void setUp() throws InvalidFormatException {
        collection = new ProgramLinesCollection();
        lines = new ArrayList<>();
        lines.add("TEST     START   1000");
        lines.add("         LDA     =C'EOF'");
        lines.add("         STX     TWO");
        lines.add("         LDA     BUFFER,X");
        lines.add("TWO      WORD    2");
        lines.add("BUFFER   RESW    3");
        lines.add("         LTORG   ");
        lines.add("BLANK    BYTE    X'F2'");
        lines.add("CHAR     BYTE    C'I AM HERE'");
        lines.add("         END     4096");
        Parser p = new ParserImp();
        for(String s : lines) {
            collection.add(p.parse(s));
        }
        FirstPasser firstPasser = new FirstPasser(collection);
        firstPasser.FirstPass();
    }

    @Test
    void secondPass() throws InvalidFormatException {
        SecondPasser secondPasser = new SecondPasser(collection);
        System.out.println(secondPasser.getObjectProgram());
        System.out.println(secondPasser.getObjectProgram());
    }

}