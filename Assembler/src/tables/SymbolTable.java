package tables;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Tuesday 11/04/2017.
 */
public interface SymbolTable {

    /**
     * add a new symbol to the the symbol table
     * @param symbol the symbol text eg: "COPY"
     * @param location the location of the symbol in decimal eg: 1050
     */
    void addSymbol(String symbol, int location);

    /**
     * checks if a specific symbol is already taken or not.
     * @param symbol the symbol to check for eg: "TEST"
     * @return true if the symbol is already taken else false.
     */
    boolean isSymbolFound(String symbol);

    /**
     * get the location of a specific symbol in the program
     * @param symbol the symbol to get its location eg: "TEST"
     * @return the symbol's location in decimal eg: "1050"
     */
    int getSymbolLocation(String symbol);

    /**.
     * Reset the symbol table to initial state
     */
    void resets();
}
