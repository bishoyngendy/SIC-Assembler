package operands;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/14/17.
 */
public class WordOperand implements Operand, RegexValidator {

    private String wordData;
    private String parsedData;

    public WordOperand(String wordData) {
        this.wordData = wordData;
    }

    public WordOperand() {

    }

    private void parseWordData() throws InvalidFormatException {
        if(wordData.length() > 5) {
            throw new InvalidFormatException("Operand larger than 3 bytes");
        }
        int wordInt = Integer.valueOf(wordData);
        parsedData = Integer.toHexString(wordInt);
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        parseWordData();
        return ("000000" + parsedData).substring(parsedData.length());
    }

    @Override
    public String getValue() {
        return wordData;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operation.equalsIgnoreCase("word");
    }

    @Override
    public Operand getNewInstance(String operand) {
        return new WordOperand(operand);
    }
}
