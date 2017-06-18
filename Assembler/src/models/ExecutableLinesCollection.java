package models;

import java.util.*;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase II : SIC Assembler
 * Created by Bishoy N. Gendy (programajor) on Wednesday 26/04/2017.
 */
public class ExecutableLinesCollection implements Iterable<ExecutableLine> {

    private List<ExecutableLine> executableLines;

    public ExecutableLinesCollection() {
        this.executableLines = new ArrayList<>();
    }

    @Override
    public Iterator<ExecutableLine> iterator() {
        return executableLines.iterator();
    }

    public void add(ExecutableLine executableLine) {
        executableLines.add(executableLine);
    }
}
