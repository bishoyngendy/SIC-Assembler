package parser.test;

import com.sun.media.sound.InvalidFormatException;
import models.*;
import operands.*;
//import org.junit.jupiter.api.Assertions.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserImp;
import parser.ParserRegexImp;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/13/17.
 */
class ParserImpTest {
    private Parser parser;

    ParserImpTest() {
//        parser = new ParserImp();
        parser = new ParserRegexImp();
    }

    @Test
    void parseComment() throws InvalidFormatException {
        String comment = ".Marc";
        String comment2 = "*Hello";
        String comment3 = ".aa";
        ProgramLine parsedComment = parser.parse(comment);
        ProgramLine parsedComment2 = parser.parse(comment2);
        ProgramLine parsedComment3 = parser.parse(comment3);
        assertThat(parsedComment, instanceOf(CommentLine.class));
        assertThat(parsedComment2, instanceOf(CommentLine.class));
        assertThat(parsedComment3, instanceOf(CommentLine.class));
    }

    @Test
    void parseStartDirective() throws InvalidFormatException {
        String inst = "Prog     START   1000";
        String inst2 = "COPY     START   1000";
        ProgramLine parsedDirective = parser.parse(inst);
        ProgramLine parsedDirective2 = parser.parse(inst2);

        testExecutableFull(parsedDirective, "START", "1000", DirectiveLine.class, HexadecimalOperand.class);
        testExecutableFull(parsedDirective2, "START", "1000", DirectiveLine.class, HexadecimalOperand.class);
        assertEquals("Prog", ((ExecutableLine)parsedDirective).getLabel());
        assertEquals("COPY", ((ExecutableLine)parsedDirective2).getLabel());
    }

    @Test
    void parseEndDirective() throws InvalidFormatException {
        String inst = "         END     FIRST";
        ProgramLine parsedDirective = parser.parse(inst);

        testExecutableFull(parsedDirective, "END", "FIRST", DirectiveLine.class, EndSymbolOperand.class);
    }

    @Test
    void parseDirectivesWithDecimalOperands() throws InvalidFormatException {
        String dctv1 = "         END     1000";
        String dctv2 = "         RESW    2000";
        String dctv3 = "         RESB    3000";
        String dctv4 = "         WORD    4000";

        ProgramLine parsedDirective1 = parser.parse(dctv1);
        ProgramLine parsedDirective2 = parser.parse(dctv2);
        ProgramLine parsedDirective3 = parser.parse(dctv3);
        ProgramLine parsedDirective4 = parser.parse(dctv4);

        testExecutableFull(parsedDirective1, "END", "1000", DirectiveLine.class, DecimalDirectiveOperand.class);
        testExecutableFull(parsedDirective2, "RESW", "2000", DirectiveLine.class, DecimalDirectiveOperand.class);
        testExecutableFull(parsedDirective3, "RESB", "3000", DirectiveLine.class, DecimalDirectiveOperand.class);
        testExecutableFull(parsedDirective4, "WORD", "4000", DirectiveLine.class, WordOperand.class);
    }

    @Test
    void parseByteCharacterDirective() throws InvalidFormatException {
        String inst = "         BYTE    c'Hello World'";
        ProgramLine parsedDirective = parser.parse(inst);

        testExecutableFull(parsedDirective, "BYTE", "Hello World", DirectiveLine.class, ByteCharacterOperand.class);
    }

    @Test
    void parseByteHexaDirective() throws InvalidFormatException {
        String inst = "         BYTE    x'FF124F'";
        ProgramLine parsedDirective = parser.parse(inst);

        testExecutableFull(parsedDirective, "BYTE", "FF124F", DirectiveLine.class, ByteHexadecimalOperand.class);
    }

    @Test
    void parseIndexedInstruction() throws InvalidFormatException {
        String inst = "         LDA     Label, x";
        String instHexa = "         LDA     0xAAF2A, x";
        String inst2 = "         LDA     Label,x";
        ProgramLine parsedDirective1 = parser.parse(inst);
        ProgramLine parsedDirective2 = parser.parse(inst2);
        ProgramLine parsedDirective3 = parser.parse(instHexa);

        testExecutableFull(parsedDirective1, "LDA", "Label", InstructionLine.class, NormalSymbolWithIndexOperand.class);
        testExecutableFull(parsedDirective2, "LDA", "Label", InstructionLine.class, NormalSymbolWithIndexOperand.class);
        testExecutableFull(parsedDirective3, "LDA", "AAF2A", InstructionLine.class, NormalAddressWithIndexingOperand.class);
    }

    @Test
    void parseNonIndexedInstruction() throws InvalidFormatException {
        String inst = "         LDA     Label";
        String instHexa = "         LDA     0xAAF2A";
        ProgramLine parsedDirective = parser.parse(inst);
        ProgramLine parsedDirective2 = parser.parse(instHexa);

        testExecutableFull(parsedDirective, "LDA", "Label", InstructionLine.class, NormalSymbolOperand.class);
        testExecutableFull(parsedDirective2, "LDA", "AAF2A", InstructionLine.class, NormalAddressOperand.class);
    }

    /**.
     * This function test that a given line has the right type, that its operand
     * has the right type and check for the value of the operand and the operation
     * @param parsedLine the parsed line to check
     * @param operation the expected operation value
     * @param operand the expected operand value
     * @param lineClass the expected line class type
     * @param operandClass the expected operand class type
     */
    private void testExecutableFull(ProgramLine parsedLine, String operation, String operand,
                                    Class lineClass, Class operandClass) {
        assertThat(parsedLine, instanceOf(lineClass));
        assertThat(((ExecutableLine) parsedLine).getOperand(), instanceOf(operandClass));
        assertEquals("Operation Value", operation, ((ExecutableLine)parsedLine).getOperation());
        assertEquals("Operand Value", operand, ((ExecutableLine)parsedLine).getOperand().getValue());
    }
}