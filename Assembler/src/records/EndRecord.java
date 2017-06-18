package records;

import static engine.GlobalDataSingleton.SEPARATOR;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Friday 14/04/2017.
 */
public class EndRecord {

    private int startLocation;
    private StringBuilder recordBuilder;

    public EndRecord(int startLocation) {
        this.startLocation = startLocation;
        this.recordBuilder = new StringBuilder();
    }

    public String getRecord() {
        recordBuilder.append('E');
        recordBuilder.append(SEPARATOR);
        String startLocationInHex = Integer.toHexString(startLocation);
        recordBuilder.append((("000000" + startLocationInHex).substring(startLocationInHex.length())).toUpperCase());
        return recordBuilder.toString();
    }
}
