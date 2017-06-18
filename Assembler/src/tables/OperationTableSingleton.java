package tables;

import java.util.HashMap;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 12/04/2017.
 */
public class OperationTableSingleton implements OperationTable {

    private static OperationTable instance;
    
    private HashMap<String, String> map;

    private OperationTableSingleton() {
        this.map = new HashMap<>();
        setUpOperationTable();
    }

    @Override
    public void addNewOperation(String literal, String operationCode) {
        map.put(literal.toLowerCase(), operationCode);
    }

    @Override
    public boolean isValidOperation(String literal) {
        return map.containsKey(literal.toLowerCase());
    }

    @Override
    public String getOperationCode(String literal) {
        return map.get(literal.toLowerCase());
    }

    public static OperationTable getInstance() {
        if(instance == null) {
            instance = new OperationTableSingleton();
        }
        return instance;
    }

    private void setUpOperationTable() {
        addNewOperation("add", getDecimalOfOperationInTwoDigits(24));
        addNewOperation("and", getDecimalOfOperationInTwoDigits(64));
        addNewOperation("comp", getDecimalOfOperationInTwoDigits(40));
        addNewOperation("div", getDecimalOfOperationInTwoDigits( 36));
        addNewOperation("j", getDecimalOfOperationInTwoDigits( 60));
        addNewOperation("jeq", getDecimalOfOperationInTwoDigits( 48));
        addNewOperation("jgt", getDecimalOfOperationInTwoDigits( 52));
        addNewOperation("jlt", getDecimalOfOperationInTwoDigits( 56));
        addNewOperation("jsub", getDecimalOfOperationInTwoDigits( 72));
        addNewOperation("lda", getDecimalOfOperationInTwoDigits( 0));
        addNewOperation("ldch", getDecimalOfOperationInTwoDigits( 80));
        addNewOperation("ldl", getDecimalOfOperationInTwoDigits( 8));
        addNewOperation("ldx", getDecimalOfOperationInTwoDigits( 4));
        addNewOperation("mul", getDecimalOfOperationInTwoDigits( 32));
        addNewOperation("or", getDecimalOfOperationInTwoDigits( 68));
        addNewOperation("rd", getDecimalOfOperationInTwoDigits( 216));
        addNewOperation("rsub", getDecimalOfOperationInTwoDigits( 76));
        addNewOperation("sta", getDecimalOfOperationInTwoDigits( 12));
        addNewOperation("stch", getDecimalOfOperationInTwoDigits( 84));
        addNewOperation("stl", getDecimalOfOperationInTwoDigits( 20));
        addNewOperation("stx", getDecimalOfOperationInTwoDigits( 16));
        addNewOperation("sub", getDecimalOfOperationInTwoDigits( 28));
        addNewOperation("td", getDecimalOfOperationInTwoDigits( 224));
        addNewOperation("tix", getDecimalOfOperationInTwoDigits( 44));
        addNewOperation("wd", getDecimalOfOperationInTwoDigits( 220));
    }

    private String getDecimalOfOperationInTwoDigits(int decimalOfOperation) {
        String hex = Integer.toHexString(decimalOfOperation);
        return ("00" + hex).substring(hex.length());
    }
}
