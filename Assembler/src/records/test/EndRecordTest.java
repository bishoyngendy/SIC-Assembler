package records.test;

import engine.GlobalDataSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import records.EndRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Monday 17/04/2017.
 */
class EndRecordTest {

    private EndRecord endRecord;

    @BeforeEach
    void setUp() {
        endRecord = new EndRecord(4096);
    }

    @Test
    void getRecord() {
        String res = "E"+ GlobalDataSingleton.SEPARATOR + "001000";
        assertEquals(res, endRecord.getRecord());
    }

}