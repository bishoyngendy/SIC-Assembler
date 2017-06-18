package records.test;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import records.HeaderRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Monday 17/04/2017.
 */
class HeaderRecordTest {

    private HeaderRecord headerRecord;

    @BeforeEach
    void setUp() {
        headerRecord = new HeaderRecord(4096, "COPY", 4218);
    }

    @Test
    void getRecord() throws InvalidFormatException {
        String res = "H" + GlobalDataSingleton.SEPARATOR + "COPY  "
                + GlobalDataSingleton.SEPARATOR + "001000"
                + GlobalDataSingleton.SEPARATOR + "00107A";
        assertEquals(headerRecord.getRecord(), res);
    }

}