package tables;

import java.util.HashMap;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 12/04/2017.
 */
public class SymbolTableSingleton implements SymbolTable {

    private static SymbolTableSingleton instance;

    private HashMap<String, Integer> map;

    private SymbolTableSingleton() {
        this.map = new HashMap<>();
    }

    @Override
    public void addSymbol(String symbol, int location) {
        map.put(symbol.toLowerCase(), location);
    }

    @Override
    public boolean isSymbolFound(String symbol) {
        return map.containsKey(symbol.toLowerCase());
    }

    @Override
    public int getSymbolLocation(String symbol) {
        return map.get(symbol.toLowerCase());
    }

    @Override
    public void resets() {
        this.map.clear();
    }

    public static SymbolTable getInstance() {
        if(instance == null) {
            instance = new SymbolTableSingleton();
        }
        return instance;
    }

}
