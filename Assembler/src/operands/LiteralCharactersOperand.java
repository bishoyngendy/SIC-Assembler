package operands;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import models.DirectiveLine;
import parser.PatternUtilities;
import tables.LiteralTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/24/17.
 * This class represents literal Characters. ex: =C'Data'
 */
public class LiteralCharactersOperand extends Literal implements Operand, RegexValidator {

    private String literalName;
    private LiteralTable literalTable;
    private ByteCharacterOperand byteCharacterOperand;

    public LiteralCharactersOperand(String literalName) {
        super(literalName);
        this.literalName = literalName;
        this.literalTable = GlobalDataSingleton.getInstance().getLiteralTable();
        this.byteCharacterOperand = new ByteCharacterOperand(literalName);
    }

    @Override
    public String getInstructionCode(String operationCodeInHexadecimal) throws InvalidFormatException {
        if(literalTable.exists(literalName)) {
            String location = Integer.toHexString(literalTable.getLiteralLocation(literalName));
            return (operationCodeInHexadecimal +
                    ("0000" + location).substring(location.length())).toUpperCase();
        } else {
            throw new InvalidFormatException("Invalid Literal");
        }
    }

    @Override
    public String getValue() {
        return literalName;
    }

    @Override
    public String getHexValue() throws InvalidFormatException {
        return byteCharacterOperand.getInstructionCode("");
    }

    @Override
    public DirectiveLine getDirective() {
        String string = "*        =C'" + byteCharacterOperand.getValue() + "'";
        DirectiveLine line = new DirectiveLine(string);
        line.setLabel("*");
        line.setOperation("BYTE");
        line.setOperand(byteCharacterOperand);
        return line;
    }

    @Override
    public boolean isValid(String operand, String operation) {
        return operand.matches(PatternUtilities.LITERAL_CHARACTERS_OPERAND);
    }

    @Override
    public Operand getNewInstance(String operand) {
        Pattern pattern = Pattern.compile(PatternUtilities.LITERAL_CHARACTERS_OPERAND);
        Matcher matcher = pattern.matcher(operand);
        matcher.find();
        return new LiteralCharactersOperand(matcher.group(2));
    }
}
