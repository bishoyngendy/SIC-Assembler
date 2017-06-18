package engine;

import tables.*;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Thursday 13/04/2017.
 */
public class GlobalDataSingleton {

    private static GlobalDataSingleton instance;

    private SymbolTable symbolTable;
    private OperationTable operationTable;
    private LiteralTable literalTable;
    private Integer programLengthInDecimal;
    private int currentLocationCounter;

    public GlobalDataSingleton(SymbolTable symbolTable, OperationTable operationTable, LiteralTable literalTable) {
        this.symbolTable = symbolTable;
        this.operationTable = operationTable;
        this.literalTable = literalTable;
    }


    public static GlobalDataSingleton getInstance() {
        if(instance == null) {
            instance = new GlobalDataSingleton(SymbolTableSingleton.getInstance(),
                            OperationTableSingleton.getInstance(), LiteralTableSingleton.getInstance());
        }
        return instance;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public OperationTable getOperationTable() {
        return operationTable;
    }
    public LiteralTable getLiteralTable() {
        return literalTable;
    }

    Integer getProgramLengthInDecimal() {
        return programLengthInDecimal;
    }

    public void setProgramLengthInDecimal(Integer programLengthInDecimal) {
        this.programLengthInDecimal = programLengthInDecimal;
    }

    public static final int MAX_NUMBER_OF_BYTES_IN_RECORD = 30;
    public static final String SEPARATOR = "^";

    int getCurrentLocationCounter() {
        return currentLocationCounter;
    }

    void setCurrentLocationCounter(int currentLocationCounter) {
        this.currentLocationCounter = currentLocationCounter;
    }

    public void resets() {
        this.symbolTable.resets();
        this.literalTable.resets();
    }
}
