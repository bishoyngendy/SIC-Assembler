package tables;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Tuesday 11/04/2017.
 */
public interface OperationTable {

    /**
     * add a new valid instruction with its operation code,
     * @param literal the literal of the operation eg: "STA"
     * @param operationCode the operation code of the operation in 2 hexadecimal digits eg: "F2"
     */
    void addNewOperation(String literal, String operationCode);

    /**
     * checks if the literal is a valid operation or not.
     * @param literal the literal to check for eg: "STA"
     * @return true if it is a valid instruction else false.
     */
    boolean isValidOperation(String literal);

    /**
     * gets the object code for a specific operation.
     * @param literal the Sting of the operation eg: "STA"
     * @return the operation code of the instruction in 2 hexadecimal digits eg: "F2"
     */
    String getOperationCode(String literal);

}
