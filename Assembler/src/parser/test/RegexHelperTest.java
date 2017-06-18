package parser.test;

import com.sun.media.sound.InvalidFormatException;
import org.junit.jupiter.api.Test;
import parser.RegexHelper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/28/17.
 */
class RegexHelperTest {
    @Test
    void getPartitionedDataSimpleValidData() throws InvalidFormatException {
        String line = "COPY     START   1000              .comment text hello :)";
        RegexHelper regexHelper = new RegexHelper();
        RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line);
        assertEquals("COPY", partitionedData.label, "Label Value");
        assertEquals("START", partitionedData.operation, "Operation Value");
        assertEquals("1000", partitionedData.operand.trim(), "Operand Value");
        assertEquals("comment text hello :)", partitionedData.comment, "Comment Value");
    }

    @Test
    void getPartitionedDataSimpleValidData2() throws InvalidFormatException {
        String line = "         LDA     s,x               .comment text hello :)";
        RegexHelper regexHelper = new RegexHelper();
        RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line);
        assertEquals("", partitionedData.label.trim(), "Label Value");
        assertEquals("LDA", partitionedData.operation, "Operation Value");
        assertEquals("s,x", partitionedData.operand.trim(), "Operand Value");
        assertEquals("comment text hello :)", partitionedData.comment, "Comment Value");
    }

    @Test
    void testInvalidData() {
        String line = "         LDA     s,x               .comment text hello :)comment text hello :)comment text hello :)comment text hello :)";
        RegexHelper regexHelper = new RegexHelper();
        Throwable exception = assertThrows(InvalidFormatException.class, () -> {
            RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line);
        });

        assertEquals("Line too long!", exception.getMessage(), "Too Long Line");

        String line2 = "       LDA     s,x               .comment text hello :)";
        exception = assertThrows(InvalidFormatException.class, () -> {
            RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line2);
        });
        assertEquals("Invalid Label!", exception.getMessage(), "Too Long Line");

//        String line3 = "         LDA     s,x               .comment text hello :)";
//        exception = assertThrows(InvalidFormatException.class, () -> {
//            RegexHelper.PartitionedData partitionedData = regexHelper.getPartitionedData(line3);
//        });
//
//        assertEquals("Invalid Label!", exception.getMessage(), "Too Long Line");

    }
}