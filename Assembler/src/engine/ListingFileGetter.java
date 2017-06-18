package engine;

import com.sun.media.sound.InvalidFormatException;
import models.CommentLine;
import models.ExecutableLine;
import models.ProgramLine;
import models.ProgramLinesCollection;
import operands.Literal;
import operands.LiteralCharactersOperand;
import operands.LiteralHexadecimalOperand;
import tables.LiteralTable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 26/04/2017.
 */
class ListingFileGetter {

    private ProgramLinesCollection programLines;
    private StringBuilder listingProgramBuilder;
    private Set<Literal> passedLiterals;
    private Queue<Literal> currentStageLiterals;
    private LiteralTable literalTable;

    ListingFileGetter(ProgramLinesCollection programLines) {
        this.programLines = programLines;
        this.listingProgramBuilder = new StringBuilder();
        this.passedLiterals = new HashSet<>();
        this.currentStageLiterals = new LinkedList<>();
        this.literalTable = GlobalDataSingleton.getInstance().getLiteralTable();
    }

    String getListingProgram() throws InvalidFormatException {
        writeListingHeader();
        int startLine = 5;
        for (ProgramLine line : programLines) {
            addLineNumber(startLine, "    ");
            if(line.isComment()) {
                CommentLine commentLine = (CommentLine) line;
                listingProgramBuilder.append("        ").append(commentLine.getLine());
            } else {
                ExecutableLine executableLine = (ExecutableLine) line;
                if(executableLine.getOperand() instanceof Literal) {
                    //handleLiteral((Literal) executableLine.getOperand());
                }
                addLocation(executableLine);
                addLine(line, executableLine);
                //startLine = addLiterals(startLine, executableLine);
            }
            listingProgramBuilder.append('\n');
            startLine += 5;
        }
        return listingProgramBuilder.toString().toUpperCase();
    }

    private void addLineNumber(int startLine, String str) {
        listingProgramBuilder.append(("    " + startLine).substring((startLine + "").length()));
        listingProgramBuilder.append(str);
    }

    private void addLine(ProgramLine line, ExecutableLine executableLine) throws InvalidFormatException {
        listingProgramBuilder.append("    ").append(line.getLine());
        int diff = 66 - line.getLine().length();
        addCharacterToBuilderMultipleTimes(' ', diff, listingProgramBuilder);
        listingProgramBuilder.append("    ").append(executableLine.getLineObjectCode());
    }

    private void addLocation(ExecutableLine executableLine) {
        String locInHex = Integer.toHexString(executableLine.getLocation());
        if(executableLine.getOperation().equalsIgnoreCase("END") ||
            executableLine.getOperation().equalsIgnoreCase("LTORG")) {
            listingProgramBuilder.append("    ");
        } else {
            listingProgramBuilder.append(("0000" + locInHex).substring(locInHex.length()).toUpperCase());
        }
    }

    private int addLiterals(int startLine, ExecutableLine executableLine) throws InvalidFormatException {
        if (executableLine.getOperation().equalsIgnoreCase("END") ||
                executableLine.getOperation().equalsIgnoreCase("LTORG")) {
            startLine = listCurrentLiterals(startLine + 5);
        }
        return startLine;
    }

    private void writeListingHeader() {
        listingProgramBuilder.append("Line    Loc     ");
        addCharacterToBuilderMultipleTimes(' ', 25, listingProgramBuilder);
        listingProgramBuilder.append("Source Statement");
        addCharacterToBuilderMultipleTimes(' ', 25, listingProgramBuilder);
        listingProgramBuilder.append("    Object Code\n\n");
    }

    private int listCurrentLiterals(int startLine) throws InvalidFormatException {
        listingProgramBuilder.append("\n");
        while (!currentStageLiterals.isEmpty()) {
            Literal literal = currentStageLiterals.poll();
            addLineNumber(startLine, "    ");
            String locInHex = Integer.toHexString(literalTable.getLiteralLocation(literal.getValue()));
            listingProgramBuilder.append(("0000" + locInHex).substring(locInHex.length()).toUpperCase());

            listingProgramBuilder.append("    ").append("*");
            addCharacterToBuilderMultipleTimes(' ', 8, listingProgramBuilder);
            String fullLiteral = getFullLiteral(literal);
            listingProgramBuilder.append(fullLiteral);
            int diff = 6 - fullLiteral.length();
            addCharacterToBuilderMultipleTimes(' ', diff + 51, listingProgramBuilder);
            listingProgramBuilder.append("    ").append(literal.getHexValue()).append('\n');
            startLine += 5;
        }
        return startLine;
    }

    private String getFullLiteral(Literal literal) {
        if(literal instanceof LiteralCharactersOperand) {
            return "=C'" + literal.getValue() + "'";
        } else if(literal instanceof LiteralHexadecimalOperand) {
            return "=X'" + literal.getValue() + "'";
        } else {
            return "=" + literal.getValue();
        }
    }

    private void handleLiteral(Literal operand) {
        if(!passedLiterals.contains(operand)) {
            passedLiterals.add(operand);
            currentStageLiterals.add(operand);
        }
    }

    private void addCharacterToBuilderMultipleTimes(char c, int numberOfTimes, StringBuilder builder) {
        for(int i = 0; i < numberOfTimes; i++) {
            builder.append(c);
        }
    }

}
