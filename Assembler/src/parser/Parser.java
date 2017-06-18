package parser;

import com.sun.media.sound.InvalidFormatException;
import models.ProgramLine;

import java.util.List;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Tuesday 11/04/2017.
 */
public interface Parser {
    /**
     * Parses a specific line and returns its operation and operands. this functions
     * sets the lineNum as -1;
     * @param line the line to be parsed
     * @return the operation and operands of the line
     */
    ProgramLine parse(String line) throws InvalidFormatException;

    /**
     * Parses a specific line and returns its operation and operands.
     * @param line the line to be parsed
     * @param lineNum the index of the line in the program.
     * @return the operation and operands of the line
     */
    ProgramLine parse(String line, int lineNum) throws InvalidFormatException;
}
