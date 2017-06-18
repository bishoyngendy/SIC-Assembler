package records;

import com.sun.media.sound.InvalidFormatException;

import static engine.GlobalDataSingleton.SEPARATOR;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 14/04/2017.
 */
public class HeaderRecord {

    private int startLocation;
    private String programName;
    private int programLengthInDecimal;
    private StringBuilder recordBuilder;

    public HeaderRecord(int startLocation, String programName, int programLengthInDecimal) {
        this.startLocation = startLocation;
        this.programName = programName;
        this.programLengthInDecimal = programLengthInDecimal;
        this.recordBuilder = new StringBuilder();
    }

    public String getRecord() throws InvalidFormatException {
        recordBuilder.append('H');
        recordBuilder.append(SEPARATOR);
        recordBuilder.append(String.format("%1$-" + 6 + "s", programName));
        recordBuilder.append(SEPARATOR);
        String startLocationInHex = Integer.toHexString(startLocation);
        recordBuilder.append(("000000" + startLocationInHex).substring(startLocationInHex.length()).toUpperCase());
        recordBuilder.append(SEPARATOR);
        String programLengthInHexadecimal = Integer.toHexString(programLengthInDecimal);

        if (startLocation + programLengthInDecimal > Math.pow(2, 15)) {
            throw new InvalidFormatException("Program doesn't fit in SIC Machine memory !!");
        }

        recordBuilder.append(("000000" + programLengthInHexadecimal).substring(programLengthInHexadecimal.length()).toUpperCase());
        return recordBuilder.toString();
    }

}
