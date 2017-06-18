package command.line;

import com.sun.media.sound.InvalidFormatException;
import engine.FirstPasser;
import engine.SecondPasser;
import models.ProgramLine;
import models.ProgramLinesCollection;
import parser.Parser;
import parser.ParserRegexImp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 */
public class AssemblerImp implements Assembler {

    private Parser parser;
    private SecondPasser secondPasser;

    AssemblerImp() {
        parser = new ParserRegexImp();
    }

    @Override
    public void saveData(File inputFile) throws FileNotFoundException, InvalidFormatException {
        ProgramLinesCollection programLines = readData(inputFile);
        FirstPasser firstPasser = new FirstPasser(programLines);
        firstPasser.FirstPass();
        secondPasser = new SecondPasser(programLines);
    }

    @Override
    public void createObjectFile(String fileName) throws InvalidFormatException {
        try {
            String objectProgram = secondPasser.getObjectProgram();
            PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8");
            writer.print(objectProgram);
            writer.close();
        } catch (IOException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    @Override
    public void createListingFile(String fileName) {
        try {
            String listingProgram = secondPasser.getListingProgram();
            PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8");
            writer.print(listingProgram);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProgramLinesCollection readData(File inputFile) throws FileNotFoundException, InvalidFormatException {
        Scanner scanner = new Scanner(inputFile);
        ProgramLinesCollection programLines = new ProgramLinesCollection();
        int lineNum = 1;
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            ProgramLine programLine;
            try {
                programLine = parser.parse(line, lineNum);
            } catch (InvalidFormatException e) {
                throw new InvalidFormatException(e.getMessage() + " at line " + lineNum);
            }
            programLines.add(programLine);
            lineNum++;
        }
        return programLines;
    }
}
