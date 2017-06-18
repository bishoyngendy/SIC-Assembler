package parser;

import com.sun.media.sound.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 */
public class RegexHelper {
    /**.
     * @param line the line to check
     * @return true if the given line is a directive
     */
    boolean isDirective(String line) {
        return line.matches(PatternUtilities.IS_DIRECTIVE);
    }

    /**.
     * @param line the line to check if it's a comment
     * @return true if the given line is a comment
     */
    boolean isComment(String line) {
        return line.matches(PatternUtilities.IS_COMMENT);
    }

    public PartitionedData getPartitionedData(String line) throws InvalidFormatException {
        Pattern pattern = Pattern.compile(PatternUtilities.VALID_LINE);
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
           throw new InvalidFormatException("Invalid Format");
        }

        checkPartitionSize(matcher.group(PatternUtilities.LABEL_GROUP_WITH_SPACE)
                , matcher.group(PatternUtilities.OPERATION_GROUP_WITH_SPACE)
        , matcher.group(PatternUtilities.OPERAND_GROUP)
        , matcher.group(PatternUtilities.COMMENT_GROUP)
        , line);

        PartitionedData partitionedData = new PartitionedData();
        partitionedData.label = matcher.group(PatternUtilities.LABEL_GROUP).trim();
        partitionedData.operation = matcher.group(PatternUtilities.OPERATION_GROUP).trim();
        partitionedData.operand = matcher.group(PatternUtilities.OPERAND_GROUP).trim();
        partitionedData.comment = matcher.group(PatternUtilities.COMMENT_GROUP);
        return partitionedData;
    }

    private void checkPartitionSize (String label, String operation
            , String operand, String comment, String line)
            throws InvalidFormatException {
        int lineLength = line.length();
        if (line.length() > 66) throw new InvalidFormatException("Line too long!");
        else if(label.length() != 9) throw new InvalidFormatException("Invalid Label!");
        else if(operation.length() != Math.min(8, lineLength - label.length()))
            throw new InvalidFormatException("Invalid Operation!");
        else if(operand.length() != Math.min(18, lineLength
                - operation.length() - label.length()))
            throw new InvalidFormatException("Invalid Operands!");
        else if(comment != null && comment.length() > 30) throw new InvalidFormatException("Invalid Comment!");
    }

    public class PartitionedData {
        public String label;
        public String operation;
        public String operand;
        public String comment;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PartitionedData that = (PartitionedData) o;

            if (!label.equals(that.label)) return false;
            if (!operation.equals(that.operation)) return false;
            if (!operand.equals(that.operand)) return false;
            return comment.equals(that.comment);
        }

        @Override
        public int hashCode() {
            int result = label.hashCode();
            result = 31 * result + operation.hashCode();
            result = 31 * result + operand.hashCode();
            result = 31 * result + comment.hashCode();
            return result;
        }
    }
}
