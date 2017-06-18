package engine;

import com.sun.media.sound.InvalidFormatException;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 26/04/2017.
 */
public interface ExpressionEvaluator {

    /**
     * gets the location of an expression (operand of EQU and ORG)
     * @param expression the expression to get its location
     * @oaram operation whether "ORG" or "EQU"
     * @return the location of the given expression or throws an exception.
     */
    int getExpressionLocation(String expression, String operation) throws InvalidFormatException;
}
