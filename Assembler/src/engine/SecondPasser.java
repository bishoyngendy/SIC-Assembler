package engine;

import com.sun.media.sound.InvalidFormatException;
import models.ExecutableLine;
import models.ExecutableLinesCollection;
import models.ProgramLine;
import models.ProgramLinesCollection;

import java.util.Iterator;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 14/04/2017.
 */
public class SecondPasser {

    private ProgramLinesCollection programLines;
    private ExecutableLinesCollection executableLines;

    /**
     * Responsible for writing the object file.
     */
    private ObjectFileGetter objectFileGetter;

    /**
     * Responsible for writing listing file.
     */
    private ListingFileGetter listingFileGetter;

    public SecondPasser(ProgramLinesCollection programLines) {
        copyInstructions(programLines);
        this.objectFileGetter = new ObjectFileGetter(getExecutableLines());
        this.listingFileGetter = new ListingFileGetter(getProgramLines());
    }

    private void copyInstructions(ProgramLinesCollection programLinesParam) {
        this.programLines = new ProgramLinesCollection();
        this.executableLines = new ExecutableLinesCollection();
        Iterator<ProgramLine> it = programLinesParam.iterator();
        while (it.hasNext()) {
            ProgramLine line = it.next();
            if(!line.isComment()) {
                executableLines.add((ExecutableLine) line);
            }
            programLines.add(line);
        }
    }

    public String getObjectProgram() throws InvalidFormatException {
        return objectFileGetter.getObjectProgram();
    }

    public String getListingProgram() throws InvalidFormatException {
        return listingFileGetter.getListingProgram();
    }

    /**
     * Getter for property 'programLines'.
     *
     * @return Value for property 'programLines'.
     */
    public ProgramLinesCollection getProgramLines() {
        return programLines;
    }

    /**
     * Setter for property 'programLines'.
     *
     * @param programLines Value to set for property 'programLines'.
     */
    public void setProgramLines(ProgramLinesCollection programLines) {
        this.programLines = programLines;
    }

    /**
     * Getter for property 'executableLines'.
     *
     * @return Value for property 'executableLines'.
     */
    public ExecutableLinesCollection getExecutableLines() {
        return executableLines;
    }

    /**
     * Setter for property 'executableLines'.
     *
     * @param executableLines Value to set for property 'executableLines'.
     */
    public void setExecutableLines(ExecutableLinesCollection executableLines) {
        this.executableLines = executableLines;
    }
}
