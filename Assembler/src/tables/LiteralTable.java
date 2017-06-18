package tables;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/25/17.
 * This class represents all of the literals in the assembly program,
 * each one with its value, address, and name.
 */
public interface LiteralTable {
    /**
     * add a new symbol to the the symbol table
     * @param literalName the name of the literal ex: C'AAF'
     * @param literalValue the hexadecimal value or binary value of the literal.
     * @param location the location of the symbol in decimal eg: 1050
     */
    void addLiteral(String literalName, String literalValue, int location);

    /**
     * add a new symbol to the the symbol table
     * @param literalName the name of the literal ex: C'AAF'
     * @param literalValue the hexadecimal value or binary value of the literal.
     */
    LiteralTableSingleton.LiteralData addLiteral(String literalName, String literalValue);

    /**
     * checks if a specific literal is already exist or not.
     * @param literalName the literal to check for eg: C'AAF'
     * @return true if the literal is found else false.
     */
    boolean exists(String literalName);

    /**
     * get the location of a specific literal in the program
     * @param literalName the literal to get its location eg: C'AAF'
     * @return the symbol's location in decimal eg: "1050"
     */
    int getLiteralLocation(String literalName);

    /**
     * set the location of a specific literal in the program
     * @param literalName the literal to get its location eg: C'AAF'
     * @param location the location of the symbol in decimal eg: 1050
     */
    void setLiteralLocation(String literalName, int location);

    /**
     * gets the total length of all literals.
     * @return the total length of all literals.
     */
    int getLiteralsTotalLength();

    /**.
     * Reset the symbol table to initial state
     */
    void resets();
}
