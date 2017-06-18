package models;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 * This class represents any comment line
 */
public class CommentLine extends ProgramLine {
    /**.
     * Default constructor
     * @param line the whole line as string
     */
    public CommentLine(String line) {
        super(line);
    }

    @Override
    public boolean isComment() {
        return true;
    }
}
