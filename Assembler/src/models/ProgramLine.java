package models;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 * This is the base class for any line in the program
 */
public abstract class ProgramLine {

    private String line;
    private int index;
    /**.
     * The line number in the program, 1, 2, 3, etc....
     */
    private int lineNum;

    /**.
     * @param line the whole line as string
     */
    ProgramLine(String line) {
        this.line = line;
    }

    /**.
     * @return true if this line is a comment
     */
    public abstract boolean isComment();

    /**.
     * @return the whole line as string
     */
    public String getLine() {
        return line;
    }

    /**.
     * @return the index of the line in the program.
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum set the line number with the given.
     */
    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }


    /**
     * Getter for property 'index'.
     *
     * @return Value for property 'index'.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter for property 'index'.
     *
     * @param index Value to set for property 'index'.
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
