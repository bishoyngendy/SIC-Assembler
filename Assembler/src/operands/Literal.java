package operands;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;
import models.DirectiveLine;
import tables.LiteralTable;

/**.
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/26/17.
 * This is class to mark a class as literal
 */
public abstract class Literal implements Operand{

    private LiteralTable literalTable;
    private String literalName;

    Literal(String literalName) {
        this.literalName = literalName;
        this.literalTable = GlobalDataSingleton.getInstance().getLiteralTable();
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

    /**.
     * @return hexadecimal value of the literal.
     */
    public abstract String getHexValue() throws InvalidFormatException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        return literalName.equals(literal.literalName);
    }

    @Override
    public int hashCode() {
        return literalName.hashCode();
    }

    public abstract DirectiveLine getDirective();
}
